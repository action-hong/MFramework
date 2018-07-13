package com.jornco.mframework.services.interceptors;

import com.jornco.mframework.libs.CurrentUserType;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by kkopite on 2018/7/13.
 */

public class ApiRequestInterceptor implements Interceptor {
    public ApiRequestInterceptor(String clientId, CurrentUserType currentUser, String url) {
        // TODO:
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request());
    }
}
