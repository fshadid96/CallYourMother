package com.example.farahshadid.callyourmother;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * This code executes when the notification is clicked
 */
public class UpdateNotification extends Activity{

    private DatabaseReference mDatabase;
    private String TAG = "UpdateNotifications";

    //name of contact: owner is being reminded to call this name in notification
    private String nameBeingCalled = "";
    //Owner of the phone
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Notification has been clicked");

        String number = getIntent().getStringExtra("number");
        //This is name of the phones owner
        name = getDeviceName();

        //This is the name of the contact being called
        nameBeingCalled = getIntent().getStringExtra("nameBeingCalled");
        Log.i(TAG, "Name of person being called: "+nameBeingCalled);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(name).child("topContacts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (nameBeingCalled == snapshot.child("name").getValue()) {
                        Long amountAccepted = (Long) snapshot.child("amountAccepted").getValue();
                        amountAccepted += 1;
                        mDatabase.child("Users").child(name).child("topContacts").child(nameBeingCalled).child("amountAccepted").setValue(amountAccepted);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        sendReminderNotification(number);

    }


    private void sendReminderNotification(String number){

        //Intent that will be used if the notification is clicked
        Intent mNotificationIntent = new Intent(Intent.ACTION_DIAL);

        //String test = "tel:977777777";
        String formatNumber = "tel:" + number.replaceAll("[\\D]", "");
        Log.i(TAG, "FormatNumber: "+formatNumber+". Regular number: "+number);
        mNotificationIntent.setData(Uri.parse(formatNumber));

        if(mNotificationIntent.resolveActivity(getPackageManager()) != null){

        } else {
            Log.i(TAG, "Failure launching intent for ACTION_DIAL");
        }

        startActivity(mNotificationIntent);
        Log.i(TAG, "Call screen is open");
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
}
