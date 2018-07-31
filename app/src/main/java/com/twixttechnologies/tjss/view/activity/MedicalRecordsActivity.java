package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

//import com.twixttechnologies.tjss.R;
import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.user.settings.medical.FragmentMedicalRecordsListing;

/**
 * @author Sony Raj on 20-10-17.
 */

public class MedicalRecordsActivity extends BaseActivityWithToolbar {


    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);


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
