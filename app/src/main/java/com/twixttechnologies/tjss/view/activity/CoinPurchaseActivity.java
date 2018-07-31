package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.payment.FragmentCoinPurchase;

/**
 * @author Sony Raj on 07-10-17.
 */

public class CoinPurchaseActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentCoinPurchase.TAG);

        if (fragment == null)
            fragment = new FragmentCoinPurchase();


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentCoinPurchase.TAG)
                .commit();

    }
}
