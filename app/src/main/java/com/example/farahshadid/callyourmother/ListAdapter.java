package com.example.farahshadid.callyourmother;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<Contact> contacts, totalContacts;
    private HashMap<Integer, Contact> checked;
    private ContactComparator comp;
    private User user;

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
        ArrayList<Contact> selected = null;
        if (checked != null) {
            Collection<Contact> temp = checked.values();
            temp.remove(null);
            selected = new ArrayList<>(temp);
            Collections.sort(selected, comp);
        }
        return selected;
    }

    void filter(String text) {
        contacts.clear();
        for (Contact c : totalContacts) {
            if (c.name.toLowerCase().contains(text)) {
                contacts.add(c);
            }
        }
    }

    ListAdapter(ArrayList<Contact> c) {
        comp = new ContactComparator();
        totalContacts = c;
        Collections.sort(this.totalContacts, comp);
        checked = new HashMap<>();
        contacts = new ArrayList<>(totalContacts);
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
        if (contacts.get(i).image != null) {
            viewHolder.icon.setImageBitmap(contacts.get(i).image);
        } else {
            viewHolder.icon.setImageResource(R.drawable.ic_account_circle_24dp);
        }


        viewHolder.check.setChecked(checked.get(contacts.get(i).id) != null);
        viewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.check.isChecked()) {
                    checked.put(contacts.get(i).id, contacts.get(i));
                } else {
                    checked.put(contacts.get(i).id, null);
                }
            }
        });

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.check.toggle();
                if (viewHolder.check.isChecked()) {
                    checked.put(contacts.get(i).id, contacts.get(i));
                } else {
                    checked.put(contacts.get(i).id, null);
                }
            }
        });
    }
}
