package com.example.room_firestore_application.helpClasses;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.room_firestore_application.R;

public class MyNotification {

    private Context applicationContext;
    private static final String CHANNEL_ID = "channel0";

    public MyNotification(Context applicationContext) {
        this.applicationContext = applicationContext;
        createNotificationChannel();
        createNotification();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My channel name";
            String description = "My channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = applicationContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(applicationContext,CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_local_fire_department_24)
                        .setContentTitle("Notification")
                        .setContentText("There is a Match Today!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(applicationContext);
        notificationManager.notify(0, builder.build());
    }
}
