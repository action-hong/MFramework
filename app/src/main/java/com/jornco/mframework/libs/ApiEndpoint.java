package com.jornco.mframework.libs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jornco.mframework.libs.utils.Secrets;

/**
 * Created by kkopite on 2018/7/13.
 */

public enum  ApiEndpoint {
    PRODUCTION("Production", Secrets.Api.Endpoint.PRODUCTION),
    STAGING("Staging", Secrets.Api.Endpoint.STAGING),
    LOCAL("Local", ""),
    CUSTOM("Custom", null);

    private String name;
    private String url;

    ApiEndpoint(final @NonNull String name, final @Nullable String url) {
        this.name = name;
        this.url = url;
    }

    public @NonNull String url() {
        return this.url;
    }

    @Override public String toString() {
        return this.name;
    }

    public static ApiEndpoint from(final @NonNull String url) {
        for (final ApiEndpoint value : values()) {
            if (value.url != null && value.url.equals(url)) {
                return value;
            }
        }
        final ApiEndpoint endpoint = CUSTOM;
        endpoint.url = url;
        return endpoint;
    }


}
