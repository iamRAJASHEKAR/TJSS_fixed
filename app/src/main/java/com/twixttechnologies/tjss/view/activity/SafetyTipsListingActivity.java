package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.safetytip.FragmentSafetyTipsListing;

/**
 * @author Sony Raj on 16-09-17.
 */

public class SafetyTipsListingActivity extends BaseActivityWithToolbar {
    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentSafetyTipsListing.TAG);

        if (fragment == null)
            fragment = new FragmentSafetyTipsListing();


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainContainer, fragment, FragmentSafetyTipsListing.TAG)
                .commit();

    }
}
