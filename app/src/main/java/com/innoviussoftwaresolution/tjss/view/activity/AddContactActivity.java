package com.innoviussoftwaresolution.tjss.view.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.helpalert.FragmentAddContact;

public class AddContactActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        Bugsnag.init(this);
       // Bugsnag.notify(new RuntimeException("Test error"));

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentAddContact.class.getName());
        if (fragment == null)
            fragment = new FragmentAddContact();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentAddContact.class.getName())
                .commit();

    }

}
