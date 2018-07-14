package com.jornco.mframework.ui.activities;

import android.os.Bundle;

import com.jornco.mframework.R;
import com.jornco.mframework.libs.BaseActivity;
import com.jornco.mframework.libs.qualifiers.RequiresActivityViewModel;
import com.jornco.mframework.viewmodels.TestViewModel;

@RequiresActivityViewModel(TestViewModel.ViewModel.class)
public class TestActivity extends BaseActivity<TestViewModel.ViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}