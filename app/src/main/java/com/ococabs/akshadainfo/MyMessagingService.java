package com.ococabs.akshadainfo;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        SharedPref.getInstance(getApplicationContext()).saveToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        //create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }

        //check if push notification has notification payload or not
        if (remoteMessage.getNotification() != null) {

            //get the title and body
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            //show notification
            showNotification(title, body);
        }
        if (remoteMessage.getData().size() > 0) {
            showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
//        Uri alarmSound = Uri.parse("android.resource://"+ getApplicationContext().getPackageName() + "/" + R.raw.music);
//        AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                .build();
        //channel_id should be unique for every notification channel
        @SuppressLint("WrongConstant")
        NotificationChannel notificationChannel = new NotificationChannel("channel_oco", "Ococabs",
                NotificationManager.IMPORTANCE_HIGH);

        notificationChannel.setDescription("MI Bari");
        notificationChannel.enableLights(true);
//        notificationChannel.setLightColor(R.color.red);
        notificationChannel.shouldVibrate();

//        notificationChannel.setSound(alarmSound,audioAttributes);
//        notificationChannel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.music ));
        notificationChannel.setLockscreenVisibility(1);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
    }
    private void showNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("redirect","booking");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //to avoid activity overlaps same instance
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        //pass the same channel_id which we created in previous method
        long[] pattern = {500,500,500,500,500,500,500,500,500};
        @SuppressLint("ResourceAsColor") NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "channel_oco")
                .setContentTitle(title)
                .setContentText(body)
//                .setSmallIcon(R.mipmap.square_logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
//                .setLights(R.color.red,10, 20)
//                .setColor(R.color.yellow)
//                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.music ))
                .setVibrate(pattern)
                .setContentIntent(pendingIntent)
                // .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE))
                ;
        notificationManager.notify(1, builder.build());

    }
}
