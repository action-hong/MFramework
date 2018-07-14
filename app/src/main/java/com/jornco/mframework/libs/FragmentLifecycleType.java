package com.jornco.mframework.libs;

import com.trello.rxlifecycle.FragmentEvent;

import rx.Observable;

/**
 * Created by kkopite on 2018/7/14.
 */

public interface FragmentLifecycleType {
    Observable<FragmentEvent> lifecycle();
}
