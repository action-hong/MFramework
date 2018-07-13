package com.jornco.mframework.libs;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;


/**
 * Created by kkopite on 2018/7/11.
 */

@AutoValue
public abstract class Environment implements Parcelable {

    public abstract Gson gson();
    public abstract ApiClientType apiClient();
    // more thing

    @AutoValue.Builder
    public interface Builder {
        Builder gson(Gson __);
        Builder apiClient(ApiClientType __);
        Environment build();
    }

    public static Builder builder() {
        return new AutoValue_Environment.Builder();
    }

}
