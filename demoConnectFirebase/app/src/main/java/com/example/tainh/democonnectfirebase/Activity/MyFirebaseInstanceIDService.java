package com.example.tainh.democonnectfirebase.Activity;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.example.tainh.democonnectfirebase.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Random;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        // get notification content
        if (remoteMessage.getNotification() != null) {
            Log.e("flag", remoteMessage.getNotification().getBody());
            Log.e("flag", remoteMessage.getNotification().getTitle());
            showNotifycation2(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());


        }

        // get data content
        if (remoteMessage.getData().size() > 0) {
            Log.e("flag", remoteMessage.getData() + "");
            Log.e("flag", remoteMessage.getData().get("k1") + "");
        }
    }


//    public void Start() {
//        Firebase.Messaging.FirebaseMessaging.MessageReceived += OnMessageReceived;
//    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }


    private void showNotifycation2(String title, String body){

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFYCATION_CHANGE_ID = "com.example.i_tainh.democonnectfirebase";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFYCATION_CHANGE_ID,"notifycation",
                    notificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("Tainh");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notifycationBulder = new NotificationCompat.Builder(this, NOTIFYCATION_CHANGE_ID);

        notifycationBulder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Info");
        notificationManager.notify(new Random().nextInt(), notifycationBulder.build());
    }

    private void sendNotifycation(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        startActivity(intent);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notifyBuilder.build());

    }



}
