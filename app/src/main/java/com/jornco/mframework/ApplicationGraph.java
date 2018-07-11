package com.jornco.mframework;

import com.jornco.mframework.libs.Environment;

/**
 * Created by kkopite on 2018/7/11.
 */

public interface ApplicationGraph {
    Environment environment();

    void inject(KSApplication __);
}
