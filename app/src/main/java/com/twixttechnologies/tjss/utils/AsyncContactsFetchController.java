package com.twixttechnologies.tjss.utils;

import android.app.Application;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.twixttechnologies.tjss.model.internal.InternalContact;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;

import java.util.ArrayList;

/**
 * @author Sony Raj on 22-09-17.
 */

public class AsyncContactsFetchController extends AsyncTask<Void, Void, ArrayList<InternalContact>> {

    private final Application mApplication;
    private final RequestCallback<ArrayList<InternalContact>> mCallback;

    private final String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
    private final String FILTER = DISPLAY_NAME + " NOT LIKE '%@%'";
    private final String ORDER = String.format("%1$s COLLATE NOCASE", DISPLAY_NAME);

    private final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            DISPLAY_NAME,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
    };

    public AsyncContactsFetchController(Application application,
                                        RequestCallback<ArrayList<InternalContact>> callback) {
        mApplication = application;
        mCallback = callback;
    }

    @Override
    protected ArrayList<InternalContact> doInBackground(Void... params) {
        ArrayList<InternalContact> contacts = null;

        ContentResolver resolver = mApplication.getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
                PROJECTION,
                FILTER,
                null,
                ORDER);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    boolean hasPhone = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0;

                    if (!hasPhone) continue;
                    Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                            new String[]{id}, null);
                    if (contacts == null)
                        contacts = new ArrayList<>(cursor.getCount());
                    try {
                        if (phoneCursor != null && phoneCursor.moveToFirst()) {
                            String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            if (phone != null && !phone.equals("")) {
                                contacts.add(new InternalContact(id, name, phone));
                            }
                        }
                    } catch (Exception e) {
                        M.log("Phone number error", e.getMessage());
                    } finally {
                        if (phoneCursor != null)
                            phoneCursor.close();
                    }
                }
            }
        } catch (Exception e) {
            M.log(e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return contacts;
    }

    @Override
    protected void onPostExecute(ArrayList<InternalContact> internalContacts) {
        super.onPostExecute(internalContacts);
        if (internalContacts == null) {
            mCallback.onFailure("Unable to chatItems contacts");
        } else if (internalContacts.size() <= 0) {
            mCallback.onFailure("No contacts found");
        } else {
            mCallback.onSuccess(internalContacts);
        }
    }
}
