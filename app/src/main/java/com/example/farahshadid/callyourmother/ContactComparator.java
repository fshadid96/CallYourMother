package com.example.farahshadid.callyourmother;

import java.util.Comparator;

public class ContactComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact c1, Contact c2) {
        if (c1 == null && c2 == null) {
            return 0;
        } else if (c1 == null) {
            return -1;
        } else if (c2 == null) {
            return 1;
        } else {
            return c1.name.compareTo(c2.name);
        }
    }
}