package com.example.farahshadid.callyourmother;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Receives the broadcast intent from AlarmManager and starts up IntentService
 */
public class MyAlarmReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 22322;
    private final String TAG = "MyAlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Alarm Received");
        Intent i = new Intent(context, MyService.class);
        context.startService(i);
    }
}
