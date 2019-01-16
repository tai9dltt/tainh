package com.example.i_tainh.democonnectfirebase;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.i_tainh.democonnectfirebase.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "InstanceIDService";
        @Override
        public void onTokenRefresh() {
            String refreshToken = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "Refresh token: " + refreshToken);
            // If you want to send messages to this application instance or
            // manage this apps subscriptions on the server side, send the
            // Instance ID token to your app server.
//
        }





//    // get notification content
//        if (remoteMessage.getNotification() != null) {
//        Log.e("flag", remoteMessage.getNotification().getBody());
//        Log.e("flag", remoteMessage.getNotification().getTitle());
//        sendNotifycation(remoteMessage.getNotification().getBody());
//
////            sendNotification2(remoteMessage.getNotification().getBody());
//    }
//
//    // get data content
//        if (remoteMessage.getData().size() > 0) {
//        Log.e("flag", remoteMessage.getData() + "");
//        Log.e("flag", remoteMessage.getData().get("k1") + "");
//    }


//    private void sendNotifycation(String messageBody) {
//        Intent intent = new Intent(this, M002Activity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//        startActivity(intent);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.icon)
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        int MnotificationID = (int) System.currentTimeMillis();
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(MnotificationID, notifyBuilder.build());
//
//    }



}
