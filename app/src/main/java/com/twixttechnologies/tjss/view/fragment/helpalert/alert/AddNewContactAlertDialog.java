package com.twixttechnologies.tjss.view.fragment.helpalert.alert;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 20-07-17.
 */

public class AddNewContactAlertDialog extends AppCompatDialogFragment {

    public static final int TYPE_MANUAL = 1;
    public static final int TYPE_EXISTING = 2;
    Unbinder unbinder;
    private ContactSelectionTypeDialogCallback mCallback;

    public void setCallback(ContactSelectionTypeDialogCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_contact_alert_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnAddContactManually, R.id.btnAddContactFromContacts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAddContactManually:
                mCallback.onContactTypeSelected(TYPE_MANUAL);
                break;
            case R.id.btnAddContactFromContacts:
                mCallback.onContactTypeSelected(TYPE_EXISTING);
                break;
        }

        dismiss();
    }

    public interface ContactSelectionTypeDialogCallback {
        void onContactTypeSelected(@ContactSelectionType int type);
    }

    @IntDef({TYPE_MANUAL, TYPE_EXISTING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ContactSelectionType {
    }

}