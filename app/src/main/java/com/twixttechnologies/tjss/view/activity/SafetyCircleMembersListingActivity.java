package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.safetycircle.FragmentSafetyCircleMembers;

/**
 * @author Sony Raj on 19-09-17.
 */

public class SafetyCircleMembersListingActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentSafetyCircleMembers.TAG);


        if (fragment == null)
            fragment = new FragmentSafetyCircleMembers();


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentSafetyCircleMembers.TAG)
                .commit();

    }
}
