package com.hanix.portfolio.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Objects;

public class PrefUtil {

    private static final String PREF_NAME = "Pref";
    private static final String KEY_FCM_TOKEN_ID = "KEY_FCM_TOKEN_ID";

    private static SharedPreferences sf;
    private static PrefUtil instance ;

    private PrefUtil() {
    }

    public static com.hanix.portfolio.common.utils.PrefUtil getInstance(Context context) {

        if (sf == null) {
            sf = context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
        }

        if (instance == null) {
            instance = new PrefUtil();
        }
        return instance;
    }

    /*******  기본 Preference 시작 *******/
    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(key, "");
    }

    public static void setInt(Context context, String key, int value) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getInt(key, 0);
    }
    //*******  기본 Preference 끝 *******/



    /** FCM Token Id 값 설정 */
    public static void setFcmTokenId(Context context, String tokenId) {
        Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_FCM_TOKEN_ID, tokenId);
        editor.apply();
    }

    /** FCM Token Id 값을 취득한다. */
    public static String getFcmTokenId(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getString(KEY_FCM_TOKEN_ID, "");
    }

    /*
    로그인 정보 시작
     */
    public static void logout(Context context) {
    }

    // TODO : 나중에 유저 정보 나오면 getString 부분 key 채우기
    public static boolean isLogout(Context context) {
        return Objects.equals(context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getString("", ""), "");
    }
    /*
    로그인 정보 끝
     */

}
