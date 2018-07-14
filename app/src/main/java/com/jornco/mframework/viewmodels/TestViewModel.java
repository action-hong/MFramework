package com.jornco.mframework.viewmodels;

import android.support.annotation.NonNull;

import com.jornco.mframework.libs.ActivityViewModel;
import com.jornco.mframework.libs.Environment;
import com.jornco.mframework.ui.activities.TestActivity;

public interface TestViewModel {

    interface Inputs {

    }

    interface Outputs {

    }


    class ViewModel extends ActivityViewModel<TestActivity> implements Inputs, Outputs {

        public ViewModel(@NonNull Environment environment) {
            super(environment);

        }
    }

}