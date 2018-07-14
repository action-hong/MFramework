package com.jornco.mframework.libs.qualifiers;

import com.jornco.mframework.libs.FragmentViewModel;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by kkopite on 2018/7/14.
 */

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresFragmentViewModel {
    Class<? extends FragmentViewModel> value();
}
