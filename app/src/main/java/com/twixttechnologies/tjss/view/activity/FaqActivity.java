package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.faq.FragmentFaq;

/**
 * @author Sony Raj on 13-09-17.
 */

public class FaqActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FragmentFaq.TAG);
        if (fragment == null) fragment = new FragmentFaq();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainContainer, fragment, FragmentFaq.TAG)
                .commit();

    }


}
