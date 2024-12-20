package com.example.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class Notificationservice extends FirebaseMessagingService {

    // Override onNewToken to get new token
    @Override
    public void onNewToken(@NonNull String token)
    {
        Log.d("TAG", "Refreshed token: " + token);
    }
    // Override onMessageReceived() method to extract the
    // title and
    // body from the message passed in FCM
    @Override
    public void
    onMessageReceived(RemoteMessage remoteMessage)
    {
        Log.d("Recever", "Received inside");
        if (remoteMessage.getNotification() != null) {
            showNotification(
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        }
    }

    // Method to display the notifications
    public void showNotification(String title,
                                 String message) {
        Log.d("Show", "" + title + "  " + message);
        // Pass the intent to switch to the MainActivity
        Intent intent
                = new Intent(this, MainActivity.class);
        // Assign channel ID
        String channel_id = "notification_channel";
        // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
        // the activities present in the activity stack,
        // on the top of the Activity that is to be launched
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Pass the intent to PendingIntent to start the
        // next Activity
        PendingIntent pendingIntent
                = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        // Create a Builder object using NotificationCompat
        // class. This will allow control over all the flags
        NotificationCompat.Builder builder
                = new NotificationCompat
                .Builder(getApplicationContext(),
                channel_id)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000,
                        1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);


        builder = builder.setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_background);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Check if the Android Version is greater than or equal to Oreo (API level 26)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Create the NotificationChannel if it doesn't exist
            NotificationChannel notificationChannel = new NotificationChannel(
                    channel_id,
                    "Web App Notifications",  // Name of the channel
                    NotificationManager.IMPORTANCE_HIGH  // Importance level (e.g., HIGH for top priority)
            );
            // Create the NotificationChannel
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // Finally, issue the notification
        notificationManager.notify(0, builder.build());
    }
}
