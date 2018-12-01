package com.example.farahshadid.callyourmother;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Notifications extends Activity {

    private static final int MY_NOTIFICATION_ID = 1;
    private float commitmentScore;
    private String mChannelID;

    //TODO We need the name of the owner
    private String ownerName;

    private float threshold = 30.0f;
    private DatabaseReference mDatabase;
    private static NotificationManager mNotificationManager;

    private String TAG = "Notifications";

    //Strings to be used for notifications
    private final CharSequence contentTitle = "Reminder";
    private  CharSequence contentText = " has not heard from you recently! Lets give them a call!";


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();


        //TODO Retrieve name of owner of phone
        ownerName = "Adam";
        /**
         * Everytime alarm occurs
         *      Update amountNotified in Firebase because another notification has been sent (Increment)
         *      Update amountAccepted if notification has been clicked. (Increment)
         */
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(ownerName).child("topContacts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Flag controls that only one notifcation is sent each time this code is run
                boolean flag = true;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(flag) {
                        Long callsMade = (Long) snapshot.child("amountAccepted").getValue();
                        Long notifications = (Long) snapshot.child("amountNotified").getValue();
                        String name = (String) snapshot.child("name").getValue();

                        commitmentScore = (callsMade * 100.0f) / notifications;
                        //Once one notification is sent about a contact, don't send any other notifications
                        //and break out of loop
                        if (commitmentScore < threshold) {
                            //Somehow retrieve contact name
                            sendReminderNotification(name);
                            flag = false;

                            //Update amountNotified on Firebase
                            notifications += 1;
                            mDatabase.child("Users").child(ownerName).child("topContacts").child(name).child("amountNotified").setValue(notifications);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void createNotificationChannel(){

        mChannelID = getPackageName() + ".channel_01";
        //TODO
        CharSequence name = "NotificationChannel";

        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

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
    private void sendReminderNotification(String contactName){

        //Intent that will be used if the notification is clicked
        Intent mNotificationIntent = new Intent(getApplicationContext(), UpdateNotification.class);

        ContactActivity activity = new ContactActivity();
        //TODO Retrieve phone number of contactName from User class
        String number = "5555";

        mNotificationIntent.putExtra("number", number);
        mNotificationIntent.putExtra("name", ownerName);
        mNotificationIntent.putExtra("nameBeingCalled", contactName);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, mNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //append contact name to contentText
        String message = contactName + contentText;

        Notification.Builder notificationBuilder = new Notification.Builder(getApplicationContext(), mChannelID)
                .setContentTitle(contentTitle).setAutoCancel(true).setContentText(message)
                .setContentIntent(pendingIntent).setSmallIcon(android.R.drawable.stat_sys_warning);


        mNotificationManager.notify(MY_NOTIFICATION_ID, notificationBuilder.build());
        Log.i(TAG, "Notification has been sent.");
    }


}
