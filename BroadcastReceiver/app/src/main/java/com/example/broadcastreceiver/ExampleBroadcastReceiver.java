package com.example.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ExampleBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)){
            Toast.makeText(context, "Change plugged in ", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Change plugged out ", Toast.LENGTH_SHORT).show();
        }
    }
}
