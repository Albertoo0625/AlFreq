package com.example.alfreq;

import static com.example.alfreq.ChannelRecAdapter.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView ChannelList;
    private EditText SearchBar;

    private ImageView SearchIcon;

    private ArrayList<Channel>channels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ChannelList=findViewById(R.id.channelListRecView);
        SearchBar=findViewById(R.id.searchBar);
        SearchIcon=findViewById(R.id.searchIcon);
        ChannelRecAdapter adapter= new ChannelRecAdapter(this);
        channels=TracksContext.getChanelList(this);

        SearchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm=SearchBar.getText().toString().toLowerCase();
                ArrayList<Channel>filteredChannels=new ArrayList<>();
                for (Channel channel : channels){
                    if(searchTerm!=null){
                     if (channel.getTitle().toLowerCase().contains(searchTerm)) {
                        filteredChannels.add(channel); // Add the channel to the filtered list
                        channels=filteredChannels;
                        adapter.setChannels(channels);
                        ChannelList.setAdapter(adapter);
                        ChannelList.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                    }else{
                         Toast.makeText(MainActivity.this, "Channel Doesn't Exist", Toast.LENGTH_SHORT).show();
                     }
                }else{
                        Toast.makeText(MainActivity.this, "All channels showing", Toast.LENGTH_SHORT).show();
                        System.out.println("All channels showing");
                        channels=TracksContext.getChanelList(MainActivity.this);
                        adapter.setChannels(channels);
                        ChannelList.setAdapter(adapter);
                        ChannelList.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                    }
                }
            }
        });
       adapter.setChannels(channels);
       ChannelList.setAdapter(adapter);
       ChannelList.setLayoutManager(new GridLayoutManager(this,2));
    }
}