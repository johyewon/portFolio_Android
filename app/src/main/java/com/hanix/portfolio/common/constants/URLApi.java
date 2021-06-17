package com.hanix.portfolio.common.constants;

import com.hanix.portfolio.common.app.GlobalApplication;

public class URLApi {

    // Server Url
    private static final String URL_DEV_SERVER = "";
    private static final String URL_REAL_SERVER = "";

    // 플레이 스토어 url
    public static final String PLAY_STORE_URL = AppConstants.STORE_URL_GOOGLE + "com.hanix.portfolio";

    /** Server url **/
    // url 취득
    public static String getServerURL() {
        //Release 버전에서는 무조건 실서버를 바라본다
        if(GlobalApplication.getInstance().isDebuggable) {
            return URL_DEV_SERVER;
        }
        return URL_REAL_SERVER;
    }

    /** 플레이스토어 url **/
    public static String getStoreURL() {
        return PLAY_STORE_URL;
    }

}
