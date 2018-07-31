package com.innoviussoftwaresolution.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.helpalert.FragmentHelpAlertsListing;

/**
 * @author Sony Raj on 02-11-17.
 */

public class HelpAlertHistoryActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        Bugsnag.init(this);
       // Bugsnag.notify(new RuntimeException("Test error"));
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentHelpAlertsListing.TAG);

        if (fragment == null)
            fragment = new FragmentHelpAlertsListing();


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentHelpAlertsListing.TAG)
                .commit();


    }
}
