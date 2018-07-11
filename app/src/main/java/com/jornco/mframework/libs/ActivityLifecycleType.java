package com.jornco.mframework.libs;

import com.trello.rxlifecycle.ActivityEvent;

import rx.Observable;

/**
 * Created by kkopite on 2018/7/11.
 */

public interface ActivityLifecycleType {

    Observable<ActivityEvent> lifecycle();
}
