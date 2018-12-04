package com.example.farahshadid.callyourmother;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;

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
    EditText notiNumView;
    Button selectContactForNotification;
    Button addContact;
    static int notiNum ;
    static int addCount = 0;
    private DatabaseReference mDatabase;
    private FirebaseDatabase db;
    private View mainView;
    private static final String TAG = "Firebase Activity: ";
    private static final String DELETE = "delete ";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainView = inflater.inflate(R.layout.fragment_settings, container, false);
        return setupUIPlusData(mainView);
    }

    /***
     * Created this method so that we can reset the UI every time a contact gets deleted.
     * @param view
     * @return
     */
    public View setupUIPlusData(View view) {
        one = view.findViewById(R.id.con_1);
        one.findViewById(R.id.con_1).setVisibility(View.GONE);
        one.setOnClickListener(this);

        two = view.findViewById(R.id.con_2);
        two.findViewById(R.id.con_2).setVisibility(View.GONE);
        two.setOnClickListener(this);

        three = view.findViewById(R.id.con_3);
        three.findViewById(R.id.con_3).setVisibility(View.GONE);
        three.setOnClickListener(this);

        four = view.findViewById(R.id.con_4);
        four.findViewById(R.id.con_4).setVisibility(View.GONE);
        four.setOnClickListener(this);

        five = view.findViewById(R.id.con_5);
        five.findViewById(R.id.con_5).setVisibility(View.GONE);
        five.setOnClickListener(this);

        selectContactForNotification = view.findViewById(R.id.selectContact);
        selectContactForNotification.setOnClickListener(this);

        addContact = view.findViewById(R.id.addContact);
        addContact.setOnClickListener(this);




        notiNumView =  (EditText) view.findViewById(R.id.inputBox);
        notiNumView.setText("12");
        notiNum = Integer.parseInt(notiNumView.getText().toString());
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("Users").child(getDeviceName()).child("topContacts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    addCount = 0;
                    int counter = 1;
                    for(DataSnapshot postSnapShot: dataSnapshot.getChildren()) {
                        if(postSnapShot.exists()) {
                            String name = (String) postSnapShot.child("name").getValue();
                            ArrayList<String> nums = (ArrayList<String>) postSnapShot.child("numbers").getValue();
                            // Contact toAdd = new Contact(Math.toIntExact(id)  ,  name,nums, Math.toIntExact(amountAccepted) , Math.toIntExact(amountNotified), null);
                            //Log.v(TAG, "amount accepted" + toAdd.getName() + " from the database");
                            //  contacts.add(toAdd);
                            switch (counter) {
                                case 1:
                                    one.setText(name);
                                    one.findViewById(R.id.con_1).setVisibility(View.VISIBLE);
                                    break;
                                case 2:
                                    two.setText(name);
                                    two.findViewById(R.id.con_2).setVisibility(View.VISIBLE);
                                    break;
                                case 3:
                                    three.setText(name);
                                    three.findViewById(R.id.con_3).setVisibility(View.VISIBLE);
                                    break;
                                case 4:
                                    four.setText(name);
                                    four.findViewById(R.id.con_4).setVisibility(View.VISIBLE);
                                    break;
                                case 5:
                                    five.setText(name);
                                    five.findViewById(R.id.con_5).setVisibility(View.VISIBLE);
                                    break;
                            }

                            counter++;
                        }
                        addCount += 1;
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

            case R.id.con_1:
                deleteTopContact(one.getText().toString());
                setupUIPlusData(mainView);
                addCount--;
                break;

            case R.id.con_2:
                deleteTopContact(two.getText().toString());
                setupUIPlusData(mainView);
                addCount--;
                break;

            case R.id.con_3:
                deleteTopContact(three.getText().toString());
                setupUIPlusData(mainView);
                addCount--;
                break;

            case R.id.con_4:
                deleteTopContact(four.getText().toString());
                addCount--;
                break;

            case R.id.con_5:
                deleteTopContact(five.getText().toString());
                addCount--;
                break;

            case R.id.selectContact:
                    Intent notify = new Intent(getContext(), UpdateNotification.class);
                    startActivity(notify);
                    break;

            case R.id.addContact:
                if(addCount < 5) {
                    Intent addContact = new Intent(getContext(), AddContactActivity.class);
                    startActivity(addContact);
                } else {
                    Snackbar.make(v, "You already have 5 contacts selected", Snackbar.LENGTH_SHORT).setAction("Action",
                            null).show();
                }
                break;
        }

    }



    public void deleteTopContact(String name){
        Log.v(TAG,  "deleting" + name);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().getRoot();
        mDatabase.child("Users").child(getDeviceName()).child("topContacts").child(name).removeValue();
        setupUIPlusData(mainView);
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