package com.jornco.mframework.ui.activities;

import android.os.Bundle;
import android.webkit.WebView;

import com.jornco.mframework.R;
import com.jornco.mframework.libs.BaseActivity;
import com.jornco.mframework.libs.qualifiers.RequiresActivityViewModel;
import com.jornco.mframework.viewmodels.BlocklyViewModel;

@RequiresActivityViewModel(BlocklyViewModel.ViewModel.class)
public class BlocklyActivity extends BaseActivity<BlocklyViewModel.ViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocklyactivity);
        WebView webView = findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/step-execution.html");
    }
}