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
    // more thing

    @AutoValue.Builder
    public interface Builder {
        Builder gson(Gson __);
        Environment build();
    }

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_Environment.Builder();
    }

}