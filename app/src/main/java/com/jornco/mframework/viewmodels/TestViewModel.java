package com.jornco.mframework.viewmodels;

import android.support.annotation.NonNull;

import com.jornco.mframework.libs.ActivityViewModel;
import com.jornco.mframework.libs.ApiClientType;
import com.jornco.mframework.libs.Environment;
import com.jornco.mframework.services.apiresponses.RobotModel;
import com.jornco.mframework.ui.activities.TestActivity;

import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import timber.log.Timber;

/**
 * Created by kkopite on 2018/7/13.
 */

public interface TestViewModel {

    interface Inputs {
        void refreshModels();
    }

    interface Outputs {
        Observable<RobotModel> robotModelList();
    }


    class ViewModel extends ActivityViewModel<TestActivity> implements Inputs, Outputs {

        private final ApiClientType client;

        public ViewModel(@NonNull Environment environment) {
            super(environment);

            client = environment.apiClient();
//
//            refresh.asObservable()
//                    .compose(bindToLifecycle())
//                    .subscribe(this.)
////                    .compose()

        }

        @Override
        public void refreshModels() {
            refresh.onNext(null);
        }

        @Override
        public Observable<RobotModel> robotModelList() {
            return this.client.fetchRobotModels()
                    .map(listBaseEntry -> {
                        Timber.d(listBaseEntry.toString());
                        return listBaseEntry.getData();
                    })
                    .flatMap(Observable::from);
        }

        private final PublishSubject<Void> refresh = PublishSubject.create();

        private final BehaviorSubject<RobotModel> models = BehaviorSubject.create();

        public final Inputs inputs = this;
        public final Outputs outputs = this;
    }

}
