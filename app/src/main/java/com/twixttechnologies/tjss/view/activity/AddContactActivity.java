package com.twixttechnologies.tjss.view.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.helpalert.FragmentAddContact;

public class AddContactActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);


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
