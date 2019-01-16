package com.example.i_tainh.democonnectfirebase;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
//         get notification content
        if (remoteMessage.getNotification() != null) {
        Log.e("flag", remoteMessage.getNotification().getBody());
        Log.e("flag", remoteMessage.getNotification().getTitle());
        SendNotification(remoteMessage.getNotification().getTitle() , remoteMessage.getNotification().getBody());

//            sendNotification2(remoteMessage.getNotification().getBody());
    }

    // get data content
        if (remoteMessage.getData().size() > 0) {
        Log.e("flag", remoteMessage.getData() + "");
        Log.e("flag", remoteMessage.getData().get("k1") + "");
    }

    }
    private void SendNotification(String title,String message){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications",
                    NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.icon)
                .setTicker("Hearty365")
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle(title)
                .setContentText(message)
                .setContentInfo("Info");

        int mNotificationId = (int) System.currentTimeMillis();
        notificationManager.notify(mNotificationId, notificationBuilder.build());
    }
}
