package com.jornco.mframework.ui.data;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

/**
 * Created by kkopite on 2018/7/11.
 */

@AutoValue
public abstract class ActivityResult implements Parcelable{

    public abstract int requestCode();
    public abstract int resultCode();
    public abstract Intent intent();


    @AutoValue.Builder
    public interface Builder {
        public abstract Builder requestCode(int __);
        public abstract Builder resultCode(int __);
        public abstract Builder intent(Intent __);
        public abstract ActivityResult build();
    }

    public static @NonNull ActivityResult create(final int requestCode, final int resultCode, final Intent intent) {
        return ActivityResult.builder()
                .intent(intent)
                .requestCode(requestCode)
                .resultCode(resultCode)
                .build();
    }

    public static Builder builder () {
        return new AutoValue_ActivityResult.Builder();
    }

    public boolean isCanceled() {
        return resultCode() == Activity.RESULT_CANCELED;
    }

    public boolean isOk() {
        return resultCode() == Activity.RESULT_OK;
    }

    public boolean isRequestCode(final int v) {
        return requestCode() == v;
    }
}
