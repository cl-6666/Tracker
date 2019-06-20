package com.cl.tracker_cl.util;

import android.util.Log;

/**
 * 打印日志
 */
public class LogUtil {

    private final static String tag = "Tracker: ";
    private static boolean isOpened = false;

    public static void openLog(boolean open) {
        isOpened = open;
    }

    public static void i(String info) {
        if (isOpened) {
            Log.i(tag, info);
        }
    }

    public static void e(String info) {
        if (isOpened) {
            Log.e(tag, info);
        }
    }

    public static void d(String info) {
        if (isOpened) {
            Log.d(tag, info);
        }
    }

    public static void v(String info) {
        if (isOpened) {
            Log.v(tag, info);
        }
    }

    public static void w(String info) {
        if (isOpened) {
            Log.w(tag, info);
        }
    }


    /**
     * 此方法谨慎修改
     * 插件配置 disableLog 会修改此方法
     *
     * @param e Exception
     */
    public static void printStackTrace(Exception e) {
        if (e != null) {
            e.printStackTrace();
        }
    }
}
