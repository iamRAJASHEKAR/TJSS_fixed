package com.innoviussoftwaresolution.tjss.model.internal;

import android.support.annotation.NonNull;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

/**
 * @author Sony Raj on 22-09-17.
 */

public class InternalContact implements SortedListAdapter.ViewModel {

    private String id;
    private String name;
    private String phone;
    private String email;

//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }




    public InternalContact(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public InternalContact() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

//    public void setEmail(String email1) {
//        email1 = email;
//    }

    @Override
    public String toString() {
        return "InternalContact{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InternalContact contact = (InternalContact) o;

        return id.equals(contact.id) && name.equals(contact.name) && phone.equals(contact.phone) && email.equals(contact.getEmail());

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + phone.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public <T> boolean isSameModelAs(@NonNull T item) {
        if (item instanceof InternalContact) {
            final InternalContact internalContact = (InternalContact) item;
            return internalContact.getId().equals(this.id);
        }
        return false;
    }

    @Override
    public <T> boolean isContentTheSameAs(@NonNull T item) {
        if (item instanceof InternalContact) {
            final InternalContact internalContact = (InternalContact) item;
            try {
                return this.id.equals(internalContact.getId())
                        && this.getName().equals(internalContact.getName())
                        && this.phone.equals(internalContact.getName())
                        && this.email.equals(internalContact.getEmail());
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
