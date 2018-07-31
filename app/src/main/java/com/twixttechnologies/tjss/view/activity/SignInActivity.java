package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.signin.FragmentSignIn;

public class SignInActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FragmentSignIn.class.getName());

        if (fragment == null)
            fragment = new FragmentSignIn();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentSignIn.TAG)
                .commit();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }
}
