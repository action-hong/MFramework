package com.jornco.mframework;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jornco.mframework.libs.AutoParcelAdapterFactory;
import com.jornco.mframework.libs.DateTimeTypeConverter;
import com.jornco.mframework.libs.Environment;

import org.joda.time.DateTime;

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
    Application provideApplication() {
        return this.mApplication;
    }

    @Provides
    @Singleton
    static Environment provideEnvironment(@NonNull Gson gson) {
        return Environment.builder().gson(gson).build();
    }

    @Provides
    @Singleton
    static Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter())
                .registerTypeAdapterFactory(new AutoParcelAdapterFactory())
                .create();
    }
}
