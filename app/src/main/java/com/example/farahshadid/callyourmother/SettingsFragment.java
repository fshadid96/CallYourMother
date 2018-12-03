package com.example.farahshadid.callyourmother;

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
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    Button one;
    Button two;
    Button three;
    Button four;
    Button five;
    private DatabaseReference mDatabase;
    private FirebaseDatabase db;
    private static final String TAG = "Firebase Activity: ";
    private static final String DELETE = "delete ";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        one = view.findViewById(R.id.button1);
        one.setOnClickListener(this);
        two = view.findViewById(R.id.button2);
        two.setOnClickListener(this);
        three = view.findViewById(R.id.button3);
        three.setOnClickListener(this);
        four = view.findViewById(R.id.button4);
        four.setOnClickListener(this);
        five = view.findViewById(R.id.button5);
        five.setOnClickListener(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Users").child(getDeviceName()).child("topContacts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    int counter = 1;
                    for(DataSnapshot postSnapShot: dataSnapshot.getChildren()) {
                        if(postSnapShot.exists()) {
                            Long amountAccepted = (Long) postSnapShot.child("amountAccepted").getValue();
                            Long amountNotified = (Long) postSnapShot.child("amountNotified").getValue();
                            Long id  = (Long) postSnapShot.child("id").getValue();
                            String name = (String) postSnapShot.child("name").getValue();
                            ArrayList<String> nums = (ArrayList<String>) postSnapShot.child("numbers").getValue();
                           // Contact toAdd = new Contact(Math.toIntExact(id)  ,  name,nums, Math.toIntExact(amountAccepted) , Math.toIntExact(amountNotified), null);
                            //Log.v(TAG, "amount accepted" + toAdd.getName() + " from the database");
                          //  contacts.add(toAdd);
                            switch (counter) {
                                case 1:
                                    one.setText(name);
                                    break;
                                case 2:
                                    two.setText(name);
                                    break;
                                case 3:
                                    three.setText(name);
                                    break;
                                case 4:
                                    four.setText(name);
                                    break;
                                case 5:
                                    five.setText(name);
                                    break;
                            }

                            counter++;
                        }
                    }
                }
//                Log.v(TAG, "amount accepted" + toAdd.getName() + " from the database");
//                for (Contact contact: contacts) {
//                    TextView aContact = view.findViewById(R.id.top_con1);
//                    aContact.setText(contact.getName());
//                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }


    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button1:
                deleteTopContact(one.getText().toString());
                one.setText(" EMPTY  ");

                break;

            case R.id.button2:
                deleteTopContact(two.getText().toString());
                v.setVisibility(View.GONE);
                break;

            case R.id.button3:
                deleteTopContact(three.getText().toString());
                break;

            case R.id.button4:
                deleteTopContact(four.getText().toString());
                break;

            case R.id.button5:
                deleteTopContact(five.getText().toString());
                break;
        }

    }



    public void deleteTopContact(String name){
        Log.v(TAG,  "deleting" + name);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().getRoot();
        mDatabase.child("Users").child(getDeviceName()).child("topContacts").child(name).removeValue();
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