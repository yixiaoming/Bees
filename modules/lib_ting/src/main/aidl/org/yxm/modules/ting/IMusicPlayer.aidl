// IMusicPlayer.aidl
package org.yxm.modules.ting;

// Declare any non-default types here with import statements

interface IMusicPlayer {

    void playMusicWithPause(String songUrl);

    void playMusicWithoutPause(String songUrl);
}
