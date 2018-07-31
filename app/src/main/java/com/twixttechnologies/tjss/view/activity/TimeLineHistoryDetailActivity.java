package com.twixttechnologies.tjss.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.user.history.FragmentTimeLineHistoryDetail;

/**
 * @author Sony Raj on 09-10-17.
 */

public class TimeLineHistoryDetailActivity extends BaseActivityWithToolbar {


    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentTimeLineHistoryDetail.TAG);

        Intent callingIntent = getIntent();
        String id = callingIntent.getStringExtra("id");
        String userId = callingIntent.getStringExtra("userId");

        if (fragment == null)
            fragment = FragmentTimeLineHistoryDetail.newInstance(id,userId);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentTimeLineHistoryDetail.TAG)
                .commit();

    }
}
