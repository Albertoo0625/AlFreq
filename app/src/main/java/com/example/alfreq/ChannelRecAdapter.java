package com.example.alfreq;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChannelRecAdapter extends RecyclerView.Adapter<ChannelRecAdapter.ChannelAdaptor>{
    private ArrayList<Channel> channels = new ArrayList<>();
    private Context context;
    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;

    }

    public ChannelRecAdapter(Context context) {
        this.context=context;
    }


    @NonNull
    @Override
    public ChannelAdaptor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.channellist,parent,false);
        ChannelAdaptor holder= new ChannelAdaptor(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelAdaptor holder,int position) {
        holder.listItem.setText(channels.get(position).getTitle());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, channels.get(holder.getAdapterPosition()).getUrl(), Toast.LENGTH_SHORT).show();
                String radioStreamUrl = channels.get(holder.getAdapterPosition()).getUrl();
                System.out.println(radioStreamUrl);
                playRadioStream(radioStreamUrl);
            }

//            private void playRadioStream(String radioStreamUrl) {
//                try {
//                    MediaPlayer mediaPlayer = new MediaPlayer();
//                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                    mediaPlayer.setDataSource(radioStreamUrl);
//                    mediaPlayer.prepare();
//                    mediaPlayer.start();
//                } catch (Exception e) {
//                    Toast.makeText(context, "Error playing radio stream", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//            }


            private void playRadioStream(String radioStreamUrl) {
                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(radioStreamUrl);

                    // Set error listener to capture errors during playback
                    mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            Toast.makeText(context, "Error playing radio stream", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    });

                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            // Start playback when prepared
                            mediaPlayer.start();
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(context, "Error playing radio stream", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public class ChannelAdaptor extends RecyclerView.ViewHolder {
        private TextView listItem,emailText;
        private CardView parent;


        public ChannelAdaptor(@NonNull View itemView) {
            super(itemView);
            listItem=itemView.findViewById(R.id.listitem);
            parent=itemView.findViewById(R.id.parent);
            emailText=itemView.findViewById(R.id.emailTxt);
        }
    }
}
