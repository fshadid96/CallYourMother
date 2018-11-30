package com.example.farahshadid.callyourmother;

public class TopContacts{

    public int amountAccepted;
    public int amountNotified;
    public String contactName;

    public TopContacts(int amountAccepted, int amountNotified, String contactName, int commitmentScore) {
        this.amountAccepted = amountAccepted;
        this.amountNotified = amountNotified;
        this.contactName = contactName;
    }
}

