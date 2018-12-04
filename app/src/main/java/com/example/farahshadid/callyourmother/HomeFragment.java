package com.example.farahshadid.callyourmother;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private DatabaseReference mDatabase;
    private TextView topConButton1, topConButton2, topConButton3, topConButton4,
            topConButton5;
    private String userName, uname1, uname2, uname3, uname4, uname5,
            num1, num2, num3, num4, num5;
    private String TAG = "HomeFragment";
    private int b1 = 0, b2 = 0, b3 = 0, b4 = 0, b5 = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        topConButton1 = v.findViewById(R.id.top_con1);
        topConButton2 = v.findViewById(R.id.top_con2);
        topConButton3 = v.findViewById(R.id.top_con3);
        topConButton4 = v.findViewById(R.id.top_con4);
        topConButton5 = v.findViewById(R.id.top_con5);

        userName = getDeviceName();   // Placeholder
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //=========================================== Top Contacts
        mDatabase.child("Users").child(userName).child("topContacts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = 0;

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                    // This gets the name of the on of the top contacts and will be used to get the
                    // the number
                    String personName = (String) childSnapshot.child("name").getValue();
                    String personNumber = (String) childSnapshot.child("numbers").child("0").getValue();

                    Log.i(TAG, "The person is: " + personName);
                    Log.i(TAG, "the number is: " + personNumber);
                    switch (count) {
                        case 0:
                            uname1 = personName;
                            num1 = personNumber;

                            topConButton1.setText(personName);
                            topConButton1.setVisibility(View.VISIBLE);
                            topConButton1.setOnClickListener(HomeFragment.this);
                            break;
                        case 1:
                            uname2 = personName;
                            num2 = personNumber;

                            topConButton2.setText(personName);
                            topConButton2.setVisibility(View.VISIBLE);
                            topConButton2.setOnClickListener(HomeFragment.this);
                            break;
                        case 2:
                            uname3 = personName;
                            num3 = personNumber;

                            topConButton3.setText(personName);
                            topConButton3.setVisibility(View.VISIBLE);
                            topConButton3.setOnClickListener(HomeFragment.this);
                            break;
                        case 3:
                            uname4 = personName;
                            num4 = personNumber;

                            topConButton4.setText(personName);
                            topConButton4.setVisibility(View.VISIBLE);
                            topConButton4.setOnClickListener(HomeFragment.this);
                            break;
                        case 4:
                            uname5 = personName;
                            num5 = personNumber;

                            topConButton5.setText(personName);
                            topConButton5.setVisibility(View.VISIBLE);
                            topConButton5.setOnClickListener(HomeFragment.this);
                            break;
                    }
                    count += 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG, "Something kinda Bad Happened!");
            }
        });

        return v;
    }

//======================================= getDeviceName ============================================
    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }
//======================================== capitalize ==============================================
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
//========================================= onClick ================================================
    @Override
    public void onClick(View v) {

        Log.i(TAG, "method is doing stuff supposely");

        switch (v.getId()) {
            case R.id.top_con1:
                if (b1 == 0) {
                    topConButton1.setText(num1);
                    b1 = 1;
                } else {
                    topConButton1.setText(uname1);
                    b1 = 0;
                }
                break;

            case R.id.top_con2:
                if (b2 == 0) {
                    topConButton2.setText(num2);
                    b2 = 1;
                } else {
                    topConButton2.setText(uname2);
                    b2 = 0;
                }
                break;

            case R.id.top_con3:
                if (b3 == 0) {
                    topConButton3.setText(num3);
                    b3 = 1;
                } else {
                    topConButton3.setText(uname3);
                    b3 = 0;
                }
                break;

            case R.id.top_con4:
                if (b4 == 0) {
                    topConButton4.setText(num4);
                    b4 = 1;
                } else {
                    topConButton4.setText(uname4);
                    b4 = 0;
                }
                break;

            case R.id.top_con5:
                if (b5 == 0) {
                    topConButton5.setText(num5);
                    b5 = 1;
                } else {
                    topConButton5.setText(uname5);
                    b5 = 0;
                }
                break;
        }
    }
}
