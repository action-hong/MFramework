package com.jornco.mframework.ui.activities;

import android.os.Bundle;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jornco.mframework.R;
import com.jornco.mframework.libs.BaseActivity;
import com.jornco.mframework.libs.qualifiers.RequiresActivityViewModel;
import com.jornco.mframework.libs.transformations.Transformers;
import com.jornco.mframework.services.apiresponses.RobotModel;
import com.jornco.mframework.viewmodels.TestViewModel;

import rx.Observer;
import timber.log.Timber;

@RequiresActivityViewModel(TestViewModel.ViewModel.class)
public class TestActivity extends BaseActivity<TestViewModel.ViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        RxView.clicks(findViewById(R.id.btn_click))
                .flatMap(__ -> this.viewModel.robotModelList())
                .compose(this.bindToLifecycle())
                .compose(Transformers.observeForUI())
                .subscribe(new Observer<RobotModel>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("robotModel on Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(RobotModel robotModel) {
                        Toast.makeText(TestActivity.this, robotModel.toString(), Toast.LENGTH_SHORT).show();
                        Timber.d("robotModel %s", robotModel.toString());
                    }
                });

    }
}
