package org.yxm.modules.ting;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import org.yxm.components.runtime.AppRuntime;
import org.yxm.modules.base.utils.LogUtils;
import org.yxm.modules.ting.aidlservice.AidlMusicService;

public class AidlMusicServiceManager {

    public static final String TAG = "AidlMusicServiceManager";

    private static AidlMusicServiceManager mInstance;

    private AidlMusicService mMusicService;
    private MusicServiceConnection mMusicConnection;
    private IMusicPlayer mMusicPlayer;

    private AidlMusicServiceManager() {
        if (mMusicConnection == null || mMusicPlayer == null) {
            Intent intent = new Intent(AppRuntime.getContext(), AidlMusicService.class);
            mMusicConnection = new MusicServiceConnection();
            AppRuntime.getContext().bindService(intent, mMusicConnection, Context.BIND_AUTO_CREATE);
        }
    }

    public static AidlMusicServiceManager getInstance() {
        synchronized (AidlMusicServiceManager.class) {
            if (mInstance == null) {
                synchronized (AidlMusicServiceManager.class) {
                    mInstance = new AidlMusicServiceManager();
                }
            }
        }
        return mInstance;
    }

    public IMusicPlayer getMusicPlayer() {
        return mMusicPlayer;
    }

    public void unbindService() {
        if (mMusicConnection != null && mMusicPlayer != null) {
            mMusicService.unbindService(mMusicConnection);
        }
    }

    private class MusicServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.d(TAG, "onServiceConcted:" + name);
            mMusicPlayer = IMusicPlayer.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.d(TAG, "onServiceDisconnected: " + name);
        }
    }
}
