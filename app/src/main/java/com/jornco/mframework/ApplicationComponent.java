package com.jornco.mframework;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by kkopite on 2018/7/11.
 */

@Singleton
@Component(modules = ExternalApplicationModule.class)
public interface ApplicationComponent extends ApplicationGraph{
}
