package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.user.settings.safetytip.FragmentSafetyTipSettings;

/**
 * @author Sony Raj on 16-09-17.
 */

public class SafetyTipsSettingsActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_layout);

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentSafetyTipSettings.TAG);

        if (fragment == null)
            fragment = new FragmentSafetyTipSettings();


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainContainer, fragment, FragmentSafetyTipSettings.TAG)
                .commit();

    }


}
