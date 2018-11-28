package com.example.farahshadid.callyourmother;

import java.util.ArrayList;

public class Contact {
    int id;
    String name;
    private ArrayList<String> numbers;

    public Contact (int id, String name, ArrayList<String> numbers) {
        this.id = id;
        this.name = name;
        this.numbers = numbers;
    }
}
