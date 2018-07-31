package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.user.settings.alert.FragmentAlertOptionsSettings;

/**
 * @author Sony Raj on 25-07-17.
 */

public class AlertOptionsSettingsActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentAlertOptionsSettings.class.getName());


        if (fragment == null)
            fragment = new FragmentAlertOptionsSettings();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment,FragmentAlertOptionsSettings.class.getName())
                .commit();
    }
}
