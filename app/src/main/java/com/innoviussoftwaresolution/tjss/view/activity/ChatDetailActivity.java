package com.innoviussoftwaresolution.tjss.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.message.FragmentChatDetail;

public class ChatDetailActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        Bugsnag.init(this);
     //   Bugsnag.notify(new RuntimeException("Test error"));
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentChatDetail.TAG);

        Intent callingIntent = getIntent();
        String users = callingIntent.getStringExtra(FragmentChatDetail.USERS);
        String groupName = callingIntent.getStringExtra(FragmentChatDetail.GROUP_NAME);
        String isNew = callingIntent.getStringExtra(FragmentChatDetail.NEW);
        if (fragment == null)
            fragment = FragmentChatDetail.newInstance(users, groupName, isNew);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentChatDetail.TAG)
                .commit();


    }
}
