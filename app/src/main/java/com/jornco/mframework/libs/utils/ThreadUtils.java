package com.jornco.mframework.libs.utils;

import android.os.Looper;

/**
 * Created by kkopite on 2018/7/13.
 */

public class ThreadUtils {
    private ThreadUtils() {}

    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
