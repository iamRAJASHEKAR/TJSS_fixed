package com.innoviussoftwaresolution.tjss.view.activity;

import android.os.Bundle;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.helpalert.FragmentContacts;

/**
 * @author Sony Raj on 22-09-17.
 */

public class SelectContactActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Bugsnag.init(this);
 //       Bugsnag.notify(new RuntimeException("Test error"));
        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.mainContainer, new FragmentContacts(), FragmentContacts.TAG)
                    .commit();
    }
}
