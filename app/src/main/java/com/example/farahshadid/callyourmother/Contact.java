package com.example.farahshadid.callyourmother;

import android.graphics.Bitmap;
import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable {
    int id;
    String name;
    private ArrayList<String> numbers;
    private int amountAccepted;
    private int amountNotified;
    Bitmap image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getNumbers() {
        return numbers;
    }

    public int getAmountAccepted() {
        return amountAccepted;
    }

    public int getAmountNotified() {
        return amountNotified;
    }


    public Contact (int id, String name, ArrayList<String> numbers, int amountAccepted,
                    int amountNotified, Bitmap image) {
        this.id = id;
        this.name = name;
        this.numbers = numbers;
        this.amountAccepted = amountAccepted;
        this.amountNotified = amountNotified;
        this.image = image;

    }
}
