package com.innoviussoftwaresolution.tjss.view.activity;

import android.os.Bundle;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.checkin.FragmentSafetyCircleMembersListing;

/**
 * @author Sony Raj on 20/11/17.
 */

public class SafetyCircleMembersListingForCheckInActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_layout);
        Bugsnag.init(this);
      //  Bugsnag.notify(new RuntimeException("Test error"));
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, new FragmentSafetyCircleMembersListing(),
                            FragmentSafetyCircleMembersListing.TAG)
                    .commit();
        }


    }
}
