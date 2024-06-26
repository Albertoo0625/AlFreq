package com.example.alfreq;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
 if(channels!= null && channels.get(position) != null){
        holder.listItem.setText(channels.get(position).getTitle());
        holder.title.setText(channels.get(position).getTitle());
        int resourceId = context.getResources().getIdentifier(channels.get(position).getArtwork(), "drawable", context.getPackageName());
        if (resourceId != 0) {
            holder.image.setImageResource(resourceId);
        } else {
            // Handle case when resource is not found
            holder.image.setImageResource(R.drawable.ic_launcher_background);
        }
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, channels.get(holder.getAdapterPosition()).getUrl(), Toast.LENGTH_SHORT).show();
//                String radioStreamUrl = channels.get(holder.getAdapterPosition()).getUrl();
//                System.out.println(radioStreamUrl);
//                AudioPlayer.getInstance(context).playRadioStream(radioStreamUrl,context);
                Intent intent = new Intent(context, ChannelActivity.class);
                intent.putExtra("channelId", holder.getAdapterPosition());
                intent.putExtra("streamurl", channels.get(holder.getAdapterPosition()).getUrl());
                intent.putExtra("imagepath", channels.get(holder.getAdapterPosition()).getArtwork());
                intent.putExtra("titlepassed", channels.get(holder.getAdapterPosition()).getTitle());
                context.startActivity(intent);
            }
        });

    }else{
     Toast.makeText(context, "Channel list done", Toast.LENGTH_SHORT).show();
 }
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public class ChannelAdaptor extends RecyclerView.ViewHolder {
        private TextView listItem,title;
        private CardView parent;

        private ImageView image;



        public ChannelAdaptor(@NonNull View itemView) {
            super(itemView);
            listItem=itemView.findViewById(R.id.listitem);
            parent=itemView.findViewById(R.id.parent);
            title=itemView.findViewById(R.id.title);
            image=itemView.findViewById(R.id.imagevw);
        }
    }
}
