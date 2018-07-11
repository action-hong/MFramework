package com.jornco.mframework.services.apiresponses;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

/**
 * Created by kkopite on 2018/7/11.
 */

@AutoValue
public abstract class RobotModel implements Parcelable{

    public abstract String model_name();

    @AutoValue.Builder
    public interface Builder {
        Builder model_name(String __);
        RobotModel build();
    }

    public static Builder builder() { return new AutoValue_RobotModel.Builder(); }
}
