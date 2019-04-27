package org.yxm.bees.module.music.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import org.yxm.bees.IMusicPlayer;
import org.yxm.bees.module.music.event.MusicEvent;
import org.yxm.bees.net.TingNetManager;
import org.yxm.bees.entity.ting.PaySongEntity;

import org.yxm.bees.utils.LogUtils;
import org.yxm.lib.rxbus.RxBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AidlMusicService extends Service {

    private static final String TAG = "AidlMusicService";

    private MediaPlayer mMediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private IMusicPlayer.Stub mBinder = new IMusicPlayer.Stub() {
        @Override
        public void playMusicWithPause(String songId) throws RemoteException {
            if (mMediaPlayer == null) {
                playMusicWithoutPause(songId);
                postPlayEvent();
                return;
            }
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                postPauseEvent();
            } else {
                mMediaPlayer.start();
                postPlayEvent();
            }
        }

        @Override
        public void playMusicWithoutPause(String songId) throws RemoteException {
            if (mMediaPlayer == null) {
                mMediaPlayer = new MediaPlayer();
            }
            mMediaPlayer.reset();
            playNetMusic(songId);
        }
    };

    private void playNetMusic(String songId) {
        TingNetManager.getPaySongData(songId, new Callback<PaySongEntity>() {
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
                            postPlayEvent();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    postPauseEvent();
                }
            }

            @Override
            public void onFailure(Call<PaySongEntity> call, Throwable t) {
                LogUtils.d(TAG, "onFailure");
                postPauseEvent();
            }
        });
    }

    public void postPlayEvent() {
        MusicEvent event = new MusicEvent();
        event.state = MusicEvent.STATE_PLAY;
        RxBus.get().post(event);
    }

    public void postPauseEvent() {
        MusicEvent event = new MusicEvent();
        event.state = MusicEvent.STATE_PAUSE;
        RxBus.get().post(event);
    }


}
