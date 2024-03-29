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
        Intent intent1=new Intent(context,NotificationService.class);
        if(intent.getAction() != null){
            switch (intent.getAction())
            {
                case ACTION_PREV:
                    Toast.makeText(context, "PREV PRESSED", Toast.LENGTH_SHORT).show();
                    intent1.putExtra("my_action",intent.getAction());
                    context.startService(intent1);
                    break;
                case ACTION_PLAY:
                    Toast.makeText(context, "PLAY PRESSED", Toast.LENGTH_SHORT).show();
                    intent1.putExtra("my_action",intent.getAction());
                    context.startService(intent1);
                    break;
                case ACTION_NEXT:
                    Toast.makeText(context, "NEXT PRESSED", Toast.LENGTH_SHORT).show();
                    intent1.putExtra("my_action",intent.getAction());
                    context.startService(intent1);
                    break;
            }
        }
    }
}
