package org.yxm.modules.base.utils;

public class TimeUtil {

    public static String getCurrentTimeStr() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    public static int getCurrentTimeInt(){
        return (int) (System.currentTimeMillis() / 1000);
    }
}
