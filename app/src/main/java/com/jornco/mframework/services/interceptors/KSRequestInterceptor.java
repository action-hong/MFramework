package com.jornco.mframework.services.interceptors;

import com.jornco.mframework.libs.Build;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by kkopite on 2018/7/13.
 */

public class KSRequestInterceptor implements Interceptor {
    public KSRequestInterceptor(Build build) {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 添加一些额外的 和额外的header信息
        // 目前暂无
        // TODO:
        return chain.proceed(chain.request());
    }
}
