package com.example.alfreq;

import static com.example.alfreq.ChannelRecAdapter.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView ChannelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ChannelList=findViewById(R.id.channelListRecView);
        ChannelRecAdapter adapter= new ChannelRecAdapter(this);
        ArrayList<Channel>channels=TracksContext.getChanelList(this);
        adapter.setChannels(channels);



       ChannelList.setAdapter(adapter);
       ChannelList.setLayoutManager(new GridLayoutManager(this,2));


    }
}