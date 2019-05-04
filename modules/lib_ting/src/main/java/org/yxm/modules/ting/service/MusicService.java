package org.yxm.modules.ting.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import org.yxm.lib.rxbus.RxBus;
import org.yxm.modules.ting.entity.ting.PaySongEntity;
import org.yxm.modules.ting.entity.ting.SongEntity;
import org.yxm.modules.ting.event.MusicEvent;
import org.yxm.modules.ting.net.TingNetManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicService extends Service {

  public static final String TAG = "MusicService";

  private MusicBinder mBinder = new MusicBinder();
  private MediaPlayer mMediaPlayer;

  private SongEntity mCurSong;

//    private SeekBarThread mSeekbarThread;

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return mBinder;
  }

  public class MusicBinder extends Binder {

    public MusicService getService() {
      return MusicService.this;
    }
  }

  public void playPauseMusic(SongEntity song) {
    mCurSong = song;
    if (mMediaPlayer == null) {
      playNextMusic(song);
      return;
    }
    if (mMediaPlayer.isPlaying()) {
      pauseMediaPlayer();
    } else {
      startMediaPlayer();
    }
  }

  public void playNextMusic(SongEntity song) {
    mCurSong = song;
    if (mMediaPlayer == null) {
      mMediaPlayer = new MediaPlayer();
    }
    mMediaPlayer.reset();
    playNetMusic(song);
  }

  @Override
  public boolean onUnbind(Intent intent) {
    stopMediaPlayer();
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
              startMediaPlayer();

//                            if (mSeekbarThread == null) {
//                                mSeekbarThread = new SeekBarThread();
//                                mSeekbarThread.start();
//                            }
            }
          });
        } catch (Exception e) {
          postPauseEvent();
        }
      }

      @Override
      public void onFailure(Call<PaySongEntity> call, Throwable t) {
        postPauseEvent();
      }
    });
  }

  public boolean isMediaPlaying() {
    if (mMediaPlayer != null) {
      return mMediaPlayer.isPlaying();
    }
    return false;
  }

  private void startMediaPlayer() {
    if (mMediaPlayer != null) {
      mMediaPlayer.start();
      postPlayEvent();
    }
  }

  private void stopMediaPlayer() {
    if (mMediaPlayer != null) {
      mMediaPlayer.stop();
    }
  }

  private void pauseMediaPlayer() {
    if (mMediaPlayer != null) {
      mMediaPlayer.pause();
      postPauseEvent();
    }
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

  public SongEntity getCurSong() {
    return mCurSong;
  }


  public class SeekBarThread extends Thread {

    @Override
    public void run() {
      while (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
        MusicEvent event = new MusicEvent();
        event.state = MusicEvent.STATE_UPDATE_PROGRESS;
//                event.progress = (int) ((mMediaPlayer.getCurrentPosition() / 1000.0) / Integer.valueOf(mCurSong.file_duration) * 100);
        RxBus.get().post(event);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
