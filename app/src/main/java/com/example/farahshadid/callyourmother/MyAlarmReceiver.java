package com.example.farahshadid.callyourmother;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Receives the broadcast intent from AlarmManager and starts up IntentService
 */
public class MyAlarmReceiver extends BroadcastReceiver {

    private static final int MY_NOTIFICATION_ID = 1;
    private float commitmentScore;
    private String mChannelID;

    //TODO We need the name of the owner
    private String ownerName;
    private String phoneNumber;

    private float threshold = 30.0f;
    private DatabaseReference database;
    private static NotificationManager mNotificationManager;

    //Strings to be used for notifications
    private final CharSequence contentTitle = "CallBuddy";
    private  CharSequence contentText = " has not heard from you recently! Lets give them a call!";
    private Context mContext;

    public static final int REQUEST_CODE = 22322;
    private final String TAG = "MyAlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Alarm Received");

        mContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();


        //TODO Retrieve name of owner of phone
        //User user = new User();
        ownerName = getDeviceName();

        /**
         * Everytime alarm occurs
         *      Update amountNotified in Firebase because another notification has been sent (Increment)
         *      Update amountAccepted if notification has been clicked. (Increment)
         */

        database = FirebaseDatabase.getInstance().getReference();

        database.child("Users").child(ownerName).child("topContacts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Flag controls that only one notifcation is sent each time this code is run
                boolean flag = true;
                Long callsMade;
                Long notifications = 0l;
                String name = "";

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(flag == true) {
                        name = (String) snapshot.child("name").getValue();
                        callsMade = (Long) snapshot.child("amountAccepted").getValue();
                        notifications = (Long) snapshot.child("amountNotified").getValue();
                        phoneNumber = (String) snapshot.child("numbers").child("0").getValue();

                        commitmentScore = (callsMade * 100.0f) / notifications;
                        //Once one notification is sent about a contact, don't send any other notifications
                        //and break out of loop
                        if (commitmentScore < threshold) {
                            //Somehow retrieve contact name
                            flag = false;
                            sendReminderNotification(name);
                        }
                    }
                }
                if(flag == false) {
                    notifications += 1;
                    database.child("Users").child(ownerName).child("topContacts").child(name).child("amountNotified").setValue(notifications);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /**
     *Got the device name and to set it as a user
     *
     * Source: https://stackoverflow.com/questions/7071281/get-android-device-name
     * @return
     */
    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
    private void createNotificationChannel(){

        mChannelID =  ".channel_01";
        //TODO
        CharSequence name = "NotificationChannel";

        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        //Channel that will be used to send all Reminder notifications
        NotificationChannel mChannel = new NotificationChannel(mChannelID, name, importance);
        mNotificationManager.createNotificationChannel(mChannel);

    }

    /**
     * Function that will remind user to call a particular contact
     * Used when application has determined a sufficient amount of time
     * has passed. If the notification is clicked, it will take them to the
     * dial screen with the number already shown.
     * This intent that is with this notification will execute the UpdateNotification
     * class. This was the only way to accurately update Firebase if the notification was
     * clicked
     */
    private void sendReminderNotification(final String contactName){

        //Intent that will be used if the notification is clicked
        Intent mNotificationIntent = new Intent(mContext, UpdateNotification.class);

        mNotificationIntent.putExtra("number", phoneNumber);
        mNotificationIntent.putExtra("name", ownerName);
        mNotificationIntent.putExtra("nameBeingCalled", contactName);

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, mNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //append contact name to contentText
        String message = contactName + contentText;

        Notification.Builder notificationBuilder = new Notification.Builder(mContext, mChannelID)
                .setContentTitle(contentTitle).setAutoCancel(true).setContentText(message)
                .setContentIntent(pendingIntent).setSmallIcon(android.R.drawable.stat_sys_warning);


        mNotificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build());
        Log.i(TAG, "Notification has been sent to "+contactName);

    }
}
