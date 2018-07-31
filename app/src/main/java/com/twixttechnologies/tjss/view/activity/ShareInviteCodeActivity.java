package com.twixttechnologies.tjss.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.safetycircle.FragmentShareInviteCode;

/**
 * @author Sony Raj on 16-10-17.
 */

public class ShareInviteCodeActivity extends BaseActivityWithToolbar {

    public static final String INVITE_CODE = "inviteCode";

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        String inviteCode;
        Intent callingIntent = getIntent();
        inviteCode = callingIntent.getStringExtra(INVITE_CODE);

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentShareInviteCode.TAG);


        if (fragment == null)
            fragment = FragmentShareInviteCode.newInstance(inviteCode);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentShareInviteCode.TAG)
                .commit();


    }
}
