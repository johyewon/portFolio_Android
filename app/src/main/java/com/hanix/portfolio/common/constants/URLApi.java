package com.hanix.portfolio.common.constants;

public class URLApi {

    // Server url
    private static final String URL_REAL_SERVER = "https://johyewon.github.io";
    private static final String URL_NOTION_URL = "https://www.notion.so/Hello-Hani-bc73f5169f1a48bf9df08ddbd53ae286";


    public static final String PLAY_STORE = AppConstants.STORE_URL_GOOGLE + "com.hanix.portfolio";

    /**
     * 서버 url
     **/
    // url 취득
    public static String getServerURL() {
        return URL_REAL_SERVER;
    }

    public static String getNotionURL() {
        return URL_NOTION_URL;
    }

    // 플레이스토어 url 개발용이 따로 나눠진 게 아니라 하나로 통합
    public static String getStoreURL() {
        return PLAY_STORE;
    }
}
