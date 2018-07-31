package com.innoviussoftwaresolution.tjss.view.activity;

import android.os.Bundle;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.serviceprovider.FragmentAppServiceProvidersListing;

/**
 * @author Sony Raj on 20/11/17.
 */

public class AppServiceProviderListingActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        Bugsnag.init(this);
     //   Bugsnag.notify(new RuntimeException("Test error"));
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, new FragmentAppServiceProvidersListing(),
                            FragmentAppServiceProvidersListing.TAG)
                    .commit();
        }

    }
}
