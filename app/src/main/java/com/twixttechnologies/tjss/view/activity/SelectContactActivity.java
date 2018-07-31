package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.helpalert.FragmentContacts;

/**
 * @author Sony Raj on 22-09-17.
 */

public class SelectContactActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.mainContainer, new FragmentContacts(), FragmentContacts.TAG)
                    .commit();
    }
}
