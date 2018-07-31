package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.payment.FragmentPurchaseService;

/**
 * @author Sony Raj on 09-10-17.
 */

public class PurchaseServiceActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentPurchaseService.TAG);

        if (fragment == null)
            fragment = new FragmentPurchaseService();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentPurchaseService.TAG)
                .commit();


    }
}
