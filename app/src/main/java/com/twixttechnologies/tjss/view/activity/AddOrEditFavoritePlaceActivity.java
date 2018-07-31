package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.favoriteplaces.FragmentAddFavoritePlace;

public class AddOrEditFavoritePlaceActivity extends BaseActivityWithToolbar {

    @Override
    void onBaseCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_layout);

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentAddFavoritePlace.class.getName());

        if (fragment == null)
            fragment = new FragmentAddFavoritePlace();


        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer, fragment,
                FragmentAddFavoritePlace.class.getName())
                .commit();

    }

}
