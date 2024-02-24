package com.example.alfreq;

import static com.example.alfreq.ChannelRecAdapter.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView ChannelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ChannelList=findViewById(R.id.channelListRecView);

        ArrayList<Channel> channels= new ArrayList<Channel>();
        channels.add(new Channel("0","http://24-7nicheradio.com:8200","24-7 Reggae","Me","https://myradiostation.com/logo.png"));
        channels.add(new Channel("1","http://www.24-7nicheradio.com:8270/","24-7 Best Of The 80s","Me","https://myradiostation.com/logo.png"));
        channels.add(new Channel("2","https://atunwadigital.streamguys1.com/capitalfm","Capital FM Kenya","Me","https://myradiostation.com/logo.png"));
        channels.add(new Channel("3","http://classic105-atunwadigital.streamguys1.com/classic105","Classic FM Kenya","Me","https://myradiostation.com/logo.png"));
        channels.add(new Channel("4","http://46.105.126.68:7304/;stream.mp3","24.7 The Mix - Sudbury, ON","Me","https://myradiostation.com/logo.png"));
        channels.add(new Channel("5","http://media-ice.musicradio.com/CapitalMP3","Capital FM London","Me","https://myradiostation.com/logo.png"));
        channels.add(new Channel("6","http://ice-sov.musicradio.com/ClassicFMMP3","Classic FM London","Me","https://myradiostation.com/logo.png"));
        channels.add(new Channel("7","http://ice-the.musicradio.com/ClassicFMMP3","Classic FM UK","Me","https://myradiostation.com/logo.png"));
        channels.add(new Channel("8","https://atunwadigital.streamguys1.com/capitalfm","Capital FM Kenya","Me","https://myradiostation.com/logo.png"));

        ChannelRecAdapter adapter= new ChannelRecAdapter(this);
        adapter.setChannels(channels);

       ChannelList.setAdapter(adapter);
       ChannelList.setLayoutManager(new GridLayoutManager(this,2));


    }
}