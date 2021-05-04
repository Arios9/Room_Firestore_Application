package com.example.room_firestore_application.helpClasses;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.room_firestore_application.MyActivities.NotificationMapsActivity;
import com.example.room_firestore_application.R;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;

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
        Intent intent = new Intent(applicationContext, NotificationMapsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(applicationContext,CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_local_fire_department_24)
                        .setContentTitle("Today's Matches")
                        .setContentText("Click to see the Locations")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(applicationContext);
        notificationManager.notify(0, builder.build());
    }
}
