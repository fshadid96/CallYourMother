package com.example.farahshadid.callyourmother;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StatsActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_stats);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // what do you want here
            }
        });

        userName = "Luke";
        mDatabase = FirebaseDatabase.getInstance().getReference();

        person1CallsMade = findViewById(R.id.person1CallsMade);
        person1Notifications = findViewById(R.id.person1Notifications);
        person2CallsMade = findViewById(R.id.person2CallsMade);
        person2Notifications = findViewById(R.id.person2Notifications);
        person3CallsMade = findViewById(R.id.person3CallsMade);
        person3Notifications = findViewById(R.id.person3Notifications);
        person4CallsMade = findViewById(R.id.person4CallsMade);
        person4Notifications = findViewById(R.id.person4Notifications);
        person5CallsMade = findViewById(R.id.person5CallsMade);
        person5Notifications = findViewById(R.id.person5Notifications);

        statsCircle = findViewById(R.id.statsCircle);

        person1Name = findViewById(R.id.person1Name);
        person2Name = findViewById(R.id.person2Name);
        person3Name = findViewById(R.id.person3Name);
        person4Name = findViewById(R.id.person4Name);
        person4Name = findViewById(R.id.person5Name);


        mDatabase.child("Users").child(userName).child("topContacts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                totalNotifications = 0;
                totalCallsMade = 0;
                int counter = 0; //Will use this to tell which textViews to update
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

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
                    //Log.i(TAG,totalNotifications.toString());
                }
                percentage = (totalCallsMade * 100.0f) / totalNotifications;
                GradientDrawable statsCircleBackground = (GradientDrawable) statsCircle.getBackground();
                if (percentage <= 75.0 && percentage >= 25.0) {
                    statsCircleBackground.setStroke(15,Color.YELLOW);
                    statsCircle.setTextColor(Color.BLACK);
                }
                else if (percentage <25.0) {
                    statsCircleBackground.setStroke(15,Color.RED);
                    statsCircle.setTextColor(Color.RED);
                }
                else {
                    statsCircleBackground.setStroke(15,Color.GREEN);
                    statsCircle.setTextColor(Color.GREEN);
                }
                statsCircle.setText(percentage + "%");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








    }

}
