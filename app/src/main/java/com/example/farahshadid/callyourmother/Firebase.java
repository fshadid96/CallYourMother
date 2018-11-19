package com.example.farahshadid.callyourmother;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Firebase {



    /*
        This class is meant for method to interact with the database
        No Methods have been written yet.
        We are in the process of figure the structure

     */

//    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
//    private static final String TAG = "MainActivity";
//    //        // Write a message to the database
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference("message");
////
////        myRef.setValue("Hello, World!");
//
//
//    // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            // This method is called once with the initial value and again
//            // whenever data at this location is updated.
//            String value = dataSnapshot.getValue(String.class);
//            Log.d(TAG, "Value is: " + value);
//        }
//
//        @Override
//        public void onCancelled(DatabaseError error) {
//            // Failed to read value
//            Log.w(TAG, "Failed to read value.", error.toException());
//        }
//    });

    private static final String TAG = "Firebase Action";


    public void insertContact() {

    }

    public void getContact() {

    }

    public void insertCommtmentScore(){

    }

    public void insertContactObject() {

    }
    public void getContactObject() {

    }


}
