package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.location.FragmentLocationSharing;
import com.twixttechnologies.tjss.view.fragment.message.FragmentChatDetail;

/**
 * @author Sony Raj on 25-07-17.
 */

public class LocationSharingActivity extends BaseActivityWithToolbar {
    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentLocationSharing.class.getName());

        if (fragment == null)
            fragment = new FragmentLocationSharing();


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer,fragment,FragmentLocationSharing.class.getName())
                .commit();

    }
}
