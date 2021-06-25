package com.hanix.portfolio.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.hanix.portfolio.R;
import com.hanix.portfolio.common.app.GLog;
import com.hanix.portfolio.common.constants.AppConstants;
import com.hanix.portfolio.common.constants.URLApi;
import com.hanix.portfolio.view.event.OnSingleClickListener;
import com.hanix.portfolio.view.web.CustomWebChromeClient;
import com.hanix.portfolio.view.web.CustomWebViewClient;
import com.hanix.portfolio.view.web.WebViewInterface;

public class MainActivity extends CommonActivity {

    private CookieManager mCookieManager;

    String APP_SCHEME = AppConstants.APP_SCHEME;
    private boolean isAppFirstRun = true;

    @Override
    public void onCreateProc(Bundle savedInstanceState) {
        super.onCreateProc(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        webInit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String url = intent.toString();

        if (url.startsWith(APP_SCHEME)) {
            // "iamportapp://https://pgcompany.com/foo/bar"와 같은 형태로 들어옴
            String redirectURL = url.substring(APP_SCHEME.length() + "://".length());
            webView.loadUrl(redirectURL);
        }
    }

    @Override
    public void onConfigurationChangedProc(@NonNull Configuration newConfig) {
        super.onConfigurationChangedProc(newConfig);
    }

    @Override
    public void onStopProc() {
        super.onStopProc();
    }

    @Override
    public void onPauseProc() {
        super.onPauseProc();
    }


    @Override
    public void onResumeProc() {
        super.onResumeProc();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDestroyProc() {
        super.onDestroyProc();
    }

    private void init() {
        webView = findViewById(R.id.mainWebView);
        mainHome = findViewById(R.id.mainHome);
        mainSkill = findViewById(R.id.mainSkill);
        mainContact = findViewById(R.id.mainContact);
        mainNotion = findViewById(R.id.mainNotion);
        mainBack = findViewById(R.id.mainBack);
        mainHomeText = findViewById(R.id.mainHomeText);
        mainSkillText = findViewById(R.id.mainSkillText);
        mainContactText = findViewById(R.id.mainContactText);
        mainNotionText = findViewById(R.id.mainNotionText);
        mainBackText = findViewById(R.id.mainBackText);


        mainHome.setOnClickListener(mainClick);
        mainSkill.setOnClickListener(mainClick);
        mainContact.setOnClickListener(mainClick);
        mainNotion.setOnClickListener(mainClick);
        mainBack.setOnClickListener(mainClick);
        mainHomeText.setOnClickListener(mainClick);
        mainSkillText.setOnClickListener(mainClick);
        mainContactText.setOnClickListener(mainClick);
        mainNotionText.setOnClickListener(mainClick);
        mainBackText.setOnClickListener(mainClick);
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    private void webInit() {
        // 웹뷰 가속화
        webViewInterface = new WebViewInterface(webView, this);
        webView.setBackgroundColor(0x00000000);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        // JavaScript Interface 연결
        webView.setNetworkAvailable(true);
        webView.clearFormData();
        webView.clearHistory();
        webView.clearCache(true);
        webView.setWebViewClient(new CustomWebViewClient(this, webViewInterface, getApplicationContext(), webView) {

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                if (isAppFirstRun) {
                    view.post(() -> {

                        view.clearHistory();                                                        // 앱 재실행 시 이전 기록 삭제
                        isAppFirstRun = false;
                    });

                    return super.shouldInterceptRequest(view, request);
                } else
                    return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                isPageLoading = true;
                super.onPageStarted(view, url, favicon);
            }

        });
        webView.setWebChromeClient(new CustomWebChromeClient(this, webView) {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView webView = new WebView(MainActivity.this);

                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(webView);
                resultMsg.sendToTarget();
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        // 클릭 시 새 창 안 뜨게
                        Intent browsIntent = new Intent(Intent.ACTION_VIEW);
                        browsIntent.setData(Uri.parse(url));
                        return true;
                    }
                });
                return true;
            }
        });     // 클릭 시 새 창 안 뜨게

        webSettings = webView.getSettings();                                                              // 세부 세팅 등록
        webSettings.setJavaScriptEnabled(true);                                                                // 웹페이지 자바스크립트 허용 여부
        webSettings.setSupportMultipleWindows(true);                                                       // 새창 띄우기 허용 여부
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);                                // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        webSettings.setLoadWithOverviewMode(true);                                                       // 메타태그 허용 여부
        webSettings.setUseWideViewPort(true);                                                                // 화면 사이즈 맞추기 허용 여부
        webSettings.setSupportZoom(false);                                                                     // 화면 줌 허용 여부
        webSettings.setBuiltInZoomControls(false);                                                             // 화면 확대 축소 허용 여부
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);                                   // 브라우저 캐시 허용 여부
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);                                                               // 로컬저장소 허용 여부
        webSettings.setDefaultTextEncodingName("UTF-8");                                             // TextEncoding 이름 정의
        webSettings.setBlockNetworkImage(false);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setUserAgentString(webSettings.getUserAgentString() + "HANIX_ANDROID");
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true);
        webView.addJavascriptInterface(webViewInterface, WebViewInterface.NAME_JS_INTERFACE); //웹뷰에 JavaScript Interface 를 연결
        webSettings.setTextZoom(100);

        webView.loadUrl(URLApi.getServerURL());
    }

    /**
     * OnClickListener -> 중복 클릭
     */
    OnSingleClickListener mainClick = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            int id = v.getId();
            if (id == R.id.mainHome || id == R.id.mainHomeText) {
                if (webView != null)
                    webView.loadUrl(URLApi.getServerURL());
            } else if (id == R.id.mainSkill || id == R.id.mainSkillText) {
                if (webView != null)
                    webView.loadUrl(URLApi.getServerURL() + "/skills");

            } else if (id == R.id.mainContact || id == R.id.mainContactText) {
                // TODO : startActivity

            } else if (id == R.id.mainNotion || id == R.id.mainNotionText) {
                if (webView != null)
                    webView.loadUrl(URLApi.getNotionURL());

            } else if (id == R.id.mainBack || id == R.id.mainBackText) {
                if (webView != null && webView.canGoBack())
                    webView.goBack();

            }
        }
    };

    /**
     * 물리키 제어
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 웹뷰 데이터 삭제 시작
     **/
    public void clearWebViewCache() {
        if (webView != null) {
            //캐시삭제
            webView.clearCache(true);
            webView.clearHistory();
            clearCookies();
        }
    }

    public static void clearCookies() {
        GLog.d("Using Cookies code for API >=" + Build.VERSION_CODES.LOLLIPOP_MR1);
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
    }

    // 세션 유지
    private void setCookieAllow(WebView webView, String mToken) {
        try {
            CookieManager mCookieManager = CookieManager.getInstance();
            mCookieManager.setAcceptCookie(true);
            GLog.d("cookie mToken " + mToken);
            mCookieManager.setCookie(URLApi.getServerURL(), "user=" + mToken);
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            mCookieManager.setAcceptThirdPartyCookies(webView, true);

        } catch (Exception ignored) {
        }
    }
    /*
     * 웹뷰 데이터 삭제 끝
     **/

    /**
     * 버튼 리셋 및 현재 url 로 하단 독바 구분
     */
    public void resetBottomBtns() {
        if (webView == null) return;

        mainBack.setImageResource(R.drawable.ic_back);
        mainBackText.setTextColor(color(R.color.dark_gray));
        mainHome.setImageResource(R.drawable.ic_home);
        mainHomeText.setTextColor(color(R.color.dark_gray));
        mainSkill.setImageResource(R.drawable.ic_skill);
        mainSkillText.setTextColor(color(R.color.dark_gray));
        mainNotion.setImageResource(R.drawable.ic_notion);
        mainNotionText.setTextColor(color(R.color.dark_gray));


        String url = webView.getUrl();

        if (webView != null && webView.canGoBack())
            mainBack.setImageResource(R.drawable.ic_back_bold);

        if (url.equals(URLApi.getServerURL()) || url.equals(URLApi.getServerURL() + "/")) {
            mainHome.setImageResource(R.drawable.ic_home_bold);
            mainHomeText.setTextColor(color(R.color.mainColor));
        }

        if (url.contains(URLApi.getServerURL() + "/skills")) {
            mainSkill.setImageResource(R.drawable.ic_skill_bold);
            mainSkillText.setTextColor(color(R.color.mainColor));
        }

        if (url.contains(URLApi.getNotionURL())) {
            mainNotion.setImageResource(R.drawable.ic_notion_bold);
            mainNotionText.setTextColor(color(R.color.mainColor));
        }

    }


    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(MainActivity.this, res);
    }


}