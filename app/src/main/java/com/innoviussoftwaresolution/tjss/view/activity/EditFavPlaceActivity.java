package com.innoviussoftwaresolution.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.FavoritePlace;
import com.innoviussoftwaresolution.tjss.view.fragment.favoriteplaces.FragmentEditFavPlace;

/**
 * @author Sony Raj on 30-10-17.
 */

public class EditFavPlaceActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        Bugsnag.init(this);
       // Bugsnag.notify(new RuntimeException("Test error"));
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentEditFavPlace.TAG);

        FavoritePlace favoritePlace = getIntent().getParcelableExtra("favPlace");


        if (fragment == null)
            fragment = FragmentEditFavPlace.newInstance(favoritePlace);


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentEditFavPlace.TAG)
                .commit();

    }
}
