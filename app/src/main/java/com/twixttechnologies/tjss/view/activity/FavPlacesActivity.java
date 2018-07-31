package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.favoriteplaces.FragmentFavoritePlacesList;

public class FavPlacesActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Fragment fragmentFavoritePlacesList =
                getSupportFragmentManager()
                        .findFragmentByTag(FragmentFavoritePlacesList.TAG);

        if (fragmentFavoritePlacesList == null)
            fragmentFavoritePlacesList = new FragmentFavoritePlacesList();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragmentFavoritePlacesList,
                        FragmentFavoritePlacesList.TAG)
                .commit();

    }

}
