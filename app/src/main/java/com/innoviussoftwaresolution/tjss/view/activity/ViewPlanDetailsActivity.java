package com.innoviussoftwaresolution.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.plans.FragmentViewPlanDetails;

public class ViewPlanDetailsActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        Bugsnag.init(this);
        //Bugsnag.notify(new RuntimeException("Test error"));
        String planId = getIntent().getStringExtra(FragmentViewPlanDetails.PLAN_ID);

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentViewPlanDetails.class.getName());

        if (fragment == null)
            fragment = FragmentViewPlanDetails.newInstance(planId);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentViewPlanDetails.class.getName())
                .commit();


    }

}
