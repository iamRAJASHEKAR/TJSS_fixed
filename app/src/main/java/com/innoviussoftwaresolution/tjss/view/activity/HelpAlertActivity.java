package com.innoviussoftwaresolution.tjss.view.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.helpalert.FragmentHelpAlert;

public class HelpAlertActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        Bugsnag.init(this);
      //  Bugsnag.notify(new RuntimeException("Test error"));
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentHelpAlert.class.getName());


        if (fragment == null)
            fragment = new FragmentHelpAlert();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentHelpAlert.class.getName())
                .commit();
    }

}
