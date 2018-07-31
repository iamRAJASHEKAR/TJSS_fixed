package com.innoviussoftwaresolution.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.user.settings.circle.FragmentSafetyCirclesList;

/**
 * @author Sony Raj on 25-07-17.
 */

public class SafetyCircleSettingsActivity extends BaseActivityWithToolbar{

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        Bugsnag.init(this);
       // Bugsnag.notify(new RuntimeException("Test error"));
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentSafetyCirclesList.class.getName());

        if (fragment == null)
            fragment = new FragmentSafetyCirclesList();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer,fragment,FragmentSafetyCirclesList.class.getName())
                .commit();


    }
}
