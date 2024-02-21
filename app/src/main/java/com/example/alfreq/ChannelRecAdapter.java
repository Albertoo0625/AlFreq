package com.example.alfreq;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public class ChannelAdaptor extends RecyclerView.ViewHolder {
        private TextView listItem;
        private RelativeLayout parent;
        public ChannelAdaptor(@NonNull View itemView) {
            super(itemView);
            listItem=itemView.findViewById(R.id.listitem);
            parent=itemView.findViewById(R.id.parent);
        }
    }
}
