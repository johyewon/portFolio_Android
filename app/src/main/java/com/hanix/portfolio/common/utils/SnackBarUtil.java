package com.hanix.portfolio.common.utils;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.hanix.portfolio.common.app.GLog;

public class SnackBarUtil {

    private static Snackbar mSnackbar;

    public static void hideSnackbar() {
        if (mSnackbar != null) {
            GLog.i("Snackbar hide true!");
            mSnackbar.dismiss();
        }
    }

    public static void showSnackbar(final View view, final String msg, final int length) {
        new Handler(Looper.getMainLooper()).post(() -> {
            hideSnackbar();
            mSnackbar = Snackbar.make(view, msg, length).setAction("Action", null);
            mSnackbar.show();
        });
    }

}
