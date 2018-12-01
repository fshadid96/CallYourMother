package com.example.farahshadid.callyourmother;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(nListner);

        //This makes sure that when the app starts from the home page that the home fragment is the
        // first thing that shows
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

        /**
         * @author Martin Palacios - PROBLEMATIC CODE
         * Code that will run the notifications class in the background

        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long currentTime = System.currentTimeMillis();
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Log.i("HomeActivity", "Bout to set alarm");
        alarm.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 10 * 1000, pendingIntent);
        */

    }

    // This is the listener that makes sure that allows the fragments to switch from one another
    private BottomNavigationView.OnNavigationItemSelectedListener nListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFrag = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFrag = new HomeFragment();
                            break;

                        case R.id.nav_stats:
                            selectedFrag = new StatsFragment();
                            break;

                        case R.id.nav_settings:
                            selectedFrag = new SettingsFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFrag).commit();

                    return true;

                }};

}