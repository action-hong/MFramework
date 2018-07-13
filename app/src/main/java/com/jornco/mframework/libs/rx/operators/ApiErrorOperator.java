package com.jornco.mframework.libs.rx.operators;

import com.google.gson.Gson;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by kkopite on 2018/7/13.
 */

public final class ApiErrorOperator<T> implements Observable.Operator<T, Response<T>> {

    private final Gson gson;

    public ApiErrorOperator(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Subscriber<? super Response<T>> call(Subscriber<? super T> subscriber) {
        final Gson gson = this.gson;

        return new Subscriber<Response<T>>() {
            @Override
            public void onCompleted() {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }

            @Override
            public void onError(Throwable e) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onError(e);
                }
            }

            @Override
            public void onNext(Response<T> tResponse) {
                if (subscriber.isUnsubscribed()) {
                    return;
                }

                if (!tResponse.isSuccessful()) {
                    // TODO:
//                    gson.fromJson(tResponse.errorBody().string(), ErrorEnvelope.class);
                } else {
                    subscriber.onNext(tResponse.body());
                    subscriber.onCompleted();
                }
            }
        };
    }
}
