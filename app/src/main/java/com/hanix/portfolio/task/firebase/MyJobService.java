package com.hanix.portfolio.task.firebase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.hanix.portfolio.common.app.GLog;

/**
 *  앱 켜져있을 시 핸들링 서비스
 */
public class MyJobService extends JobService {

    @Override
    public boolean onStartJob(JobParameters job) {
        GLog.d("MyJobService service is started");

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if(pm != null) {
            @SuppressLint("InvalidWakeLockTag")
            WakeLock wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "ClosetClient");
            wakeLock.acquire(3000);
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }

}
