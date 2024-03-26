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
    private static final String ACTION_PLAY="PLAY";
    private static final String ACTION_PREV="PREV";
    private static final String ACTION_NEXT="NEXT";

    ActionPlayer actionPlayer;
    private Context context;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String actionName=intent.getStringExtra("my_action");
        if(actionName != null){
            switch (actionName)
            {
                case ACTION_PREV:
                    if(actionPlayer != null) {
                        actionPlayer.prev();
                    }
                    break;
                case ACTION_PLAY:
                    if(actionPlayer != null) {
                        actionPlayer.playPause();
                    }
                    break;
                case ACTION_NEXT:
                    if(actionPlayer != null) {
                        actionPlayer.next();
                    }
                    break;
            }
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

    public void setCallBack(ActionPlayer actionPlayer){
       this.actionPlayer=actionPlayer;
    }
}
