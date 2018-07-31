package com.twixttechnologies.tjss.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.safetycircle.FragmentAddSafetyCircle;
import com.twixttechnologies.tjss.view.fragment.signup.SignUpFragment;

public class CreateCircleActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        boolean finishActivity = false;
        Bundle user = null;
        Intent callingIntent = getIntent();
        if (callingIntent != null) {
            finishActivity = callingIntent.getBooleanExtra(FragmentAddSafetyCircle.FINISH_ACTIVITY, false);
            user = callingIntent.getBundleExtra(SignUpFragment.USER);
        }
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FragmentAddSafetyCircle.TAG);
        if (fragment == null)
            fragment = FragmentAddSafetyCircle.newInstance(finishActivity, user);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentAddSafetyCircle.TAG)
                .commit();
    }
}
