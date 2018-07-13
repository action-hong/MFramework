package com.jornco.mframework;

import com.jornco.mframework.libs.ApiEndpoint;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kkopite on 2018/7/11.
 */

@Module(includes = ApplicationModule.class)
public class ExternalApplicationModule {
    private ExternalApplicationModule() {}

    @Provides
    @Singleton
    static ApiEndpoint provideApiEndpoint() {
        return ApiEndpoint.PRODUCTION;
    }
}
