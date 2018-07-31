package com.innoviussoftwaresolution.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.user.settings.safetytip.FragmentSafetyTipSettings;

/**
 * @author Sony Raj on 16-09-17.
 */

public class SafetyTipsSettingsActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_layout);
        Bugsnag.init(this);
        try {
            // Some potentially crashy code
        } catch (Throwable exception) {
            Bugsnag.notify(exception);
        }
     //   Bugsnag.notify(new RuntimeException("Test error"));
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
