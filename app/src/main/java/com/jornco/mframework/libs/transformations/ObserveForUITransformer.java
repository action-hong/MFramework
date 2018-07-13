package com.jornco.mframework.libs.transformations;

import android.support.annotation.NonNull;

import com.jornco.mframework.libs.utils.ThreadUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kkopite on 2018/7/13.
 */

public final class ObserveForUITransformer<T> implements Observable.Transformer<T, T> {
    @Override
    public @NonNull
    Observable<T> call(final @NonNull Observable<T> source) {

        return source.flatMap(value -> {
            if (ThreadUtils.isMainThread()) {
                return Observable.just(value).observeOn(Schedulers.immediate());
            } else {
                return Observable.just(value).observeOn(AndroidSchedulers.mainThread());
            }
        });
    }
}