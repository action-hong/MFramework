package com.jornco.mframework.libs;

import android.webkit.JavascriptInterface;

import com.jornco.mframework.services.KSWebViewClient;

import timber.log.Timber;

/**
 * Created by kkopite on 2018/7/17.
 */

public class WebViewJavascriptInterface {

    private final KSWebViewClient mKSWebViewClient;

    public WebViewJavascriptInterface(KSWebViewClient KSWebViewClient) {
        mKSWebViewClient = KSWebViewClient;
    }

    @JavascriptInterface
    public void exec(String module, String method, String args) {
        Timber.d(module, method, args);
    }
}
