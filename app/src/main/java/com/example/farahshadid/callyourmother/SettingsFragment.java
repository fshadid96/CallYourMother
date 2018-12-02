package com.example.farahshadid.callyourmother;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.util.ArrayList;

public class SettingsFragment extends Fragment {
        Button button;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        button = (Button)view.findViewById(R.id.contacts_button);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeContactIntent = new Intent(view.getContext(), ChangeContactsActivity.class);
                startActivity(changeContactIntent);
            }
        });


        return view;
    }
}