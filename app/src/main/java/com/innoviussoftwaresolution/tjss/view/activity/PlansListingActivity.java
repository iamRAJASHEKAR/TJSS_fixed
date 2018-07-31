package com.innoviussoftwaresolution.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.signup.SignUpStepSixSelectPlan;

public class PlansListingActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        Bugsnag.init(this);
      //  Bugsnag.notify(new RuntimeException("Test error"));
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(SignUpStepSixSelectPlan.class.getName());


        if (fragment == null)
            fragment = new SignUpStepSixSelectPlan();


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, SignUpStepSixSelectPlan.class.getName())
                .commit();



    }

}
