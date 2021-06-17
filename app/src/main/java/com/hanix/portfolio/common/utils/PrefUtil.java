package com.hanix.portfolio.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefUtil {

    private static final String PREF_NAME = "Pref";
    private static final String KEY_FCM_TOKEN_ID = "KEY_FCM_TOKEN_ID";

    private PrefUtil() {
    }

    private static Editor getEditor(Context context) {
        return getPref(context).edit();
    }

    private static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void setString(Context context, String key, String value) {
        Editor editor = getEditor(context);
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences pref = getPref(context);
        return pref.getString(key, "");
    }

    /**
     * FCM Token Id 값 설정
     */
    public static void setFcmTokenId(Context context, String tokenId) {
        setString(context, KEY_FCM_TOKEN_ID, tokenId);
    }

    /**
     * FCM Token Id 값을 취득한다.
     */
    public static String getFcmTokenId(Context context) {
        return getString(context, KEY_FCM_TOKEN_ID);
    }


    /*
    로그인 정보 시작
     */

    public static void logOut(Context context) {
        setMemEmail(context, "");
        setMemIdx(context, "");
        setMemSnsSort(context, "");
        setMemSnsId(context, "");
        setMemName(context, "");
        setString(context, "pwd", "");
    }

    public static void login(Context context, String email, String idx, String snsSort, String snsId, String name) {
        setMemEmail(context, email);
        setMemIdx(context, idx);
        setMemSnsSort(context, snsSort);
        setMemSnsId(context, snsId);
        setMemName(context, name);
    }

    /*  idx 시작  */
    public static String getMemIdx(Context context) {
        return getString(context, "mem_idx");
    }

    public static void setMemIdx(Context context, String idx) {
        setString(context, "mem_idx", idx);
    }
    /*  idx 끝  */


    /*  이메일 시작  */
    public static String getMemEmail(Context context) {
        return getString(context, "mem_email");
    }

    public static void setMemEmail(Context context, String memEmail) {
        setString(context, "mem_email", memEmail);
    }
    /*  이메일 끝  */

    /* sns 구분 시작 */
    public static String getMemSnsSort(Context context) {
        return getString(context, "mem_sns_sort");
    }

    public static void setMemSnsSort(Context context, String snsSort) {
        setString(context, "mem_sns_sort", snsSort);
    }
    /* sns 구분 끝*/

    /*   sns id 시작  */
    public static String getMemSnsId(Context context) {
        return getString(context, "mem_sns_id");
    }

    public static void setMemSnsId(Context context, String snsId) {
        setString(context, "mem_sns_id", snsId);
    }
    /*   sns id 끝 */

    /*  이름 시작 */
    public static String getMemName(Context context) {
        return getString(context, "mem_name");
    }

    public static void setMemName(Context context, String name) {
        setString(context, "mem_name", name);
    }
    /*  이름 끝 */

    /*
    로그인 정보 끝
     */

}
