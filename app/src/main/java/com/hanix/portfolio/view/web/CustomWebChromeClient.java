package com.hanix.portfolio.view.web;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.core.content.ContextCompat;

import com.hanix.portfolio.common.app.GLog;
import com.hanix.portfolio.view.MainActivity;

public class CustomWebChromeClient extends WebChromeClient {

    MainActivity activity;
    WebView webView;

    View customView;
    WebChromeClient.CustomViewCallback customViewCallback;
    int originalOrientation;
    FrameLayout fullScreenContainer;
    FrameLayout.LayoutParams COVER_SCREEN_PARAMS;

    public CustomWebChromeClient(MainActivity activity, WebView webView) {
        this.activity = activity;
        this.webView = webView;
        COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }



    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        GLog.d("onJsAlert : " + message);
        new AlertDialog.Builder(view.getContext())
                .setTitle("알림")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,
                        (dialog, which) -> result.confirm())
                .setCancelable(false)
                .create()
                .show();
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        GLog.e("onJsConfirm : " + message);
        new AlertDialog.Builder(view.getContext())
                .setTitle("Confirm")
                .setMessage(message)
                .setPositiveButton("확인",
                        (dialog, which) -> result.confirm())
                .setNegativeButton("취소",
                        (dialog, which) -> result.cancel())
                .setCancelable(false)
                .create()
                .show();
        return true;
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        GLog.e(consoleMessage.message() + '\n' + consoleMessage.messageLevel() + '\n' + consoleMessage.sourceId());
        return super.onConsoleMessage(consoleMessage);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return activity.onKeyDown(keyCode, event);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onShowCustomView(View view, int requestedOrientation, WebChromeClient.CustomViewCallback callback) {
        this.onShowCustomView(view, callback);
    }

    @Override
    public void onHideCustomView() {
        if (customView == null)
            return;

        setFullscreen(false);
        FrameLayout decor = (FrameLayout) activity.getWindow().getDecorView();
        decor.removeView(fullScreenContainer);
        fullScreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        activity.setRequestedOrientation(originalOrientation);
    }

    private void setFullscreen(boolean enabled) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        activity.setRequestedOrientation(enabled ? ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        if (enabled) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
            if (customView != null) {
                customView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
        win.setAttributes(winParams);
    }

    private static class FullscreenHolder extends FrameLayout {
        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ContextCompat.getColor(ctx, android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

}
