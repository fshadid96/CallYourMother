package com.example.farahshadid.callyourmother;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    public String username;
    public ArrayList<Contact> topContacts;
    public int commitmentScore;
    private DatabaseReference mDatabase;
    public static FirebaseDatabase database;
    private static final String TAG = "Firebase Activity: ";

    public User(String username,ArrayList<Contact> topContacts){
        this.username = username;
        this.topContacts = new ArrayList<Contact>();
        this.commitmentScore = 0;
        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot();
        database = FirebaseDatabase.getInstance();

    }

    /**
     * add a new user under users in the database with a commitment Score of 0
     * @param userId
     * @param topContactsList
     */
    public void writeNewUser(String userId, ArrayList<Contact> topContactsList) {
        User user = new User(userId, topContactsList);
        mDatabase.child("Users").child(userId).setValue(user);

        for(Contact contact: topContactsList){
            mDatabase.child("Users").child(userId).child("topContacts").child(contact.getName()).setValue(contact);
        }
        Log.v(TAG, "inserted " + userId + " to the database");
    }

    /**
     * get the commitment score of this user
     * @return
     */
    public int getCommitmentScore() {

        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().getRoot().child("Users").child(this.username);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("commitmentScore").exists()) {
                    int commitmentScore = dataSnapshot.child("commitmentScore").getValue(Integer.class);
                }
                System.out.println("The commitment Score: "  + commitmentScore);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return commitmentScore;

    }

    public ArrayList<Contact> getTopContacts() {
        return topContacts;
    }


    /**
     * remove a user from the data base
     * @param user
     */

    public void removeUser(User user){
        mDatabase.child("Users").child(user.username).removeValue();
        Log.v(TAG, "removed " + user.username + " from the database");
    }

    /**
     * update the commitmentScore of a user
     * @param user
     * @param newCommitmentScore
     */
    public void updateCommitmentScore(User user,int newCommitmentScore){
        mDatabase.child("Users").child(user.username).child("commitmentScore").setValue(newCommitmentScore);
        Log.v(TAG, "Updated " + user.username + "'s from the database");
    }

}
