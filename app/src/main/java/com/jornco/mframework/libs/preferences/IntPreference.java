package com.jornco.mframework.libs.preferences;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by kkopite on 2018/7/14.
 */

public final class IntPreference implements IntPreferenceType {
    private final SharedPreferences sharedPreferences;
    private final String key;
    private final int defaultValue;

    public IntPreference(final @NonNull SharedPreferences sharedPreferences, final @NonNull String key) {
        this(sharedPreferences, key, 0);
    }

    public IntPreference(final @NonNull SharedPreferences sharedPreferences, final @NonNull String key,
                         final int defaultValue) {
        this.sharedPreferences = sharedPreferences;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    @Override
    public int get() {
        return this.sharedPreferences.getInt(this.key, this.defaultValue);
    }

    @Override
    public boolean isSet() {
        return this.sharedPreferences.contains(this.key);
    }

    @Override
    public void set(final int value) {
        this.sharedPreferences.edit().putInt(this.key, value).apply();
    }

    @Override
    public void delete() {
        this.sharedPreferences.edit().remove(this.key).apply();
    }
}
