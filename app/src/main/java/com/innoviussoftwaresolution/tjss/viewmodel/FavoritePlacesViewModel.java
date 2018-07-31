package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.model.LatLng;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.AddFavPlaceResponse;
import com.innoviussoftwaresolution.tjss.model.network.response.FavoritePlace;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;
import com.innoviussoftwaresolution.tjss.model.repository.FavoritePlacesRepository;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 08-08-17.
 */

public class FavoritePlacesViewModel extends ViewModelBase {

    private final MutableLiveData<List<FavoritePlace>> mFavPlacesData
            = new MutableLiveData<>();

    private final MutableLiveData<AddFavPlaceResponse> mAddFavPlaceResponse = new MutableLiveData<>();
    FavoritePlacesRepository mFavoritePlacesRepository = new FavoritePlacesRepository();
    private MutableLiveData<StatusMessage> mStatusMessageData
            = new MutableLiveData<>();
    private RequestCallback<StatusMessage> mRequestCallback
            = new RequestCallback<StatusMessage>() {
        @Override
        public void onSuccess(StatusMessage result) {
            mStatusMessageData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };
    private RequestCallback<AddFavPlaceResponse> mAddFavPlaceCallback
            = new RequestCallback<AddFavPlaceResponse>() {
        @Override
        public void onSuccess(AddFavPlaceResponse result) {
            mAddFavPlaceResponse.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };
    private RequestCallback<List<FavoritePlace>> mFavPlacesRequestCallback
            = new RequestCallback<List<FavoritePlace>>() {
        @Override
        public void onSuccess(List<FavoritePlace> result) {
            mFavPlacesData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };
    private FavoritePlacesViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<StatusMessage> getStatusMessageData() {
        return mStatusMessageData;
    }

    public MutableLiveData<AddFavPlaceResponse> getAddFavPlaceResponse() {
        return mAddFavPlaceResponse;
    }

    public void addFavPlace(LatLng latLng, String name, String address, String placeId, int radius) {
        mFavoritePlacesRepository.addFavPlace(
                getApplication().getString(R.string.add_fav_place_path),
                PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, ""),
                PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_USER_ID, ""),
                name, address, latLng.latitude, latLng.longitude, placeId, radius, mAddFavPlaceCallback
        );
    }

    public void editFavPlace(LatLng latLng, String name, String address, String placeId, int radius,
                             String favPlaceId) {
        String path = getApplication().getString(R.string.fav_place_edit_path);
        HashMap<String, String> params = getUserIdParamsMap();
        params.put("name", name);
        params.put("address", address);
        params.put("placeId", placeId);
        params.put("latitude", String.valueOf(latLng.latitude));
        params.put("longitude", String.valueOf(latLng.longitude));
        params.put("favoriteId", favPlaceId);
        params.put("radius", String.valueOf(radius));
        mFavoritePlacesRepository.edit(path, getHeaders(), params, mRequestCallback);
    }


    public void deleteFavPlace(String favPlaceId) {
        String path = getApplication().getString(R.string.delete_fav_place_path);
        HashMap<String, String> params = getUserIdParamsMap();
        params.put("favoriteId", favPlaceId);
        mFavoritePlacesRepository.delete(path, getHeaders(), params, mRequestCallback);
    }



    public MutableLiveData<List<FavoritePlace>> getFavPlacesData() {
        return mFavPlacesData;
    }

    public void getFavPlaces() {
        new FavoritePlacesRepository().getFavoritePlaces(getApplication().getString(R.string.user_fav_places),
                PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, ""),
                PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_USER_ID, ""),
                mFavPlacesRequestCallback);
    }

    @SuppressWarnings("unchecked")
    public static class FavouritePlacesViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public FavouritePlacesViewModelFactory(Application application) {
            this.mApplication = application;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new FavoritePlacesViewModel(mApplication);
        }
    }

}
