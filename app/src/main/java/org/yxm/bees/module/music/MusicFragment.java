package org.yxm.bees.module.music;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.yxm.bees.R;
import org.yxm.bees.base.BaseMvpFragment;
import org.yxm.bees.entity.ting.SongBillListEntity;
import org.yxm.bees.net.TingNetManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicFragment extends BaseMvpFragment<IMusicView, MusicPresenter>
        implements IMusicView {

    private static final String TAG = "MusicFragment";

    @Override
    protected MusicPresenter createPresenter() {
        return new MusicPresenter();
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
        Button btn = root.findViewById(R.id.btn_music_list);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TingNetManager.getSongBillListData(1, 5, 0, new Callback<SongBillListEntity>() {
                    @Override
                    public void onResponse(Call<SongBillListEntity> call, Response<SongBillListEntity> response) {
                        SongBillListEntity songBillListBean = response.body();
                        List<SongBillListEntity> list = songBillListBean.song_list;
                        if (list != null) {
                            Log.d(TAG, "onSuccess: :" + list.size());
                        } else {
                            Log.d(TAG, "onSuccess: " + songBillListBean);
                        }
                    }

                    @Override
                    public void onFailure(Call<SongBillListEntity> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
