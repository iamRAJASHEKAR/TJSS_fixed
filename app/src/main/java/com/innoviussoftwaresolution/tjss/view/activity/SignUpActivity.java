package com.innoviussoftwaresolution.tjss.view.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.signup.SignUpFragment;

public class SignUpActivity extends BaseActivity {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        Bugsnag.init(this);
        //Bugsnag.notify(new RuntimeException("Test error"));
        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.mainContainer, new SignUpFragment(), SignUpFragment.TAG)
                    .commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
