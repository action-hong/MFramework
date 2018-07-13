package com.jornco.mframework.libs;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.jornco.mframework.libs.models.BaseEntry;
import com.jornco.mframework.libs.rx.operators.ApiErrorOperator;
import com.jornco.mframework.libs.rx.operators.Operators;
import com.jornco.mframework.services.ApiService;
import com.jornco.mframework.services.apiresponses.RobotModel;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by kkopite on 2018/7/13.
 */

public class ApiClient implements ApiClientType {

    private final ApiService service;
    private final Gson gson;

    public ApiClient(ApiService service, Gson gson) {
        this.service = service;
        this.gson = gson;
    }

    @NonNull
    @Override
    public Observable<BaseEntry<List<RobotModel>>> fetchRobotModels() {
        return this.service.getNewModels(1, "cn")
                .lift(apiErrorOperator())
                .subscribeOn(Schedulers.io());
    }

    /**
     * Utility to create a new {@link ApiErrorOperator}, saves us from littering references to gson throughout the client.
     */
    private @NonNull <T> ApiErrorOperator<T> apiErrorOperator() {
        return Operators.apiError(this.gson);
    }
}
