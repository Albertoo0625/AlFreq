package com.example.alfreq;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.Toast;

public class AudioPlayer {

    private static AudioPlayer instance;
    private MediaPlayer mediaPlayer;

    private AudioPlayer(Context context) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // Set error listener to capture errors during playback
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(context, "Error playing radio stream", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public static synchronized AudioPlayer getInstance(Context context) {
        if (instance == null) {
            instance = new AudioPlayer(context);
        }
        return instance;
    }

    public void playRadioStream(String radioStreamUrl, final Context context) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(radioStreamUrl);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // Start playback when prepared
                    mediaPlayer.start();
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

    // Method to release the MediaPlayer instance
    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
