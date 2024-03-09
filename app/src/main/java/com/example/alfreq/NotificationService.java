package com.example.alfreq;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class NotificationService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final String CHANNEL_ID = "my_foreground_service";
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Notification",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentText("Test")
                    .setContentTitle("TestTitle")
                    .setSmallIcon(R.drawable.ic_launcher_background);

            startForeground(1010, notificationBuilder.build());
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    public IBinder binder= new myBinder();
    public class myBinder extends Binder{
        NotificationService getService (){
            return NotificationService.this;
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }
}
