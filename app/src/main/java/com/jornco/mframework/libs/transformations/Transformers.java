package com.jornco.mframework.libs.transformations;

import android.support.annotation.NonNull;

/**
 * Created by kkopite on 2018/7/13.
 */

public class Transformers {

    public static @NonNull
    <T> ObserveForUITransformer<T> observeForUI() {
        return new ObserveForUITransformer<>();
    }
}
