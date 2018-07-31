package com.innoviussoftwaresolution.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.safetycircle.FragmentSafetyCircleMembers;

/**
 * @author Sony Raj on 19-09-17.
 */

public class SafetyCircleMembersListingActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        Bugsnag.init(this);
    //    Bugsnag.notify(new RuntimeException("Test error"));
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentSafetyCircleMembers.TAG);


        if (fragment == null)
            fragment = new FragmentSafetyCircleMembers();


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentSafetyCircleMembers.TAG)
                .commit();

    }
}
