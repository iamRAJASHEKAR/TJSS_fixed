package com.twixttechnologies.tjss.model.internal;

import android.support.annotation.NonNull;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;

/**
 * @author Sony Raj on 22-09-17.
 */

public class InternalContact implements SortedListAdapter.ViewModel {

    private final String id;
    private final String name;
    private final String phone;

    public InternalContact(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
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


    @Override
    public String toString() {
        return "InternalContact{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InternalContact contact = (InternalContact) o;

        return id.equals(contact.id) && name.equals(contact.name) && phone.equals(contact.phone);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + phone.hashCode();
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
                        && this.phone.equals(internalContact.getName());
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
