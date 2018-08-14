package org.yxm.bees.module.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.yxm.entity.ting.PaySongEntity;
import org.yxm.entity.ting.SongEntity;
import org.yxm.bees.net.TingNetManager;
import org.yxm.rxbus.RxBus;
import org.yxm.rxbus.events.MusicEvent;
import org.yxm.utils.LogUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicService extends Service {
    public static final String TAG = "MusicService";

    private MyBinder mBinder = new MyBinder();
    private MediaPlayer mMediaPlayer;

    private SongEntity mCurSong;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class MyBinder extends Binder {

        public MusicService getService() {
            return MusicService.this;
        }
    }

    public void playMusicWithPause(SongEntity song) {
        mCurSong = song;
        if (mMediaPlayer == null) {
            playMusicWithoutPause(song);
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

    public void playMusicWithoutPause(SongEntity song) {
        mCurSong = song;
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        mMediaPlayer.reset();
        playNetMusic(song);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mMediaPlayer.stop();
        return super.onUnbind(intent);
    }

    private void playNetMusic(SongEntity song) {
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
                LogUtil.d(TAG, "onFailure");
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

    public SongEntity getCurSong(){
        return mCurSong;
    }

}
