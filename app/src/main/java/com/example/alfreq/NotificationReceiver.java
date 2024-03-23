package com.example.alfreq;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String ACTION_PLAY="PLAY";
    private static final String ACTION_PREV="PREV";
    private static final String ACTION_NEXT="NEXT";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction() != null){
            switch (intent.getAction())
            {
                case ACTION_PREV:
                    Toast.makeText(context, "PREV PRESSED", Toast.LENGTH_SHORT).show();
                    break;
                case ACTION_PLAY:
                    Toast.makeText(context, "PLAY PRESSED", Toast.LENGTH_SHORT).show();
                    break;
                case ACTION_NEXT:
                    Toast.makeText(context, "NEXT PRESSED", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
