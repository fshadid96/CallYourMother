package com.example.farahshadid.callyourmother;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StatsFragment extends Fragment {

    private String TAG = "callyourmother";
    private DatabaseReference mDatabase;
    private FirebaseDatabase db;
    private int totalCallsMade;
    private int totalNotifications;

    TextView person1CallsMade;
    TextView person1Notifications;
    TextView person2CallsMade;
    TextView person2Notifications;
    TextView person3CallsMade;
    TextView person3Notifications;
    TextView person4CallsMade;
    TextView person4Notifications;
    TextView person5CallsMade;
    TextView person5Notifications;

    TextView person1Name;
    TextView person2Name;
    TextView person3Name;
    TextView person4Name;
    TextView person5Name;

    Button statsCircle;
    String userName;
    float percentage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_stats, container, false);

        userName = getDeviceName();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        person1CallsMade = v.findViewById(R.id.person1CallsMade);
        person1Notifications = v.findViewById(R.id.person1Notifications);
        person2CallsMade = v.findViewById(R.id.person2CallsMade);
        person2Notifications = v.findViewById(R.id.person2Notifications);
        person3CallsMade = v.findViewById(R.id.person3CallsMade);
        person3Notifications = v.findViewById(R.id.person3Notifications);
        person4CallsMade = v.findViewById(R.id.person4CallsMade);
        person4Notifications = v.findViewById(R.id.person4Notifications);
        person5CallsMade = v.findViewById(R.id.person5CallsMade);
        person5Notifications = v.findViewById(R.id.person5Notifications);

        statsCircle = v.findViewById(R.id.statsCircle);

        person1Name = v.findViewById(R.id.person1Name);
        person2Name = v.findViewById(R.id.person2Name);
        person3Name = v.findViewById(R.id.person3Name);
        person4Name = v.findViewById(R.id.person4Name);
        person5Name = v.findViewById(R.id.person5Name);

        // Going through database to update the statistics and commitment score.
        mDatabase.child("Users").child(userName).child("topContacts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                totalNotifications = 0;
                totalCallsMade = 0;
                percentage = 0;
                int counter = 0; //Will use this to figure out which textViews to update
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.i(TAG,"ENTERING LOOP");
                    Long callsMade = (Long) postSnapshot.child("amountAccepted").getValue();
                    Long notifications = (Long) postSnapshot.child("amountNotified").getValue();
                    String personName = (String) postSnapshot.child("name").getValue();

                    if (!(callsMade == null || notifications == null || personName == null)) {
                        totalNotifications += notifications;
                        totalCallsMade += callsMade;
                        switch (counter) {
                            case 0:
                                person1CallsMade.setText(callsMade.toString());
                                person1Notifications.setText(notifications.toString());
                                person1Name.setText(personName);
                                break;
                            case 1:
                                person2CallsMade.setText(callsMade.toString());
                                person2Notifications.setText(notifications.toString());
                                person2Name.setText(personName);
                                break;
                            case 2:
                                person3CallsMade.setText(callsMade.toString());
                                person3Notifications.setText(notifications.toString());
                                person3Name.setText(personName);
                                break;
                            case 3:
                                person4CallsMade.setText(callsMade.toString());
                                person4Notifications.setText(notifications.toString());
                                person4Name.setText(personName);
                                break;
                            case 4:
                                person5CallsMade.setText(callsMade.toString());
                                person5Notifications.setText(notifications.toString());
                                person5Name.setText(personName);
                                break;
                        }
                        counter += 1;
                    }
                    else {
                        Log.i(TAG,"THINGS WERE NULL");
                    }
                }
                if(totalNotifications == 0){
                    percentage = 0;
                }
                else {
                    percentage = (totalCallsMade * 100.0f) / totalNotifications;
                    GradientDrawable statsCircleBackground = (GradientDrawable) statsCircle.getBackground();
                    if (percentage <= 75.0 && percentage >= 25.0) {
                        statsCircleBackground.setStroke(15, Color.YELLOW);
                        statsCircle.setTextColor(Color.BLACK);
                    } else if (percentage < 25.0) {
                        statsCircleBackground.setStroke(15, Color.RED);
                        statsCircle.setTextColor(Color.RED);
                    } else {
                        statsCircleBackground.setStroke(15, Color.GREEN);
                        statsCircle.setTextColor(Color.GREEN);
                    }
                }
                statsCircle.setText((int)percentage + "%");
                // Updating db for user's commitment score.
                mDatabase.child("Users").child(userName).child("commitmentScore").setValue(percentage);
    }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG,"Something Bad Happened!");
            }
        });
        return v;
    }

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
