package com.hanix.portfolio.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 사용자가 웹 화면 -> 플레이스토어에서 설치할 때 데이터 받아오기
 * referrer 추가해 플레이스토어에 링크 넘김 -> 이후 설치 시 receiver 들어옴
 */
public class StoreReceiver extends BroadcastReceiver {

    static String returnReferrer = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        String referrer = intent.getStringExtra("referrer");
        if(referrer != null && referrer.length() > 0) {
            returnReferrer = referrer;
        }
    }

    public static String getReferrer() {
        return returnReferrer;
    }
}
