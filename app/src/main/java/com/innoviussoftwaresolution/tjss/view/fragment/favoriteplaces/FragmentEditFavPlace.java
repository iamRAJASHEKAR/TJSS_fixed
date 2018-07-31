package com.innoviussoftwaresolution.tjss.view.fragment.favoriteplaces;

import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.internal.AddressPrediction;
import com.innoviussoftwaresolution.tjss.model.network.response.FavoritePlace;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;
import com.innoviussoftwaresolution.tjss.utils.M;

import java.util.List;

/**
 * @author Sony Raj on 30-10-17.
 */

public class FragmentEditFavPlace extends FragmentAddFavoritePlace {

    public static final String TAG = "editFavPlace";
    public static final String FAV_PLACE_ID = "FavPlaceId";
    private FavoritePlace mFavPlace;
    private Observer<StatusMessage> mStatusMessageObserver
            = new Observer<StatusMessage>() {
        @Override
        public void onChanged(@Nullable StatusMessage statusMessage) {
            if (statusMessage == null || statusMessage.status == null || statusMessage.status.equals("") || !statusMessage.status.toLowerCase().contains("success")) {
                M.showToast(getActivity(), "An error occurred, Please try again later");
            } else {
                M.showToast(getActivity(), statusMessage.status);
                getActivity().finish();
            }
        }
    };

    public static FragmentEditFavPlace newInstance(FavoritePlace favPlace) {

        Bundle args = new Bundle();
        args.putParcelable(FAV_PLACE_ID, favPlace);
        FragmentEditFavPlace fragment = new FragmentEditFavPlace();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        super.onBaseCreate(SavedInstanceState);
        mFavPlace = getArguments().getParcelable(FAV_PLACE_ID);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.getStatusMessageData().observe(this, mStatusMessageObserver);
        mTxtFavPlaceName.setText(mFavPlace.name);
        mTxtFavPlaceAddress.setText(mFavPlace.address);
        mSkRadius.setProgress(mFavPlace.radius);
        mSelectedAddress = new AddressPrediction(new AutocompletePrediction() {
            @Override
            public CharSequence getFullText(@Nullable CharacterStyle characterStyle) {
                return mFavPlace.address;
            }

            @Override
            public CharSequence getPrimaryText(@Nullable CharacterStyle characterStyle) {
                return null;
            }

            @Override
            public CharSequence getSecondaryText(@Nullable CharacterStyle characterStyle) {
                return null;
            }

            @Nullable
            @Override
            public String getPlaceId() {
                return mFavPlace.placeId;
            }

            @Nullable
            @Override
            public List<Integer> getPlaceTypes() {
                return null;
            }

            @Override
            public AutocompletePrediction freeze() {
                return null;
            }

            @Override
            public boolean isDataValid() {
                return true;
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.delete, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            if (TextUtils.isEmpty(mTxtFavPlaceName.getText())) {
                showErrorSnackBar("Please select a new name for your new Favorite place",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mTxtFavPlaceName.requestFocus();
                            }
                        });
            } else if (TextUtils.isEmpty(mTxtFavPlaceAddress.getText())) {
                showErrorSnackBar("Please give an address for your new Favorite place",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mTxtFavPlaceAddress.requestFocus();
                            }
                        });
            } else if (mMarker == null) {
                M.showSnackBar(mTxtFavPlaceAddress, "Please select favorite place on the map");
            } else {
                mViewModel.editFavPlace(mMarker.getPosition(), mTxtFavPlaceName.getText().toString(),
                        mTxtFavPlaceAddress.getText().toString(), mSelectedAddress.getPlaceId(), mSkRadius.getProgress(), mFavPlace.favPlaceId);
                M.showSnackBar(mTxtFavPlaceName, "Updating favorite place...");

            }
        } else if (item.getItemId() == R.id.mnuDelete) {
            M.showAlert(getActivity(), "Delete favorite place", "Are you sure?", "DELETE", "DON'T DELETE",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mViewModel.deleteFavPlace(mFavPlace.favPlaceId);
                        }
                    }, null, false);
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        if (mFavPlace == null) return;
        if (mGoogleMap != null) {
            mGoogleMap.clear();
            mMarker = mGoogleMap.addMarker(new MarkerOptions()
                    .anchor(0.5f, 1.0f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin))
                    .position(new LatLng(Double.valueOf(mFavPlace.favoriteLat), Double.valueOf(mFavPlace.favoriteLong))));

            addCircle(mFavPlace.radius);
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mMarker.getPosition(), 14));
        }
    }
}