package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.signup.SignUpFragment;

public class SignUpActivity extends BaseActivity {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

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
