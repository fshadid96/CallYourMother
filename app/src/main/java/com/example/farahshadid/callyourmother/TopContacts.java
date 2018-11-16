package com.example.farahshadid.callyourmother;

import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class TopContacts {
    public String user;
    public String contactName;
    public int amountAccepted;
    public int amountNotified;

    private DatabaseReference mDatabase;
    private static final String TAG = "Firebase Problem";

    public TopContacts(String user, String contactName, int amountAccepted, int amountNotified) {
        this.user = user;
        this.contactName = contactName;
        this.amountAccepted = amountAccepted;
        this.amountNotified = amountNotified;

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * add a top contact for a user
     *
     *
     * @param user
     * @param contactName
     * @param amountAccepted
     * @param amountNotified
     */
    public void writeNewContact(String user, String contactName, int amountAccepted, int amountNotified) {
        TopContacts contact = new TopContacts(user,contactName,amountAccepted,amountNotified);
        mDatabase.child("topContacts").child(user).child(contactName).setValue(contact);
        Log.v(TAG, "inserted " + contactName+ " to the database under " + user + "'s list");
    }

    /**
     * remove a top contact for a user
     *
     * @param username
     * @param contactName
     */
    public void removeTopContact(String username, String contactName){
        mDatabase.child("topContacts").child(user).child(contactName).removeValue();
        Log.v(TAG, "removed " + contactName + " from the database");
    }


    /*
    private void addTopContact(User user, String userId, String topContact) {
        if(user.topContacts.size() <= 5){
            mDatabase.child("topContacts").child(userId).child("topContacts").setValue(topContact);
            mDatabase.child("users").child(user.username).child("topContacts").setValue(user.topContacts.add(topContact));
        }
        else{
            // Add an alert dialouge
            Log.e(TAG,"Please Remove a top contact");
        }
    }*/





}