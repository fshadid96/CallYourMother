package com.example.farahshadid.callyourmother;

import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<Contact> contacts;
    private ArrayList<Contact> selectedContacts;
    SparseBooleanArray selected;
    User user;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView icon;
        CheckBox check;
        ConstraintLayout layout;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            icon = itemView.findViewById(R.id.icon);
            check = itemView.findViewById(R.id.checkbox);
            layout = itemView.findViewById(R.id.layout);
        }
    }

    public ArrayList<Contact> getSelected() {
        return selectedContacts;
    }

    ListAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
        Collections.sort(this.contacts, new ContactComparator());

        selectedContacts = new ArrayList<>();

        selected = new SparseBooleanArray();
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_element, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.name.setText(contacts.get(i).name);
        viewHolder.check.setChecked(selected.get(i));
        viewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.check.isChecked()) {
                    selected.put(i, true);
                    selectedContacts.add(contacts.get(i));
                } else {
                    selected.delete(i);
                    selectedContacts.remove(i);
                }
            }
        });

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.check.toggle();
                if (viewHolder.check.isChecked()) {
                    selected.put(i, true);
                    selectedContacts.add(contacts.get(i));
                } else {
                    selected.delete(i);
                    selectedContacts.remove(i);
                }
            }
        });

        System.out.print("size of line adapter: " + selectedContacts.size());
        user = new User(getDeviceName(), selectedContacts);
        user.writeNewUser(getDeviceName(), selectedContacts);
    }

    /**
     *Got the device name and to set it as a user
     *
     * Source: https://stackoverflow.com/questions/7071281/get-android-device-name
     * @return
     */
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