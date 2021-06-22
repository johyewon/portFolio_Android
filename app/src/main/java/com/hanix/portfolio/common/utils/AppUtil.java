package com.hanix.portfolio.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.hanix.portfolio.common.app.GLog;
import com.hanix.portfolio.common.constants.URLApi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class AppUtil {

    /** 앱이 실행 중인지 확인 **/
    public static boolean isAppRunning(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();
        for(int i = 0; i < procInfos.size(); i ++) {
            if(procInfos.get(i).processName.equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /** 확인하고 싶은 서비스가 실행중인지 확인 **/
    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if(serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 현재 설치된 버전 가져오기
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
     *  - 주의사항 : 기기에 따라 다릅니다. 일 경우 읽어올 수 없음
     * @return
     */
    public static String getMarketVersion() {
        try {
            Document doc = Jsoup.connect(URLApi.getStoreURL()).get();
            Elements currentVersionDiv = doc.select(".BgcNfc");
            Elements currentVersion = doc.select("div.hAyfc div span.htlgb");
            for(int i = 0; i < currentVersionDiv.size(); i++) {
                if(currentVersionDiv.get(i).text().equals("Current Version"))
                    return currentVersion.get(i).text();
            }
        } catch (IOException e) {
            e.printStackTrace();
            GLog.e(e.getMessage());
            GLog.e("문서 오류");
            e.printStackTrace();
        } catch (RuntimeException runTimeEx) {
            GLog.e(runTimeEx.getMessage());
            GLog.e("버전가져오기 에러");
        }
        return null;
    }

    //  현재 설치 버전과 플레이스토어 버전이 같은지 확인
    public static boolean isVersionEquals(Activity context) {
        return getMarketVersion() != null && !getMarketVersion().equals("기기에 따라 다릅니다.") && TextUtils.equals(getAppVersion(context), getMarketVersion());
    }

    // 현재 버전과 플레이스토어 버전이 같은지 비교
    public static boolean  compareVersion(Activity context) {
        String myAppVersion = getAppVersion(context);
        String marketVersion = getMarketVersion();

        if(myAppVersion != null && marketVersion !=  null && !marketVersion.contains("기기에 따라 다릅니다.")) {
            String[] myAppVersionSplit = myAppVersion.split("\\.");
            String[] marketVersionSplit = marketVersion.split("\\.");

            if(myAppVersionSplit.length == marketVersionSplit.length) {
                for(int i = 0; i < marketVersionSplit.length; i++) {
                    if(Integer.parseInt(marketVersionSplit[i]) > Integer.parseInt(myAppVersionSplit[i]))
                        return false;
                }
            }
        }

        return true;
    }
}
