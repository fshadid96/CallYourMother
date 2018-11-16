package com.example.farahshadid.callyourmother;

import android.nfc.Tag;
import android.widget.Toast;
import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class User {
    public String username;
    public ArrayList<String> topContacts;
    public int commitmentScore;

    private DatabaseReference mDatabase;
    private static final String TAG = "Firebase Problem";

    //Default Constructor
    public User(){

    }

    public User(String username,String[] topContacts, int commitmentScore){
        this.username = username;
        this.topContacts = new ArrayList<String>();
        this.commitmentScore = commitmentScore;

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void writeNewUser(String userId, String[] topContactsList) {
        User user = new User(userId,topContactsList,0);

        mDatabase.child("users").child(userId).setValue(user);
    }

//    private void addTopContact(User user, String topContact) {
//        if(user.topContacts.size() <= 5){
//            mDatabase.child("users").child(user.username).child("top contacts").setValue(user.topContacts.add(topContact));
//        }
//        else{
//            // Add an alert dialouge
//           Log.e(TAG,"Please Remove a top contact");
//        }
//
//        }

    private void removeUser(User user){
        mDatabase.child("users").child(user.username).removeValue();
    }
    
    private void updateCommitmentScore(User user,int newCommitmentScore){
        mDatabase.child("users").child(user.username).child("commitment score").setValue(newCommitmentScore);
    }



//    private void removeTopContact(User user, String topContactToDelete){
//        int index = user.topContacts.indexOf(topContactToDelete);
//        user.topContacts.remove(index);
//        if(index != -1){
//            mDatabase.child("users").child("top contacts").removeValue(user.topContacts.remove(index));
//        }
//
//    }

    }
