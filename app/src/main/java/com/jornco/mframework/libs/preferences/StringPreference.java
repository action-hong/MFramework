package com.jornco.mframework.libs.preferences;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by kkopite on 2018/7/14.
 */

public final class StringPreference implements StringPreferenceType {
    private final SharedPreferences sharedPreferences;
    private final String key;
    private final String defaultValue;

    public StringPreference(final @NonNull SharedPreferences sharedPreferences, final @NonNull String key) {
        this(sharedPreferences, key, null);
    }

    public StringPreference(final @NonNull SharedPreferences sharedPreferences, final @NonNull String key,
                            final @Nullable String defaultValue) {
        this.sharedPreferences = sharedPreferences;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    @Override
    public String get() {
        return this.sharedPreferences.getString(this.key, this.defaultValue);
    }

    @Override
    public boolean isSet() {
        return this.sharedPreferences.contains(this.key);
    }

    @Override
    public void set(final @NonNull String value) {
        this.sharedPreferences.edit().putString(this.key, value).apply();
    }

    @Override
    public void delete() {
        this.sharedPreferences.edit().remove(this.key).apply();
    }
}