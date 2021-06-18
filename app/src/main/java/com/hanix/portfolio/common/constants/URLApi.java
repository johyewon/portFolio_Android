package com.hanix.portfolio.common.constants;

import com.hanix.portfolio.common.app.GlobalApplication;

public class URLApi {

    // Server Url
    public static final String URL_SERVER = "https://johyewon.github.io/";

    // 플레이 스토어 url
    public static final String PLAY_STORE_URL = AppConstants.STORE_URL_GOOGLE + "com.hanix.portfolio";

    /** Server url **/
    // url 취득
    public static String getServerURL() {
        return URL_SERVER;
    }

    /** 플레이스토어 url **/
    public static String getStoreURL() {
        return PLAY_STORE_URL;
    }

}
