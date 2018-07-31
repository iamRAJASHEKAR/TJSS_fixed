package com.twixttechnologies.tjss.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.helpalert.FragmentHelpAlertDisplay;

/**
 * @author Sony Raj on 10/11/17.
 */

public class HelpAlertDisplayActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Intent callingIntent = getIntent();
        String lat = callingIntent.getStringExtra(FragmentHelpAlertDisplay.LAT);
        String lng = callingIntent.getStringExtra(FragmentHelpAlertDisplay.LNG);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainContainer, FragmentHelpAlertDisplay.newInstance(lat, lng), FragmentHelpAlertDisplay.TAG)
                .commit();

    }


}
