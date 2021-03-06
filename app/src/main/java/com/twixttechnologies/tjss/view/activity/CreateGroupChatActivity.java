package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.message.FragmentCreateGroupChat;

/**
 * @author Sony Raj on 02-10-17.
 */

public class CreateGroupChatActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentCreateGroupChat.TAG);

        if (fragment == null)
            fragment = new FragmentCreateGroupChat();


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentCreateGroupChat.TAG)
                .commit();
    }
}
