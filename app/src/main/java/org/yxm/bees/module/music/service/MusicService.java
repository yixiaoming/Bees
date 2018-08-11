package org.yxm.bees.module.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.yxm.bees.entity.ting.PaySongEntity;
import org.yxm.bees.entity.ting.SongEntity;
import org.yxm.bees.net.TingNetManager;
import org.yxm.utils.LogUtil;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicService extends Service {
    public static final String TAG = "MusicService";

    private MyBinder mBinder;
    private MediaPlayer mMediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mBinder == null) {
            mBinder = new MyBinder();
        }
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        public void playMusic(SongEntity song) {
            if (!mMediaPlayer.isPlaying()) {
                mMediaPlayer.reset();

                TingNetManager.getPaySongData(song.song_id, new Callback<PaySongEntity>() {
                    @Override
                    public void onResponse(Call<PaySongEntity> call, Response<PaySongEntity> response) {
                        PaySongEntity entity = response.body();

                        try {
                            mMediaPlayer.setDataSource(entity.bitrate.file_link);
                            mMediaPlayer.prepare();
                            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mMediaPlayer.start();
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<PaySongEntity> call, Throwable t) {
                        LogUtil.d(TAG, "onFailure");
                    }
                });
            } else {
                mMediaPlayer.pause();
            }
        }
    }
}
