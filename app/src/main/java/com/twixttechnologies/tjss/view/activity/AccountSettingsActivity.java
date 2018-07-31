package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.user.settings.account.FragmentAccountSettings;

/**
 * @author Sony Raj on 25-07-17.
 */

public class AccountSettingsActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentAccountSettings.class.getName());

        if (fragment == null)
            fragment = new FragmentAccountSettings();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentAccountSettings.class.getName())
                .commit();


    }
}
