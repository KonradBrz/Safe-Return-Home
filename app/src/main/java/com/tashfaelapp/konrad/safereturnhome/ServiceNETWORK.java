package com.tashfaelapp.konrad.safereturnhome;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import static com.tashfaelapp.konrad.safereturnhome.App.CHANNEL_ID;

public class ServiceNETWORK extends android.app.Service {

    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;

    //what I should do with this?
    //private Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);

        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp:MyWakeLockTag");

        wakeLock.acquire();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //String input = intent.getStringExtra("inputExtra");

        Intent notificationIntent = new Intent(this, LocationGPS.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Intent broadcastIntent = new Intent(this, DestroyFromNotification.class);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        Intent intent1 = new Intent(context, MainActivity.class);
//        intent1.setAction(Intent.ACTION_MAIN);
//        intent1.addCategory(Intent.CATEGORY_DEFAULT);
//        PendingIntent openIntent = PendingIntent.getActivity(context, 0, intent1, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Application running in the background ")
                .setContentText("")   //ealier was inputextra
                .setSmallIcon(R.drawable.ic_intent_icon)
                .setContentIntent(pendingIntent)
                .addAction(R.mipmap.ic_launcher, "Close app", actionIntent)
                //.addAction(R.mipmap.ic_launcher, "Open app", openIntent)
                .build();

        startForeground(1, notification);

        //do heavy work on a background thread
        //stopSelf();

        //means if you click on foreground info then server do not restart again? if 1 then start if 3 then ... ?
        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        wakeLock.release();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
