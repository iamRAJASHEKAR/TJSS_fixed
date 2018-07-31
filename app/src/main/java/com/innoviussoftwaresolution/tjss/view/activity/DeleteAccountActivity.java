package com.innoviussoftwaresolution.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.user.settings.account.FragmentDeleteAccount;

/**
 * @author Sony Raj on 25-07-17.
 */

public class DeleteAccountActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        Bugsnag.init(this);
       // Bugsnag.notify(new RuntimeException("Test error"));
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentDeleteAccount.class.getName());

        if (fragment == null)
            fragment = new FragmentDeleteAccount();


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer,fragment, FragmentDeleteAccount.class.getName())
                .commit();

    }
}
