package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.incident.FragmentIncidentListing;

/**
 * @author Sony Raj on 19-10-17.
 */

public class IncidentsListingActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentIncidentListing.TAG);

        if (fragment == null)
            fragment = new FragmentIncidentListing();


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentIncidentListing.TAG)
                .commit();

    }
}
