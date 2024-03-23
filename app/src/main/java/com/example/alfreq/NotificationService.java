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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class NotificationService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent.getAction() != null){
            Toast.makeText(this, "Toast service test", Toast.LENGTH_SHORT).show();
        }
        return START_STICKY;
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
