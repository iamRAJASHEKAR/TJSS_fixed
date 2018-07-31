package com.innoviussoftwaresolution.tjss.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyCircle;
import com.innoviussoftwaresolution.tjss.view.fragment.user.settings.alert.FragmentSafetyCircleAlertOptions;

/*
 * @author Sony Raj on 25-07-17.
 */

/**
 * Anyone who calls this activity should pass a
 * {@link com.innoviussoftwaresolution.tjss.model.network.response.SafetyCircle}
 * with key {@link #SAFETY_CIRCLE}
 */
public class SafetyCircleAlertOptionActivity extends BaseActivityWithToolbar {

    public static final String SAFETY_CIRCLE = "safetyCircle";

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        Bugsnag.init(this);
      //  Bugsnag.notify(new RuntimeException("Test error"));
        Intent intent = getIntent();
        SafetyCircle safetyCircle = intent.getParcelableExtra(SAFETY_CIRCLE);
        if (safetyCircle == null)
            throw new NullPointerException("safety circle Not found");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer,
                        FragmentSafetyCircleAlertOptions.newInstance(safetyCircle),
                        FragmentSafetyCircleAlertOptions.TAG)
                .commit();

    }
}
