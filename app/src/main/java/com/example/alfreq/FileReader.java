package com.example.alfreq;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class FileReader {

    public static ArrayList<Channel> readJsonFile(Context context, int resourceId) {
        ArrayList<Channel> channels = new ArrayList<>();

        try {
            // Open the JSON file from resources as an InputStream
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Read the JSON content line by line
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            reader.close();

            // Use Gson to deserialize JSON into ArrayList<Channel>
            Gson gson = new Gson();
            Type channelListType = new TypeToken<ArrayList<Channel>>(){}.getType();
            channels = gson.fromJson(jsonString.toString(), channelListType);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading JSON file");
        }

        return channels;
    }
}
