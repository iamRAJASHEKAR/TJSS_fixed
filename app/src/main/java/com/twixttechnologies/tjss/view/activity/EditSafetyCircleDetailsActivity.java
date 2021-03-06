package com.twixttechnologies.tjss.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.user.settings.circle.FragmentEditSafetyCircle;
import com.twixttechnologies.tjss.view.fragment.user.settings.circle.FragmentSafetyCircleDetails;

/**
 * @author Sony Raj on 25-07-17.
 */

public class EditSafetyCircleDetailsActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Intent callingIntent = getIntent();
        String circleId = callingIntent.getStringExtra(FragmentSafetyCircleDetails.SAFETY_CIRCLE_ID);
        String circleName = callingIntent.getStringExtra(FragmentSafetyCircleDetails.SAFETY_CIRCLE_NAME);
        String circleImageLink = callingIntent.getStringExtra(FragmentSafetyCircleDetails.SAFETY_CIRCLE_IMAGE_LINK);
        boolean isAdmin = callingIntent.getBooleanExtra(FragmentSafetyCircleDetails.IS_ADMIN, false);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer,
                        FragmentEditSafetyCircle.newInstance(circleId, circleName, circleImageLink, isAdmin),
                        FragmentEditSafetyCircle.TAG)
                .commit();

    }
}
