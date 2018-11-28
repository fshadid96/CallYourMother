package com.example.farahshadid.callyourmother;

import java.util.Comparator;

public class ContactComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact c1, Contact c2) {

        return c1.name.compareTo(c2.name);
    }
}