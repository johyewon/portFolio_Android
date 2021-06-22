package com.hanix.portfolio.view.web;

import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;

public class WebViewInterface {

    public static final String NAME_JS_INTERFACE = "HaniAndroid";
    private WebView webView;
    private Activity activity;
    Context context;

    public WebViewInterface(WebView webView, Activity activity) {
        this.webView = webView;
        this.activity = activity;
        context = activity.getApplicationContext();
    }

}
