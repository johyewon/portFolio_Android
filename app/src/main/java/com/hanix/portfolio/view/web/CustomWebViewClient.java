package com.hanix.portfolio.view.web;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hanix.portfolio.common.app.GLog;
import com.hanix.portfolio.view.MainActivity;

import java.net.URISyntaxException;

/**
 * webView 자체를 처리하는 클래스
 * 링크에 따른 내용, 예시, 에러, 폼 등록 등 렌더링 할 때 호출
 */
public class CustomWebViewClient extends WebViewClient {

    private MainActivity activity;
    private Context context;
    private WebViewInterface mWebViewInterface;
    WebView webView;

    public CustomWebViewClient(MainActivity activity, WebViewInterface webViewInterface, Context context, WebView webView) {
        this.activity = activity;
        this.context = context;
        this.mWebViewInterface = webViewInterface;
        this.webView = webView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = String.valueOf(request.getUrl());
        // 클릭 시 새 창 안 뜨게
        if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {
            Intent intent = null;

            try {
                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME); //IntentURI 처리
                Uri uri = Uri.parse(intent.getDataString());

                context.startActivity(new Intent(Intent.ACTION_VIEW, uri).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)); //해당되는 Activity 실행
                return true;
            } catch (URISyntaxException ex) {
                return false;
            } catch (ActivityNotFoundException e) {
                if (intent == null) return false;

                String packageName = intent.getPackage();
                if (packageName != null) { //packageName 이 있는 경우에는 Google Play 에서 검색을 기본
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    return true;
                }

                return false;
            }
        }
        return false;
    }


    // Load the url
    /**
     *  딥링크 호출 시 처리
     * @param view
     * @param url
     * @return
     */
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        GLog.e("shouldOverrideUrlLoading: " + url);

        if(url.startsWith("tel:")) {
            Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            activity.startActivity(dial);
            return true;
        }
        //키보드 암호화 호출처리
        else if (url.startsWith("intent://")) {
            Intent intent = null;
            try {
                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                if (intent != null) {
                    //앱실행
                    activity.startActivity(intent);
                }
            } catch (URISyntaxException e) {
                //URI 문법 오류 시 처리 구간
                e.printStackTrace();
            } catch (ActivityNotFoundException e) {
                assert intent != null;
                String packageName = intent.getPackage();
                assert packageName != null;
                if (!packageName.equals("")) {
                    // 앱이 설치되어 있지 않을 경우 구글마켓 이동
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                }
            }
            return true;
        }
        else {
            view.loadUrl(url);
            return true;
        }
    }

    /**
     * url intercept 가능
     * @param view
     * @param url
     * @param favicon
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        GLog.e("page start: " + url);
    }

    /**
     * 페이지가 로딩이 된 이후
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(final WebView view, String url) {
        activity.resetBottomBtns();
        GLog.e("onPageFinished: " + url);
    }
}
