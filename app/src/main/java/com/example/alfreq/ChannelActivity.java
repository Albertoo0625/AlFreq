package com.example.alfreq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChannelActivity extends AppCompatActivity implements ServiceConnection {

    private static final String ACTION_PLAY="PLAY";
    private static final String ACTION_PREV="PREV";
    private static final String ACTION_NEXT="NEXT";
    private static final String ACTION_PAUSE="PAUSE";

    private static final String CHANNEL_ID="CHANNEL_ID";
    private ImageView playButton,prevButton,nextButton,pauseButton;
    private TextView title;
    NotificationService notificationservice;

    private boolean isPlaying;
    MediaSessionCompat mediaSession;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        Intent intent=getIntent();
        int id=intent.getIntExtra("channelId",-1);
        String url=intent.getStringExtra("streamurl");
        System.out.println(id);
        System.out.println(url);

        mediaSession=new MediaSessionCompat(this,"audioPlayer");

        playButton=findViewById(R.id.play);
        prevButton=findViewById(R.id.skipprevious);
        nextButton=findViewById(R.id.skipnext);
        pauseButton=findViewById(R.id.pause);
        title=findViewById(R.id.title);

        Intent serviceintent=new Intent(ChannelActivity.this, NotificationService.class);
        bindService(serviceintent,this,BIND_AUTO_CREATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        }
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("test");
                AudioPlayer player= AudioPlayer.getInstance(ChannelActivity.this);
                if(!isPlaying){
                    player.playRadioStream(url,ChannelActivity.this);
                    isPlaying=true;
                    showNotification(R.drawable.baseline_pause_circle_outline_24);
                    playButton.setImageResource(R.drawable.baseline_pause_circle_outline_24);
                }else{
                    player.stopPlayback();
                    isPlaying=false;
                    playButton.setImageResource(R.drawable.baseline_play_circle_outline_24);
                    showNotification(R.drawable.baseline_play_circle_outline_24);
                }
            }
        });


//        pauseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isPlaying){
//
//                }else{
//
//                }
//            }
//        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying){

                }else{

                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying){

                }else{

                }
            }
        });
    }


    public void showNotification(int playPause){
        Intent contextIntent=new Intent(this,MainActivity.class);
        PendingIntent contextpendingIntent=PendingIntent.getActivity(this,0,contextIntent, PendingIntent.FLAG_IMMUTABLE);

        Intent prevIntent= new Intent(this,NotificationReceiver.class).setAction(ACTION_PREV);
        PendingIntent prevpendingIntent=PendingIntent.getBroadcast(this,0,prevIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent playIntent= new Intent(this,NotificationReceiver.class).setAction(ACTION_PLAY);
        PendingIntent playpendingIntent=PendingIntent.getBroadcast(this,0,playIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent pauseIntent= new Intent(this,NotificationReceiver.class).setAction(ACTION_PAUSE);
        PendingIntent pausependingIntent=PendingIntent.getBroadcast(this,0,pauseIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextIntent= new Intent(this,NotificationReceiver.class).setAction(ACTION_NEXT);
        PendingIntent nextpendingIntent=PendingIntent.getBroadcast(this,0,nextIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap picture=BitmapFactory.decodeResource(getResources(),R.drawable.testimage);
        Notification notification=new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(picture)
                .setContentTitle("title")
                .setContentText("test")
                .addAction(R.id.skipprevious,"previous",prevpendingIntent)
                .addAction(playPause,"play",playpendingIntent)
                .addAction(R.id.skipnext,"skip",nextpendingIntent)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setMediaSession(mediaSession.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentIntent(contextpendingIntent)
                .setOnlyAlertOnce(true)
                .build();
        NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,notification);
    }
    @Override
    protected void onPause() {

        super.onPause();
        unbindService(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent serviceintent=new Intent(ChannelActivity.this, NotificationService.class);
        bindService(serviceintent,this,BIND_AUTO_CREATE);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        NotificationService.myBinder mBinder= (NotificationService.myBinder) service;
        notificationservice=mBinder.getService();
        Log.e("CONNECTED ", notificationservice+"");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        notificationservice=null;
        Log.e("DISCONNECTED ", notificationservice+"");
    }
}