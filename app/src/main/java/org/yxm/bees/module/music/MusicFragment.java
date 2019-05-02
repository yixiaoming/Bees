package org.yxm.bees.module.music;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import org.yxm.bees.R;
import org.yxm.bees.base.GlideApp;
import org.yxm.bees.entity.ting.SongBillListEntity;
import org.yxm.bees.entity.ting.SongEntity;

import java.util.ArrayList;
import java.util.List;
import org.yxm.bees.module.music.event.MusicEvent;
import org.yxm.bees.utils.ToastUtils;
import org.yxm.lib.rxbus.RxBus;
import org.yxm.modules.base.BaseMvpFragment;

@SuppressLint("NewApi")
public class MusicFragment extends BaseMvpFragment<IMusicView, TingPresenter>
        implements IMusicView {

    private static final String TAG = "MusicFragment";

    private ImageView mBtnPreSong;
    private ImageView mBtnNextSong;
    private ImageView mBtnPlayStop;
    private ImageView mSongCover;
    private SeekBar mSeecbar;

    private List<SongEntity> mSongList;
    private int mCurSoneIndex;


    private ObjectAnimator mCoverAnimator;

    @Override
    protected TingPresenter createPresenter() {
        return new TingPresenter();
    }

    public static MusicFragment newInstance() {
        Bundle bundle = new Bundle();
        MusicFragment fragment = new MusicFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = LayoutInflater.from(container.getContext())
                .inflate(R.layout.music_fragment_layout, container, false);
        initViews(root);
        return root;
    }

    private void initViews(View root) {
        mSongCover = root.findViewById(R.id.song_cover);
        mBtnPreSong = root.findViewById(R.id.btn_pre_song);
        mBtnPreSong.setOnClickListener(v -> {
            mCurSoneIndex = getPreSongIndex();
            SongEntity song = mSongList.get(mCurSoneIndex);
            MusicServiceManager.getInstance().getService().playNextMusic(song);
            updateSongCover(song);
        });
        mBtnNextSong = root.findViewById(R.id.btn_next_song);
        mBtnNextSong.setOnClickListener(v -> {
            mCurSoneIndex = getNextSongIndex();
            SongEntity song = mSongList.get(mCurSoneIndex);
            MusicServiceManager.getInstance().getService().playNextMusic(song);
            updateSongCover(song);
        });
        mBtnPlayStop = root.findViewById(R.id.btn_play_stop);
        mBtnPlayStop.setOnClickListener(v -> {
            if (MusicServiceManager.getInstance().getService() == null) {
                ToastUtils.s(getContext(), "播放器还没准备好-.-");
                return;
            }
            SongEntity song = mSongList.get(mCurSoneIndex);
            MusicServiceManager.getInstance().getService().playPauseMusic(song);
        });
        mSeecbar = root.findViewById(R.id.music_seekbar);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.doInitMusicList();
        // rxbux注册控制play和stop按钮显示
        RxBus.get().toObservable().map(new Function<Object, MusicEvent>() {
            @Override
            public MusicEvent apply(Object o) throws Exception {
                return (MusicEvent) o;
            }
        }).subscribe(new Consumer<MusicEvent>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void accept(MusicEvent eventMsg) throws Exception {
                if (eventMsg != null) {
                    if (eventMsg.state == MusicEvent.STATE_PLAY) {
                        mBtnPlayStop.setImageResource(R.drawable.ic_pause_music);
                        startCoverRotateAnim();
                    } else if (eventMsg.state == MusicEvent.STATE_PAUSE) {
                        mBtnPlayStop.setImageResource(R.drawable.ic_play_music);
                        stopCoverRotateAnim();
                    } else if (eventMsg.state == MusicEvent.STATE_UPDATE_PROGRESS) {
                        mSeecbar.setProgress(eventMsg.progress);
                    }
                }
            }
        });

        MusicServiceManager.getInstance().init();
    }

    private void startCoverRotateAnim() {
        if (mCoverAnimator == null) {
            mCoverAnimator = ObjectAnimator.ofFloat(mSongCover, "rotation", 0f, 360.0f);
            mCoverAnimator.setDuration(10000);
            mCoverAnimator.setInterpolator(new LinearInterpolator());
            mCoverAnimator.setRepeatCount(-1);
            mCoverAnimator.setRepeatMode(ValueAnimator.RESTART);
            mCoverAnimator.start();
        }
        mCoverAnimator.resume();
    }

    private void stopCoverRotateAnim() {
        if (mCoverAnimator != null) {
            mCoverAnimator.pause();
        }
    }

    @Override
    public void onInitMusicListSuccess(SongBillListEntity songBillListEntity) {
        if (mSongList == null) {
            mSongList = new ArrayList<>();
        }
        mSongList.addAll(songBillListEntity.song_list);
        mCurSoneIndex = 0;
        if (MusicServiceManager.getInstance().getService() != null
                && MusicServiceManager.getInstance().getService().getCurSong() != null) {
            updateSongCover(MusicServiceManager.getInstance().getService().getCurSong());
            if (MusicServiceManager.getInstance().getService().isMediaPlaying()) {
                mBtnPlayStop.setImageResource(R.drawable.ic_pause_music);
                startCoverRotateAnim();
            }
            SongEntity serviceCurSong = (MusicServiceManager.getInstance().getService().getCurSong());
            for (int i = 0; i < mSongList.size(); i++) {
                SongEntity song = mSongList.get(i);
                if (TextUtils.equals(song.song_id, serviceCurSong.song_id)) {
                    mCurSoneIndex = i;
                }
            }
        } else {
            updateSongCover(mSongList.get(mCurSoneIndex));
        }
    }

    private int getNextSongIndex() {
        if (mCurSoneIndex == mSongList.size() - 1) {
            return 0;
        }
        return mCurSoneIndex + 1;
    }

    private int getPreSongIndex() {
        if (mCurSoneIndex == 0) {
            return mSongList.size() - 1;
        }
        return mCurSoneIndex - 1;
    }

    private void updateSongCover(SongEntity songEntity) {
        GlideApp.with(this)
                .load(songEntity.pic_big)
                .into(mSongCover);
    }
}
