package com.example.alfreq;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import java.util.ArrayList;

public class TracksContext extends Application {

    private static final String CHANNEL_ID="CHANNEL_ID";
    private static ArrayList<Channel> list;

    public static ArrayList<Channel> getChanelList(Context context){
        list=FileReader.readJsonFile(context,R.raw.channel_list_raw);
        return list;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.CHANNEL_ID);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
