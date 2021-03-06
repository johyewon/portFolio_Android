package com.hanix.portfolio.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.hanix.portfolio.common.app.GLog;
import com.hanix.portfolio.common.utils.DlgUtil;
import com.hanix.portfolio.view.event.OnBackPress;
import com.hanix.portfolio.view.web.WebViewInterface;

public class CommonActivity extends AppCompatActivity {

    public OnBackPress onBackPress;
    public WebView webView;
    public WebSettings webSettings;
    public WebViewInterface webViewInterface;
    public ImageView mainHome, mainSkill, mainNotion, mainBack;
    public TextView mainHomeText, mainSkillText, mainNotionText, mainBackText;
    public static boolean mIsAppFinish = false;
    public static boolean isPageLoading = false;

    @RequiresApi(api = Build.VERSION_CODES.P)
    public static String[] getRequestPermissions() {
        return new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.FOREGROUND_SERVICE,
                Manifest.permission.VIBRATE,
                Manifest.permission.ACCESS_NETWORK_STATE
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length == getRequestPermissions().length) {

            boolean checkResult = false;
            String resultMsg = "";

            for (int i = 0; i < grantResults.length; i++) {
                int result = grantResults[i];

                if (result != PackageManager.PERMISSION_GRANTED) {
                    checkResult = true;
                    resultMsg = permissions[i];
                    break;
                }
            }

            if (checkResult) {

                String errMsg = resultMsg + " ????????? ???????????? ?????? ????????? ??? ????????????. ???????????? ????????? ???????????? ?????? ????????? ?????????.";
                GLog.e(errMsg);
                DlgUtil.showConfirmDlg(CommonActivity.this, errMsg, false, (dialog, which) -> {
                });
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCompat.requestPermissions(this, getRequestPermissions(), 100);

        onBackPress = new OnBackPress(this, webView);


        try {
            onCreateProc(savedInstanceState);
        } catch (Exception e) {
            GLog.e(e.getMessage(), e);
        }
    }


    // ????????? ??? onCreateProc ??????
    public void onCreateProc(Bundle savedInstanceState) {
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        try {
            onConfigurationChangedProc(newConfig);
        } catch (Exception e) {
            GLog.e(e.getMessage(), e);
        }

    }

    public void onConfigurationChangedProc(@NonNull Configuration newConfig) {
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mIsAppFinish) {
            if (this instanceof MainActivity) {
                mIsAppFinish = false;
            }
            finish();
            return;
        }

        try {
            //???????????? onResume()??????
            onResumeProc();
            //???????????? ????????? ??????
            commonLayerInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //???????????? onResume()??????
    public void onResumeProc() {
    }

    //???????????? ????????? ??????
    public void commonLayerInit() {
    }

    @Override
    protected void onStop() {
        super.onStop();

        try {
            //onDestroy ????????? ?????? ?????? onSavedInstanceState
            onStopProc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onStopProc() {

    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            //onDestroy ????????? ?????? ?????? onSavedInstanceState
            onPauseProc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPauseProc() {

    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed();
        if (onBackPress != null) {
            onBackPress.onBackPressed();
        }

    }

    @Override
    final protected void onDestroy() {
        super.onDestroy();

        try {
            //onDestroy ????????? ?????? ?????? onSavedInstanceState
            onDestroyProc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //onDestroy() ????????? ??????
    public void onDestroyProc() {
    }


    // ??? ????????????
    public void finishApp(Activity activity) {
        if (activity.isTaskRoot()) {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }

    // ?????????????????? ?????? ????????????
    public void goMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }


}
