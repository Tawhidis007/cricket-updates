package com.asymptote.bails;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("koi_asi", "Receiver Triggered");

        Bundle bundle = intent.getExtras();
        int position = (int) bundle.get("position");
        Log.i("koi_asi", "carrying position : "+position);
        Intent i = new Intent(context, NotificationService.class);
        i.putExtra("position",position);
        context.startService(i);
    }
}
