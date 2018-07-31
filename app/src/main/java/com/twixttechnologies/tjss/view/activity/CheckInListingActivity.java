package com.twixttechnologies.tjss.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;
import com.twixttechnologies.tjss.view.fragment.checkin.FragmentCheckIn;

/**
 * @author Sony Raj on 01-11-17.
 */

public class CheckInListingActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_layout);

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentCheckIn.TAG);

        Intent callingIntent = getIntent();
        SafetyCircleMember member = callingIntent.getParcelableExtra("member");

        if (fragment == null)
            fragment = FragmentCheckIn.newInstance(member);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainContainer, fragment, FragmentCheckIn.TAG)
                .commit();

    }
}
