package com.twixttechnologies.tjss.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.HelpAlert;
import com.twixttechnologies.tjss.view.fragment.helpalert.FragmentHelpAlertDetail;

/**
 * @author Sony Raj on 02-11-17.
 */

public class HelpAlertDetailsActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Intent intent = getIntent();
        HelpAlert helpAlert = intent.getParcelableExtra("helpAlert");

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentHelpAlertDetail.TAG);


        if (fragment == null)
            fragment = FragmentHelpAlertDetail.newInstance(helpAlert);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentHelpAlertDetail.TAG)
                .commit();

    }
}
