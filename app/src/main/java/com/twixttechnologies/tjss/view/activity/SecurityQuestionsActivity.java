package com.twixttechnologies.tjss.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.security.FragmentSecurityQuestion;

/*
 * @author Sony Raj on 31-10-17.
 */


/**
 * Should pass a boolean value to redirect to home or not
 */
public class SecurityQuestionsActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Intent callingIntent = getIntent();
        boolean redirectToHome = callingIntent.getBooleanExtra(FragmentSecurityQuestion.REDIRECT, true);


        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentSecurityQuestion.TAG);

        if (fragment == null)
            fragment = FragmentSecurityQuestion.newInstance(redirectToHome);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentSecurityQuestion.TAG)
                .commit();

    }
}
