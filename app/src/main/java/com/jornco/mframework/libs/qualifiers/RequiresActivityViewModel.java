package com.jornco.mframework.libs.qualifiers;

import com.jornco.mframework.libs.ActivityViewModel;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by kkopite on 2018/7/12.
 */

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresActivityViewModel {
    Class<? extends ActivityViewModel> value();
}
