package com.example.farahshadid.callyourmother;

import android.util.Log;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


public class TopContacts extends  User{
    public String user;
    public String contactName;
    public int amountAccepted;
    public int amountNotified;

    private DatabaseReference mDatabase;
    private static final String TAG = "Firebase Problem";

    //Default Constructor
    public TopContacts() {

    }

    public TopContacts(String user, String contactName, int amountAccepted, int amountNotified) {
        this.user = user;
        this.contactName = contactName;
        this.amountAccepted = amountAccepted;
        this.amountNotified = amountNotified;

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void writeNewContact(String user, String contactName, int amountAccepted, int amountNotified) {
        TopContacts contact = new TopContacts(user,contactName,amountAccepted,amountNotified);

        mDatabase.child("Top Contact").child(user).setValue(contact);
    }

    private void addTopContact(User user, String userId, String topContact) {
        if(user.topContacts.size() <= 5){
            mDatabase.child("Top Contacts").child(userId).child("top contacts").setValue(topContact);
        }
        else{
            // Add an alert dialouge
           Log.e(TAG,"Please Remove a top contact");
        }

        }





}
