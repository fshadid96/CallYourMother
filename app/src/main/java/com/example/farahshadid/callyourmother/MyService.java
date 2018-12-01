package com.example.farahshadid.callyourmother;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * This class will actually perform the work
 */

public class MyService extends IntentService{

    public MyService() {
        super("MyService");
    }

    /**
     * Work to be performed every time an alarm occurs. Calls notifications
     * class so notifications can be sent
     * @param intent
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Intent newIntent = new Intent(this, Notifications.class);
        startActivity(newIntent);
    }
}
