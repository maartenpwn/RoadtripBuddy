package com.example.maarten.roadtripbuddy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ReceiveBroadcast extends BroadcastReceiver {

    private final static String BATTERY_LEVEL = "level";
    private static final String TAG = "poep";

    public ReceiveBroadcast() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        int level = intent.getIntExtra(BATTERY_LEVEL, 0);
        Log.d(TAG, "onReceive: batterij leverl: " + level);

        if(level < 26){
            Log.d(TAG, "onReceive: low battery!" + level);
            showNotification(context);
        }
    }

    private void showNotification(Context context) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, OverviewActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.city)
                        .setContentTitle("Battery low!")
                        .setContentText("Charge your phone for your city activities!");
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

    }

}