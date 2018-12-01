package com.example.farahshadid.callyourmother;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

                    InputStream stream = getImage(Long.parseLong(id));
                    Bitmap icon = null;
                    if (stream != null) {
                        icon = getCircleBitmap(BitmapFactory.decodeStream(stream));
                    }

                    Contact c = new Contact(Long.parseLong(id), name, temp, icon);
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

    public InputStream getImage(long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = getContentResolver().query(photoUri, new String[]{ContactsContract.Contacts.Photo.PHOTO},
                null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    return new ByteArrayInputStream(data);
                }
            }
        } finally {
            cursor.close();
        }
        return null;
    }

    // https://stackoverflow.com/questions/11932805/cropping-circular-area-from-bitmap-in-android
    public Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
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
