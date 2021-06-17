package com.hanix.portfolio.view.event;

import android.app.Activity;

import com.hanix.portfolio.R;
import com.hanix.portfolio.common.utils.ToastUtil;

/**
 * 뒤로가기(물리키) 터치 시 이벤트 처리
 */
public class OnBackPress {

    private long backKeyPressedTime = 0;

    private final Activity activity;

    public OnBackPress(Activity context) {
        this.activity = context;
    }

    /**
     * 단순하게 activity 를 끄기만 할 때 사용함
     */
    public void onBackPressedInActivity() {
        activity.finish();
        activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * mainActivity 등 주요 화면에서 물리키 작동 시
     */
    public void onBackPress() {

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            ToastUtil.setToastLayoutBlack(activity, "'뒤로' 버튼을 한 번 더 누르시면 종료됩니다.", activity.getApplicationContext());
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
        }
    }


}
