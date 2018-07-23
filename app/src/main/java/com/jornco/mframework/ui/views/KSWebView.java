package com.jornco.mframework.ui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.jornco.mframework.libs.ApiCapabilities;
import com.jornco.mframework.libs.WebViewJavascriptInterface;
import com.jornco.mframework.services.KSWebViewClient;

import javax.inject.Inject;

/**
 * Created by kkopite on 2018/7/17.
 */

public class KSWebView extends WebView {

    @Inject
    KSWebViewClient mClient;

    public KSWebView(Context context) {
        this(context, null);
    }

    public KSWebView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.webViewStyle);
    }

    public KSWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode()) {
            return;
        }

//        context.getApplicationContext()
        setWebViewClient(mClient);
        setWebChromeClient(new WebChromeClient());
        getSettings().setJavaScriptEnabled(true);
        getSettings().setAllowFileAccess(true);
        enableDebugging();

        addJavascriptInterface(new WebViewJavascriptInterface(this.mClient), "WebViewJavascriptInterface");
    }

    public KSWebViewClient client() {
        return this.mClient;
    }

    @TargetApi(19)
    private void enableDebugging() {
        if (ApiCapabilities.canDebugWebViews()) {
            setWebContentsDebuggingEnabled(true);
        }
    }

}
