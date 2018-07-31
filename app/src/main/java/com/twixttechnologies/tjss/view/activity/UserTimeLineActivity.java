package com.twixttechnologies.tjss.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;
import com.twixttechnologies.tjss.view.fragment.user.history.FragmentUserTimeLine;

/*
 * @author Sony Raj on 07-10-17.
 */


/**
 * Anyone who calls this activity should pass a safety circle member in the intent
 * with key {@link #SAFETY_CIRCLE_MEMBER}
 */
public class UserTimeLineActivity extends BaseActivityWithToolbar {

    public static final String SAFETY_CIRCLE_MEMBER = "SafetyCircleMember";

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentUserTimeLine.TAG);

        if (fragment == null) {

            Intent callingIntent = getIntent();
            SafetyCircleMember safetyCircleMember = callingIntent.getParcelableExtra(SAFETY_CIRCLE_MEMBER);
            fragment = FragmentUserTimeLine.newInstance(safetyCircleMember);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentUserTimeLine.TAG)
                .commit();

    }


}
