package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.user.FragmentUserProfile;

public class UserProfileActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentUserProfile.TAG);

        if (fragment == null)
            fragment = new FragmentUserProfile();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentUserProfile.TAG)
                .commit();

    }

}
