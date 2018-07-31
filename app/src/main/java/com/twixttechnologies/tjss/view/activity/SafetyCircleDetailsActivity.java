package com.twixttechnologies.tjss.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.user.settings.circle.FragmentSafetyCircleDetails;

/*
 * @author Sony Raj on 25-07-17.
 */

/**
 * Anyone calling this class should pass
 * <ol>
 *     <li> {@link FragmentSafetyCircleDetails#SAFETY_CIRCLE_ID} </li>
 *     <li> {@link FragmentSafetyCircleDetails#SAFETY_CIRCLE_NAME} </li>
 *     <li> {@link FragmentSafetyCircleDetails#SAFETY_CIRCLE_IMAGE_LINK} </li>
 *     <li> {@link FragmentSafetyCircleDetails#IS_ADMIN}</li>
 * </ol>
 * as arguments
 */
public class SafetyCircleDetailsActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Fragment fragment = null;
        if (savedInstanceState != null)
            fragment = getSupportFragmentManager()
                    .findFragmentByTag(FragmentSafetyCircleDetails.class.getName());

        Intent callingIntent = getIntent();
        String circleId = callingIntent.getStringExtra(FragmentSafetyCircleDetails.SAFETY_CIRCLE_ID);
        String circleName = callingIntent.getStringExtra(FragmentSafetyCircleDetails.SAFETY_CIRCLE_NAME);
        String circleImage = callingIntent.getStringExtra(FragmentSafetyCircleDetails.SAFETY_CIRCLE_IMAGE_LINK);
        boolean isAdmin = callingIntent.getBooleanExtra(FragmentSafetyCircleDetails.IS_ADMIN, false);
        if (fragment == null)
            fragment = FragmentSafetyCircleDetails.newInstance(circleId, circleName, circleImage, isAdmin);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentSafetyCircleDetails.class.getName())
                .commit();


    }


}
