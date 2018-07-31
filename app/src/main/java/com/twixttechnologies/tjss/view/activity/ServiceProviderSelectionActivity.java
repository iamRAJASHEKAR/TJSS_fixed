package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.user.settings.serviceprovider.FragmentServiceProvidersSelection;

/**
 * @author Sony Raj on 21-10-17.
 */

public class ServiceProviderSelectionActivity extends BaseActivityWithToolbar {


    Fragment fragment;

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentServiceProvidersSelection.TAG);


        if (fragment == null)
            fragment = new FragmentServiceProvidersSelection();


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentServiceProvidersSelection.TAG)
                .commit();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return !((FragmentServiceProvidersSelection) fragment).canGoBack() || super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (((FragmentServiceProvidersSelection) fragment).canGoBack()) {
            super.onBackPressed();
        }
    }
}
