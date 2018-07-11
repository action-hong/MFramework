package com.jornco.mframework;

import android.support.annotation.CallSuper;
import android.support.multidex.MultiDexApplication;

import java.net.CookieManager;

import javax.inject.Inject;

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

        mApplicationComponent =
    }
}
