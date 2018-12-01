package com.example.farahshadid.callyourmother;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Contact {
    long id;
    String name;
    private ArrayList<String> numbers;
    Bitmap image;

    public Contact (long id, String name, ArrayList<String> numbers, Bitmap image) {
        this.id = id;
        this.name = name;
        this.numbers = numbers;
        this.image = image;
    }
}
