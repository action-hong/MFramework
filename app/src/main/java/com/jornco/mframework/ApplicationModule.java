package com.jornco.mframework;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.jornco.mframework.libs.Environment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kkopite on 2018/7/11.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(final @NonNull Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    static Environment provideEnvironment(final @NonNull Gson gson) {
        return Environment.builder().gson(gson).build();
    }
}
