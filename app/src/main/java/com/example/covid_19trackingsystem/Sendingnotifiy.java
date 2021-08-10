package com.example.covid_19trackingsystem;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

public class Sendingnotifiy extends ContextWrapper {

    String channel_notification = "a";

    public Sendingnotifiy(Context base) {
        super(base);

        createNotificationChannel();

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "covid";
            String description = "im here";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;



            NotificationChannel channel = new NotificationChannel(channel_notification, name, importance);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.RED);

            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }

    }


    public void highPriorityNotifications(String title, String body) {

      /*  Intent intent = new Intent(this, BluetoothDiscovery.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);*/

        Notification notification = new NotificationCompat.Builder(this, channel_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_corona_virs)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(body))
                .build();

       /* NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel_notification)
                //.setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Set the intent that will fire when the user taps the notification
                //  .setContentIntent(pendingIntent)
                .setAutoCancel(true);*/


        NotificationManagerCompat.from(this)
                .notify(new Random().nextInt(), notification);
// notificationId is a unique int for each notification that you must define


    }
}
