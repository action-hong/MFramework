package com.jornco.mframework;

import android.support.annotation.CallSuper;
import android.support.multidex.MultiDexApplication;

import timber.log.Timber;

/**
 * Created by kkopite on 2018/7/11.
 */

public class KSApplication extends MultiDexApplication {

    private ApplicationComponent mApplicationComponent;
//    @Inject protected CookieManager mCookieManager;

    @Override
    @CallSuper
    public void onCreate() {
        super.onCreate();

        this.mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        this.mApplicationComponent.inject(this);

        Timber.plant(new Timber.DebugTree());

    }

    public ApplicationComponent component() {
        return this.mApplicationComponent;
    }
}
