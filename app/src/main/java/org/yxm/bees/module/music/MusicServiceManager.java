package org.yxm.bees.module.music;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import org.yxm.bees.base.BeesApp;
import org.yxm.bees.module.music.service.MusicService;
import org.yxm.modules.base.utils.LogUtils;

public class MusicServiceManager {

    public static final String TAG = "MusicServiceManager";

    private static MusicServiceManager mInstance;

    private MusicService mMusicService;
    private MusicServiceConnection mMusicConnection;


    private MusicServiceManager() {
        Intent intent = new Intent(BeesApp.getInstance(), MusicService.class);
        mMusicConnection = new MusicServiceConnection();
        Context appContext = BeesApp.getInstance();
        appContext.bindService(intent, mMusicConnection, Context.BIND_AUTO_CREATE);
    }

    public static MusicServiceManager getInstance() {
        synchronized (MusicServiceManager.class) {
            if (mInstance == null) {
                synchronized (MusicServiceManager.class) {
                    mInstance = new MusicServiceManager();
                }
            }
        }
        return mInstance;
    }

    public MusicService getService() {
        return mMusicService;
    }

    public void unbindService() {
        if (mMusicConnection != null && mMusicService != null) {
            mMusicService.unbindService(mMusicConnection);
        }
    }

    public void init() {
        if (mMusicService != null && mMusicService.isMediaPlaying() && mMusicService.getCurSong() != null) {

        }
    }

    private class MusicServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicService = ((MusicService.MusicBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.d(TAG, "onServiceDisconnected: " + name);
        }
    }
}
