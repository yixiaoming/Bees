package org.yxm.bees.module.music;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseMvpFragment;
import org.yxm.bees.base.GlideApp;
import org.yxm.entity.ting.SongBillListEntity;
import org.yxm.entity.ting.SongEntity;
import org.yxm.bees.module.music.service.MusicService;
import org.yxm.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends BaseMvpFragment<IMusicView, TingPresenter>
        implements IMusicView {

    private static final String TAG = "MusicFragment";

    private MusicService mMusicService;
    private MusicService.MyBinder mMusicBinder;

    private ImageView mBtnPreSong;
    private ImageView mBtnNextSong;
    private ImageView mBtnPlayStop;
    private ImageView mSongCover;

    private List<SongEntity> mSongList;
    private int mCurSoneIndex;

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
            mMusicBinder.playMusicWithoutPause(song);
            updateSongCover();
        });
        mBtnNextSong = root.findViewById(R.id.btn_next_song);
        mBtnNextSong.setOnClickListener(v -> {
            mCurSoneIndex = getNextSongIndex();
            SongEntity song = mSongList.get(mCurSoneIndex);
            mMusicBinder.playMusicWithoutPause(song);
            updateSongCover();
        });
        mBtnPlayStop = root.findViewById(R.id.btn_play_stop);
        mBtnPlayStop.setOnClickListener(v -> {
            SongEntity song = mSongList.get(mCurSoneIndex);
            mMusicBinder.playMusicWithPause(song);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.doInitMusicList();
        Intent intent = new Intent(getContext(), MusicService.class);
        getContext().bindService(intent, new MusicServiceConnection(), Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onInitMusicListSuccess(SongBillListEntity songBillListEntity) {
        if (mSongList == null) {
            mSongList = new ArrayList<>();
        }
        mSongList.addAll(songBillListEntity.song_list);
        mCurSoneIndex = 0;
        GlideApp.with(this)
                .load(mSongList.get(mCurSoneIndex).pic_big)
                .into(mSongCover);
    }

    private class MusicServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.d(TAG, "onServiceConcted:" + name);
            mMusicBinder = (MusicService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.d(TAG, "onServiceDisconnected: " + name);
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

    private void updateSongCover() {
        GlideApp.with(this)
                .load(mSongList.get(mCurSoneIndex).pic_big)
                .into(mSongCover);
    }
}
