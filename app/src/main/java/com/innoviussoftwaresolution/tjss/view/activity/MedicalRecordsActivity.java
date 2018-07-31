package com.innoviussoftwaresolution.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.user.settings.medical.FragmentMedicalRecordsListing;

/**
 * @author Sony Raj on 20-10-17.
 */

public class MedicalRecordsActivity extends BaseActivityWithToolbar {


    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Bugsnag.init(this);
//        Bugsnag.notify(new RuntimeException("Test error"));
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentMedicalRecordsListing.TAG);

        if (fragment == null)
            fragment = new FragmentMedicalRecordsListing();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentMedicalRecordsListing.TAG)
                .commit();

    }
}
