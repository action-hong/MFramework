package com.jornco.mframework.libs;

import android.support.annotation.NonNull;

import com.jornco.mframework.libs.models.BaseEntry;
import com.jornco.mframework.services.apiresponses.RobotModel;

import java.util.List;

import rx.Observable;

/**
 * Created by kkopite on 2018/7/13.
 */

public interface ApiClientType {

    @NonNull
    Observable<BaseEntry<List<RobotModel>>> fetchRobotModels();
}
