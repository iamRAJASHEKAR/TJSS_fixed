package com.innoviussoftwaresolution.tjss.RequestCircleCodeModule;

import com.google.gson.annotations.SerializedName;

public class ContactsModel {

    @SerializedName("contact")
    private StringBuffer contactNumber;


    public ContactsModel() {
    }


    public StringBuffer getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(StringBuffer contactNumber) {
        this.contactNumber = contactNumber;
    }
}
