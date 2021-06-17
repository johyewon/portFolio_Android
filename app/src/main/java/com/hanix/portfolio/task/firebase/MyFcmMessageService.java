package com.hanix.portfolio.task.firebase;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hanix.portfolio.R;
import com.hanix.portfolio.common.app.GLog;
import com.hanix.portfolio.common.utils.AppUtil;
import com.hanix.portfolio.common.utils.PrefUtil;
import com.hanix.portfolio.view.AppIntro;
import com.hanix.portfolio.view.MainActivity;

/**
 * fcm token 획득 및 topic 추가 / fcm 수신 시 메시징 처리
 */
public class MyFcmMessageService extends FirebaseMessagingService {

    private String title, body, imgUrl = "";

    /**
     * fcm token 획득 및 topic 추가
     * @param s
     */
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        FirebaseMessaging.getInstance().subscribeToTopic("Closet");
        FirebaseMessaging.getInstance().subscribeToTopic("ClosetForClient");
        GLog.d("Firebase Instance Id Service : " + s);
        PrefUtil.setFcmTokenId(getApplicationContext(), s);

    }

    /**
     * fcm 수신 시 메시징 처리
     * @param remoteMessage
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if(remoteMessage.getData().size() > 0) {

            if(true) {
                scheduleJob();
            } else {
                handleNow();
            }
        }

        if(remoteMessage.getNotification() != null) {
            GLog.d("Message Notification Body : " + remoteMessage.getNotification().getColor());
            GLog.d("Message Notification Body : " + remoteMessage.getNotification().getIcon());
            GLog.d("Message Notification Body : " + remoteMessage.getNotification().getTitle());
            GLog.d("Message Notification Body : " + remoteMessage.getNotification().getBody());

            if(remoteMessage.getData().get("body") != null && remoteMessage.getData().get("body").length() > 0) {
                title = remoteMessage.getData().get("title");
                body = remoteMessage.getData().get("message");
                imgUrl = remoteMessage.getData().get("imgUrl");
            } else {
                title = remoteMessage.getNotification().getTitle();
                body = remoteMessage.getNotification().getBody();
                imgUrl = String.valueOf(remoteMessage.getNotification().getImageUrl());
            }
            sendNotification();
        }
    }

    /**
     *  어플 켜져있을 시에 핸들링
     */
    private void scheduleJob() {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
    }

    private void handleNow() {
        GLog.d("Short lived task is done.");
    }

    /** 메시지가 수신되었을 때 실행되는 메소드**/
    private void sendNotification() {

        // 어플 실행 중인지 확인해서 실행 중이면 Main 으로 보내기
        Intent resultIntent;
        if(AppUtil.isAppRunning(this)) {
            resultIntent = new Intent(this, MainActivity.class);
        } else {
            resultIntent = new Intent(this, AppIntro.class);
        }

        resultIntent.putExtra("title", title);
        resultIntent.putExtra("text", body);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = getResources().getString(R.string.default_notification_channel_id);
        String channel_nm = getResources().getString(R.string.default_notification_channel_name); // 앱 설정에서 알림 이름으로 뜸.
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setContentTitle(title)
                .setGroup("ClosetClient")
                .setContentText(body)
                .setWhen(System.currentTimeMillis())
                .setSound(defaultSoundUri)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setVibrate(new long[]{1000, 1000})
                .setContentIntent(pendingIntent)
                .setFullScreenIntent(pendingIntent, true)
                .setPriority(Notification.PRIORITY_HIGH);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT  >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channel_nm, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("옷가게");
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setShowBadge(true);
            channel.setVibrationPattern(new long[]{100, 200, 100, 200});
            if(notificationManager != null )
                notificationManager.createNotificationChannel(channel);

            notiBuilder.setNumber(100);
            notiBuilder.setChannelId(channelId);
        }

        // 화면 깨우기
        PowerManager  pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if(pm != null) {
            @SuppressLint("InvalidWakeLockTag")
            WakeLock wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "ClosetClient");
            wakeLock.acquire(3000);
        }

        // 알림 보내기
        if(notificationManager != null) {
            notificationManager.notify("ClosetForClient",0, notiBuilder.build());
        }
    }

}
