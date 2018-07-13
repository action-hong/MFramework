package com.jornco.mframework.libs.rx.operators;

import com.google.gson.Gson;

/**
 * Created by kkopite on 2018/7/13.
 */

public class Operators {

    private Operators() {

    }


    public static <T> ApiErrorOperator<T> apiError(Gson gson) {
        return new ApiErrorOperator<>(gson);
    }
}
