package com.twixttechnologies.tjss.view.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.appcompat.*;
import android.view.Window;
import android.view.WindowManager;

import com.twixttechnologies.tjss.view.fragment.FragmentSplash;

/**
 * @author Sony Raj on 16-10-17.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.);

        android.support.v4.app.Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentSplash.TAG);

        if (fragment == null)
            fragment = new FragmentSplash();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentSplash.TAG)
                .commit();

*/

    }
}
