package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.message.FragmentGroupMembers;

/**
 * @author Sony Raj on 28-10-17.
 */

public class GroupMembersListingActivity extends BaseActivityWithToolbar {

    public static final String GROUP_ID = "groupId";
    public static final String GROUP_NAME = "groupName";

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);


        String groupId = getIntent().getStringExtra(GROUP_ID);
        String groupName = getIntent().getStringExtra(GROUP_NAME);

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentGroupMembers.TAG);


        if (fragment == null)
            fragment = FragmentGroupMembers.newInstance(groupId, groupName);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentGroupMembers.TAG)
                .commit();


    }
}
