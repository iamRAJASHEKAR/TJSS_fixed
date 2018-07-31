package com.innoviussoftwaresolution.tjss.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.FragmentSplash;

/**
 * @author Sony Raj on 16-10-17.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bugsnag.init(this);
       // Bugsnag.notify(new RuntimeException("Test error"));
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_layout);

        android.support.v4.app.Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentSplash.TAG);

        if (fragment == null)
            fragment = new FragmentSplash();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentSplash.TAG)
                .commit();


    }
}
