package com.example.alfreq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.os.BuildCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;

import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.window.OnBackInvokedDispatcher;
import java.util.ArrayList;

public class ChannelActivity extends AppCompatActivity implements ServiceConnection,ActionPlayer{

    private static final String ACTION_PLAY="PLAY";
    private static final String ACTION_PREV="PREV";
    private static final String ACTION_NEXT="NEXT";
    private static final String ACTION_PAUSE="PAUSE";

    private static final String CHANNEL_ID="CHANNEL_ID";
    private ImageView playButton,prevButton,nextButton,pauseButton;
    private TextView title;
    private ImageView album_cover;
    NotificationService notificationservice;

    private boolean isPlaying;
    private boolean fromNotification=false;

    MediaSessionCompat mediaSession;

    private int id;

    private String url,image,titlePassed;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        id=intent.getIntExtra("channelId",-1);
        url=intent.getStringExtra("streamurl");
        image=intent.getStringExtra("imagepath");
        titlePassed=intent.getStringExtra("titlepassed");

        onCreate(null);
    }


//    @NonNull
//    @Override
//    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
//        return super.getOnBackInvokedDispatcher();
//    }


    @NonNull
    @Override
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        if(isTaskRoot()){
            Intent intent= new Intent(ChannelActivity.this,MainActivity.class);
            startActivity(intent);
        }
        return super.getOnBackInvokedDispatcher();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);

        playButton=findViewById(R.id.play);
        prevButton=findViewById(R.id.skipprevious);
        nextButton=findViewById(R.id.skipnext);
        pauseButton=findViewById(R.id.pause);
        title=findViewById(R.id.title);
        album_cover=findViewById(R.id.album_cover);

        Intent intent=getIntent();
        id=intent.getIntExtra("channelId",-1);
        url=intent.getStringExtra("streamurl");
        image=intent.getStringExtra("imagepath");
        titlePassed=intent.getStringExtra("titlepassed");
        title.setText(titlePassed);

        int resourceId = getResources().getIdentifier(image, "drawable", getPackageName());
        System.out.println("resource time "+resourceId);

        if (resourceId != 0) {
            album_cover.setImageResource(resourceId);
        } else {
            // Resource not found
            // Handle this case as per your application logic
            Toast.makeText(ChannelActivity.this, "Resource not found", Toast.LENGTH_SHORT).show();
        }


        System.out.println(id);
        System.out.println(url);
        System.out.println(image);

        mediaSession=new MediaSessionCompat(this,"audioPlayer");

        Intent serviceintent=new Intent(ChannelActivity.this, NotificationService.class);
        bindService(serviceintent,this,BIND_AUTO_CREATE);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPause();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              next();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev();
            }
        });

    }

    public void playPause(){
        System.out.println("test");
        AudioPlayer player= AudioPlayer.getInstance(ChannelActivity.this);
        if(!isPlaying){
            player.playRadioStream(url,ChannelActivity.this);
            isPlaying=true;
            showNotification(R.drawable.baseline_pause_circle_outline_24,id,url,image,titlePassed);
            playButton.setImageResource(R.drawable.baseline_pause_circle_outline_24);
        }else{
            player.stopPlayback();
            isPlaying=false;
            playButton.setImageResource(R.drawable.baseline_play_circle_outline_24);
            showNotification(R.drawable.baseline_play_circle_outline_24,id,url,image,titlePassed);
        }
    }

    public void prev(){
        if(isPlaying) {
            id--;
            ArrayList<Channel> channels=TracksContext.getChanelList(ChannelActivity.this);
            if(id<0)
            {
                id=channels.size()-1;
                Channel prevTrack = channels.get(id);
                String prevUrl = prevTrack.getUrl();
                url=prevUrl;
                String prevAlbumCover=prevTrack.getArtwork();
                String prevTitle=prevTrack.getTitle();
                title.setText(prevTitle);
                int resourceId = getResources().getIdentifier(prevAlbumCover, "drawable", getPackageName());
                System.out.println("resource time "+resourceId);


                if (resourceId != 0) {
                    album_cover.setImageResource(resourceId);
                } else {
                    // Resource not found
                    // Handle this case as per your application logic
                    Toast.makeText(ChannelActivity.this, "Resource not found", Toast.LENGTH_SHORT).show();
                }
                AudioPlayer.getInstance(ChannelActivity.this).playRadioStream(prevUrl, ChannelActivity.this);
            }else {
                System.out.println("PROBLEMATIC ID"+ id);
                if(id<0) {
                    channels=TracksContext.getChanelList(ChannelActivity.this);
                    id=0;
                    Channel prevTrack = channels.get(id);
                    String prevUrl = prevTrack.getUrl();
                    url=prevUrl;
                    String prevAlbumCover = prevTrack.getArtwork();
                    String prevTitle = prevTrack.getTitle();
                    title.setText(prevTitle);
                    int resourceId = getResources().getIdentifier(prevAlbumCover, "drawable", getPackageName());
                    System.out.println("resource time " + resourceId);

                    if (resourceId != 0) {
                        album_cover.setImageResource(resourceId);
                    } else {
                        // Resource not found
                        // Handle this case as per your application logic
                        Toast.makeText(ChannelActivity.this, "Resource not found", Toast.LENGTH_SHORT).show();
                    }
                    AudioPlayer.getInstance(ChannelActivity.this).playRadioStream(prevUrl, ChannelActivity.this);
                }else{
                   //////////////////////////////////////
                    Channel prevTrack = channels.get(id);
                    System.out.println("THREE CLICKS");
                    String prevUrl = prevTrack.getUrl();
                    url=prevUrl;
                    String prevAlbumCover = prevTrack.getArtwork();
                    String prevTitle = prevTrack.getTitle();
                    title.setText(prevTitle);
                    int resourceId = getResources().getIdentifier(prevAlbumCover, "drawable", getPackageName());
                    System.out.println("resource time " + resourceId);

                    if (resourceId != 0) {
                        album_cover.setImageResource(resourceId);
                    } else {
                        // Resource not found
                        // Handle this case as per your application logic
                        Toast.makeText(ChannelActivity.this, "Resource not found", Toast.LENGTH_SHORT).show();
                    }
                    AudioPlayer.getInstance(ChannelActivity.this).playRadioStream(prevUrl, ChannelActivity.this);
                }
            }
        }
        else{
            AudioPlayer player= AudioPlayer.getInstance(ChannelActivity.this);
            player.playRadioStream(url,ChannelActivity.this);
            title.setText(titlePassed);
        }
        showNotification(R.drawable.baseline_pause_circle_outline_24,id,url,image,titlePassed);
        playButton.setImageResource(R.drawable.baseline_pause_circle_outline_24);
        isPlaying=true;
    }

    public void next(){
        if(isPlaying){
            id++;
            ArrayList<Channel> channels=TracksContext.getChanelList(ChannelActivity.this);
            if(id>channels.size()-1){
                id=0;
                Channel nextTrack=channels.get(id);
                String nextUrl=nextTrack.getUrl();
                url=nextUrl;
                String nextAlbumCover=nextTrack.getArtwork();
                String nextTitle=nextTrack.getTitle();
                title.setText(nextTitle);
                int resourceId = getResources().getIdentifier(nextAlbumCover, "drawable", getPackageName());
                System.out.println("resource time "+resourceId);

                if (resourceId != 0) {
                    album_cover.setImageResource(resourceId);
                } else {
                    // Resource not found
                    // Handle this case as per your application logic
                    Toast.makeText(ChannelActivity.this, "Resource not found", Toast.LENGTH_SHORT).show();
                }
                AudioPlayer.getInstance(ChannelActivity.this).playRadioStream(nextUrl, ChannelActivity.this);
            }else{
                Channel nextTrack=channels.get(id);
                String nextUrl=nextTrack.getUrl();
                url=nextUrl;
                String nextAlbumCover=nextTrack.getArtwork();
                String nextTitle=nextTrack.getTitle();
                title.setText(nextTitle);
                int resourceId = getResources().getIdentifier(nextAlbumCover, "drawable", getPackageName());
                System.out.println("resource time "+resourceId);

                if (resourceId != 0) {
                    album_cover.setImageResource(resourceId);
                } else {
                    // Resource not found
                    // Handle this case as per your application logic
                    Toast.makeText(ChannelActivity.this, "Resource not found", Toast.LENGTH_SHORT).show();
                }
                AudioPlayer.getInstance(ChannelActivity.this).playRadioStream(nextUrl, ChannelActivity.this);
            }

        }else{
            AudioPlayer player= AudioPlayer.getInstance(ChannelActivity.this);
            player.playRadioStream(url,ChannelActivity.this);
            title.setText(titlePassed);
        }
        showNotification(R.drawable.baseline_pause_circle_outline_24,id,url,image,titlePassed);
        playButton.setImageResource(R.drawable.baseline_pause_circle_outline_24);
        isPlaying=true;
    }


    public void showNotification(int playPause,int id,String url,String image,String title){
        Intent contextIntent=new Intent(this,ChannelActivity.class);

        contextIntent.putExtra("channelid",id);
        contextIntent.putExtra("streamurl",url);
        contextIntent.putExtra("imagepath",image);
        contextIntent.putExtra("titlepassed",title);




        contextIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contextpendingIntent=PendingIntent.getActivity(this,0,contextIntent, PendingIntent.FLAG_IMMUTABLE);

//        Intent contextIntent=new Intent(this,MainActivity.class);
//        PendingIntent contextpendingIntent=PendingIntent.getActivity(this,0,contextIntent, PendingIntent.FLAG_IMMUTABLE);


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
                .addAction(R.drawable.baseline_skip_previous_24,"previous",prevpendingIntent)
                .addAction(playPause,"play",playpendingIntent)
                .addAction(R.drawable.baseline_skip_next_24,"skip",nextpendingIntent)
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
        notificationservice.setCallBack(ChannelActivity.this);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        notificationservice=null;
        Log.e("DISCONNECTED ", notificationservice+"");
    }
}