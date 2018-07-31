package com.innoviussoftwaresolution.tjss.RequestCircleCodeModule;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ContactRequestModel {

    @SerializedName("")
    private ArrayList<ContactsModel> contactList;

    public ContactRequestModel() {
    }

    public ArrayList<ContactsModel> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<ContactsModel> contactList) {
        this.contactList = contactList;
    }
}
