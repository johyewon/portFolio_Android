package com.hanix.portfolio.view.event;

import android.app.Activity;
import android.webkit.WebView;

import com.hanix.portfolio.R;
import com.hanix.portfolio.common.utils.ToastUtil;

/**
 * 뒤로가기(물리키) 터치 시 이벤트 처리
 */
public class OnBackPress {

    private long backKeyPressedTime = 0;

    private final Activity activity;
    private final WebView webView;

    public OnBackPress(Activity context, WebView webView) {
        this.activity = context;
        this.webView = webView;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            if (webView != null && webView.canGoBack()) {
                webView.goBack();
            } else {
                backKeyPressedTime = System.currentTimeMillis();
                ToastUtil.setToastLayoutBlack(activity, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", activity.getApplicationContext());
            }
        } else if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
        }

    }

}
