package com.twixttechnologies.tjss.view.fragment.favoriteplaces;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
//import com.twixttechnologies.tjss.R;
import com.twixttechnologies.tjss.model.network.response.FavoritePlace;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.activity.AddOrEditFavoritePlaceActivity;
import com.twixttechnologies.tjss.view.adapter.listadapter.FavoritePlaceAdapter;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.viewmodel.FavoritePlacesViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 20-07-17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class FragmentFavoritePlacesList extends BaseFragment {

    public static final String TAG = "favoritePlacesFragment";
    @BindView(R.id.lstFavoritePlaces)
    RecyclerView mLstFavoritePlaces;
    Unbinder unbinder;

    private FavoritePlacesViewModel mViewModel;
    private Observer<List<FavoritePlace>> mFavPlacesListObserver
            = new Observer<List<FavoritePlace>>() {
        @Override
        public void onChanged(@Nullable List<FavoritePlace> favoritePlaces) {

            if (favoritePlaces == null) {
                M.log("fav places is null");
                return;
            }

            if (favoritePlaces.size() <= 0 && mLstFavoritePlaces.getAdapter() == null) {
                M.log("fav Places count is Zero");
                return;
            }
            FavoritePlaceAdapter adapter = new FavoritePlaceAdapter(favoritePlaces);
            Activity activity = getActivity();
            if (activity == null) return;
            if (mLstFavoritePlaces == null) return;
            mLstFavoritePlaces.setAdapter(adapter);
            mLstFavoritePlaces.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    };

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_places_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getFavPlaces();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new FavoritePlacesViewModel.FavouritePlacesViewModelFactory(getActivity().getApplication()))
                .get(FavoritePlacesViewModel.class);

        mViewModel.getFavPlacesData().observe(this, mFavPlacesListObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnAddFavoritePlace)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), AddOrEditFavoritePlaceActivity.class));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("Favorite Places");
    }
}
