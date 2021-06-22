package com.hanix.portfolio.common.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hanix.portfolio.R;
import com.hanix.portfolio.common.app.GLog;

import static android.view.Gravity.BOTTOM;


public class ToastUtil {

    private static Toast mToast;

    /**
     * LENGTH_SHORT Toast
     *
     * @param context
     * @param msg
     */
    public static void showToastS(final Context context, final String msg) {
        new Handler(Looper.getMainLooper()).post(() -> {
            hideToast();
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            mToast.show();
        });
    }

    /**
     * LENGTH_LONG Toast
     *
     * @param context
     * @param msg
     */
    public static void showToastL(final Context context, final String msg) {
        new Handler(Looper.getMainLooper()).post(() -> {
            hideToast();
            mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            mToast.show();

        });
    }

    /**
     * 토스트 전부 내리기
     */
    public static void hideToast() {
        if (mToast != null) {
            GLog.i("Toast hide true!");
            mToast.cancel();
        }
    }

    /**
     * @param context
     * @param msg
     * @param isHtml
     */
    public static void showToastPresent(final Context context, final String msg, final boolean isHtml) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (isHtml) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Toast.makeText(context, Html.fromHtml(msg, Html.FROM_HTML_MODE_LEGACY), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void setToastLayout(Activity activity, String msg, Context context) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, activity.findViewById(R.id.toastLayout));

        TextView toastTv = layout.findViewById(R.id.toastTv);
        toastTv.setText(msg);

        final Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static void setToastLayoutBlack(Activity activity, String msg, Context context) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_black_layout, activity.findViewById(R.id.toastDarkLayout));

        TextView toastTv = layout.findViewById(R.id.toastDarkTv);
        toastTv.setText(msg);

        final Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
