package com.hanix.portfolio.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;

import com.hanix.portfolio.common.app.GLog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class AppUtil {

    /**
     * 앱이 실행 중인지 확인
     **/
    public static boolean isAppRunning(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        for (int i = 0; i < procInfos.size(); i++) {
            if (procInfos.get(i).processName.equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 현재 설치된 버전 가져오기
     *
     * @param context
     * @return
     */
    public static String getAppVersion(Activity context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            GLog.e(e.getMessage(), e);
        }

        return null;
    }

    /**
     * 플레이스토어 최신 버전 가져오기
     * - 주의사항 : 기기에 따라 다릅니다. 일 경우 읽어올 수 없음
     *
     * @return
     */
    public static String getMarketVersion() {
        String packageName = "com.hanix.portfolio";
        String mData = "", mVer;
        try {
            URL mUrl = new URL("https://play.google.com/store/apps/details?id=" + packageName);
            HttpURLConnection mConnection = (HttpURLConnection) mUrl.openConnection();
            if (mConnection == null) return null;
            mConnection.setConnectTimeout(5000);
            mConnection.setUseCaches(false);
            mConnection.setDoOutput(true);
            if (mConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader mReader = new BufferedReader(new InputStreamReader(mConnection.getInputStream()));
                while (true) {
                    String line = mReader.readLine();
                    if (line == null) break;
                    mData += line;
                }
                mReader.close();
            }
            mConnection.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        String startToken = "softwareVersion\">";
        String endToken = "<";
        int index = mData.indexOf(startToken);
        if (index == -1)
            mVer = null;
        else {
            mVer = mData.substring(index + startToken.length(), index + startToken.length() + 100);
            mVer = mVer.substring(0, mVer.indexOf(endToken)).trim();
        }
        return mVer;
    }

}
