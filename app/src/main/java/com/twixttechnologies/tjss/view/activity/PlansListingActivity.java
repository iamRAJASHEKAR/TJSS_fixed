package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.signup.SignUpStepSixSelectPlan;

public class PlansListingActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

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
