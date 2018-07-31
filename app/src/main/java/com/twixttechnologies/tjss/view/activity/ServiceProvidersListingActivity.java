package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.serviceprovider.FragmentServiceProviders;

/**
 * @author Sony Raj on 25-09-17.
 */

public class ServiceProvidersListingActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FragmentServiceProviders.TAG);

        if (fragment == null)
            fragment = new FragmentServiceProviders();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentServiceProviders.TAG)
                .commit();

    }


}
