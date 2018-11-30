package com.example.farahshadid.callyourmother;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {
    Toolbar toolbar;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ListAdapter adapter;
    final int READ_CONTACTS = 0;
    ArrayList<Contact> contacts;
    CoordinatorLayout parent;
    boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        contacts = new ArrayList<>();
        ArrayList<String> numbers  = new ArrayList<String>();
        numbers.add("!@#!@#");
        numbers.add("342" );
        numbers.add("234");

        Contact one = new Contact(4,"Jacob", numbers, 0,0);
        Contact two = new Contact(5,"Noah", numbers, 0,0);
        Contact three = new Contact(8,"Caleb", numbers, 0,0);
        contacts.add(one);
        contacts.add(two);
        contacts.add(three);

        User usr = new User("Luke", contacts);
        usr.writeNewUser(usr.username, contacts);
        started = true;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Choose Your Contacts");

        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        parent = findViewById(R.id.parent);

        contacts = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager
                .PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS);
        } else {
            getContacts();
        }

        adapter = new ListAdapter(contacts);
        recyclerView.setAdapter(adapter);

        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelected().size() > 0) {
                    pref.edit().putBoolean(SplashActivity.PREF_KEY, false).apply();
                    // Launch home activity
                    Intent i = new Intent(ContactActivity.this, HomeActivity.class);
                    i.putExtra("contacts", adapter.getSelected());
                    startActivity(i);
                    finish();
                } else {
                    Snackbar.make(view, "Please choose some contacts", Snackbar.LENGTH_SHORT).setAction("Action",
                            null).show();
                }
            }
        });
    }

    private void getContacts() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor != null && cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor cursor1 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);

                    ArrayList<String> temp = new ArrayList<>();
                    while (cursor1.moveToNext()) {
                        String number = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds
                                .Phone.NUMBER));
                        temp.add(number);
                    }

                    Contact c = new Contact(Integer.parseInt(id), name, temp,0 , 0);
                    contacts.add(c);
                    cursor1.close();
                }
            }
        }
        if (cursor != null) {
            cursor.close();
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContacts();
                } else {
                    fab.setEnabled(false);
                    Snackbar.make(parent, "Allow access to contacts", Snackbar.LENGTH_INDEFINITE).setAction("OK", new
                            View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fab.setEnabled(true);
                            if (ContextCompat.checkSelfPermission(ContactActivity.this, Manifest.permission
                                    .READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(ContactActivity.this, new String[]{Manifest
                                        .permission.READ_CONTACTS}, READ_CONTACTS);
                            } else {
                                getContacts();
                            }
                        }
                    }).show();
                }
            }
        }
    }
}
