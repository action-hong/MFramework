package com.jornco.mframework.services.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Interceptor for web requests to Kickstarter, not API requests. Used by web views and the web client.
 * Created by kkopite on 2018/7/13.
 */

public class WebRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        // TODO:
        return chain.proceed(chain.request());
    }
}
