package com.jornco.mframework.libs.preferences;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by kkopite on 2018/7/14.
 */

public final class BooleanPreference implements BooleanPreferenceType {
    private final SharedPreferences sharedPreferences;
    private final String key;
    private final boolean defaultValue;

    public BooleanPreference(final @NonNull SharedPreferences sharedPreferences, final @NonNull String key) {
        this(sharedPreferences, key, false);
    }

    public BooleanPreference(final @NonNull SharedPreferences sharedPreferences, final @NonNull String key,
                             final boolean defaultValue) {
        this.sharedPreferences = sharedPreferences;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean get() {
        return this.sharedPreferences.getBoolean(this.key, this.defaultValue);
    }

    @Override
    public boolean isSet() {
        return this.sharedPreferences.contains(this.key);
    }

    @Override
    public void set(final boolean value) {
        this.sharedPreferences.edit().putBoolean(this.key, value).apply();
    }

    @Override
    public void delete() {
        this.sharedPreferences.edit().remove(this.key).apply();
    }
}

