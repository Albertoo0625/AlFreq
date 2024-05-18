package com.example.alfreq;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.io.IOException;

public class AudioPlayer implements AudioManager.OnAudioFocusChangeListener{

    private static AudioPlayer instance;
    private MediaPlayer mediaPlayer;
    private Context context;

    private AudioManager audioManager;
    private String url;

    private AudioPlayer(Context context) {
        this.context=context.getApplicationContext();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        // Set error listener to capture errors during playback
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(context, "Error playing radio stream handle media player on error", Toast.LENGTH_SHORT).show();
                AudioPlayer.getInstance(context);
                resetPlayer();
                return true;
            }
        });
    }


    private void resetPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
        }
        instance = new AudioPlayer(context);
    }

    public static synchronized AudioPlayer getInstance(Context context) {
        if (instance == null) {
            instance = new AudioPlayer(context);
        }
        return instance;
    }

    public String getUrl(String urls){
        return urls;
    }
    public void playRadioStream(String radioStreamUrl, final Context context) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(radioStreamUrl);
            mediaPlayer.prepareAsync();
            url=getUrl(radioStreamUrl);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // Start playback when prepared
                    int result = audioManager.requestAudioFocus(AudioPlayer.this,
                            AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        // Start playback when prepared
                        mediaPlayer.start();
                    } else {
                        // Failed to gain audio focus
                        Toast.makeText(context, "Failed to gain audio focus", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        } catch (Exception e) {
            Toast.makeText(context, "Error playing radio stream", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            playRadioStream(radioStreamUrl,context);
        }
    }

    // Method to stop playback
    public void stopPlayback() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public void pausePlayback() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    // Method to release the MediaPlayer instance
    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void fadeOutMediaPlayer(){
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(0.1f, 0.1f);
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        AudioPlayer audio=AudioPlayer.getInstance(context);
           switch(focusChange){
               case AudioManager.AUDIOFOCUS_GAIN:
                   audio.playRadioStream(url,context);
                   break;

               case AudioManager.AUDIOFOCUS_LOSS:
                 audio.stopPlayback();
                 audio.releaseMediaPlayer();
                 break;

               case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    audio.pausePlayback();
                   break;

               case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                   audio.fadeOutMediaPlayer();
                   break;
           }
    }
}
