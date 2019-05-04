package org.yxm.modules.ting.event;

/**
 * Created by yxm on 2018.8.12.
 */

public class MusicEvent {
    public static final int STATE_PLAY = 1;
    public static final int STATE_PAUSE = 2;
    public static final int STATE_UPDATE_PROGRESS = 3;

    public int state;
    public int progress;
}
