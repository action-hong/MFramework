package com.jornco.mframework.libs.utils;

import android.os.Bundle;

/**
 * Created by kkopite on 2018/7/12.
 */

public class BundleUtils {

    private BundleUtils() {}

    public static Bundle maybeGetBundle(Bundle savedInstanceState, String key) {
        if (savedInstanceState == null) {
            return null;
        }

        return savedInstanceState.getBundle(key);
    }
}
