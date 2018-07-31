package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.response.CircleSwitchResponse;
import com.twixttechnologies.tjss.model.network.response.FavoritePlace;
import com.twixttechnologies.tjss.model.network.response.Incident;
import com.twixttechnologies.tjss.model.network.response.SafetyCircle;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;
import com.twixttechnologies.tjss.model.network.response.ServiceProvider;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.model.network.response.UnreadMessage;
import com.twixttechnologies.tjss.model.network.response.nearby.NearbySearchResponse;
import com.twixttechnologies.tjss.model.repository.FavoritePlacesRepository;
import com.twixttechnologies.tjss.model.repository.IncidentsRepository;
import com.twixttechnologies.tjss.model.repository.NearbyPlacesRepository;
import com.twixttechnologies.tjss.model.repository.SafetyCircleRepository;
import com.twixttechnologies.tjss.model.repository.ServiceProviderRepository;
import com.twixttechnologies.tjss.model.repository.TjssRepository;
import com.twixttechnologies.tjss.utils.PreferenceHelper;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 07-08-17.
 */

public class UserHomeViewModel extends ViewModelBase {

    private final MutableLiveData<List<FavoritePlace>> mFavoritePlacesData
            = new MutableLiveData<>();

    private final MutableLiveData<String> mError
            = new MutableLiveData<>();

    private final MutableLiveData<List<SafetyCircle>> mSafetyCirclesData
            = new MutableLiveData<>();

    private final MutableLiveData<SafetyCircleMember[]> mActiveSafetyCircleMembersData
            = new MutableLiveData<>();

    private final MutableLiveData<Incident[]> mIncidentsLogData
            = new MutableLiveData<>();

    private final MutableLiveData<NearbySearchResponse> mHospitalsData
            = new MutableLiveData<>();

    private final MutableLiveData<NearbySearchResponse> mFireStationsData
            = new MutableLiveData<>();

    private final MutableLiveData<NearbySearchResponse> mPoliceStationData
            = new MutableLiveData<>();

    private final MutableLiveData<List<ServiceProvider>> mServiceProviderData
            = new MutableLiveData<>();

    private final MutableLiveData<CircleSwitchResponse> mSafetyCircleSwitchData
            = new MutableLiveData<>();

    private final MutableLiveData<StatusMessage> mLocationUpdateStatusData
            = new MutableLiveData<>();

    private final MutableLiveData<UnreadMessage> mUnreadMessageData
            = new MutableLiveData<>();

    private FavoritePlacesRepository mFavoritePlacesRepository;
    private SafetyCircleRepository mSafetyCircleRepository;
    private IncidentsRepository mIncidentsRepository;
    private ServiceProviderRepository mServiceProviderRepository;
    private TjssRepository mTjssRepository = new TjssRepository();

    private RequestCallback<SafetyCircleMember[]> mActiveCircleMembersCallback
            = new RequestCallback<SafetyCircleMember[]>() {
        @Override
        public void onSuccess(SafetyCircleMember[] result) {
            mActiveSafetyCircleMembersData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private RequestCallback<List<FavoritePlace>> mFavoritePlaceRequestCallback
            = new RequestCallback<List<FavoritePlace>>() {
        @Override
        public void onSuccess(List<FavoritePlace> result) {
            mFavoritePlacesData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };


    private RequestCallback<List<SafetyCircle>> mSafetyCircleRequestCallback
            = new RequestCallback<List<SafetyCircle>>() {
        @Override
        public void onSuccess(List<SafetyCircle> result) {
            mSafetyCirclesData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private RequestCallback<Incident[]> mIncidentsCallback
            = new RequestCallback<Incident[]>() {
        @Override
        public void onSuccess(Incident[] result) {
            mIncidentsLogData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private RequestCallback<NearbySearchResponse> mHospitalSearchResponse
            = new RequestCallback<NearbySearchResponse>() {
        @Override
        public void onSuccess(NearbySearchResponse result) {
            mHospitalsData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private RequestCallback<NearbySearchResponse> mFireStationSearchResponse
            = new RequestCallback<NearbySearchResponse>() {
        @Override
        public void onSuccess(NearbySearchResponse result) {
            mFireStationsData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private RequestCallback<NearbySearchResponse> mPoliceStationSearchResponse
            = new RequestCallback<NearbySearchResponse>() {
        @Override
        public void onSuccess(NearbySearchResponse result) {
            mPoliceStationData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };


    private RequestCallback<List<ServiceProvider>> mServiceProvidersCallback
            = new RequestCallback<List<ServiceProvider>>() {
        @Override
        public void onSuccess(List<ServiceProvider> result) {
            mServiceProviderData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {

        }
    };

    private RequestCallback<CircleSwitchResponse> mCircleSwitchRequestCallback
            = new RequestCallback<CircleSwitchResponse>() {
        @Override
        public void onSuccess(CircleSwitchResponse result) {
            if (result == null) return;
            PreferenceHelper.saveString(getApplication(), PreferenceHelper.KEY_PRIMARY_CIRCLE, result.circleId);
            mSafetyCircleSwitchData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private RequestCallback<StatusMessage> mLocationUpdateCallback
            = new RequestCallback<StatusMessage>() {
        @Override
        public void onSuccess(StatusMessage result) {
            mLocationUpdateStatusData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {

        }
    };

    private RequestCallback<UnreadMessage> mUnreadMessageRequestCallback
            = new RequestCallback<UnreadMessage>() {
        @Override
        public void onSuccess(UnreadMessage result) {
            mUnreadMessageData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {

        }
    };


    private UserHomeViewModel(Application application) {
        super(application);
        mFavoritePlacesRepository = new FavoritePlacesRepository();
    }

    public MutableLiveData<UnreadMessage> getUnreadMessageData() {
        return mUnreadMessageData;
    }

    public MutableLiveData<List<ServiceProvider>> getServiceProvidersData() {
        return mServiceProviderData;
    }

    public NearbySearchResponse getHospitalData(HashMap<String, String> queryParams) {
        if (mHospitalsData.getValue() == null) {
            String path = getApplication().getString(R.string.near_by_path);
            queryParams.put("key", getApplication().getString(R.string.google_places_key));
            new NearbyPlacesRepository().get(path, queryParams, mHospitalSearchResponse);
            return null;
        } else {
            return mHospitalsData.getValue();
        }
    }

    public NearbySearchResponse getFireStationData(HashMap<String, String> queryParams) {
        if (mFireStationsData.getValue() == null) {
            String path = getApplication().getString(R.string.near_by_path);
            queryParams.put("key", getApplication().getString(R.string.google_places_key));
            new NearbyPlacesRepository().get(path, queryParams, mFireStationSearchResponse);
            return null;
        } else {
            return mFireStationsData.getValue();
        }
    }

    public NearbySearchResponse getPoliceStationData(HashMap<String, String> queryParams) {
        if (mPoliceStationData.getValue() == null) {
            String path = getApplication().getString(R.string.near_by_path);
            queryParams.put("key", getApplication().getString(R.string.google_places_key));
            new NearbyPlacesRepository().get(path, queryParams, mPoliceStationSearchResponse);
            return null;
        } else {
            return mPoliceStationData.getValue();
        }
    }

    public void getUnreadMessages() {
        String path = getApplication().getString(R.string.unread_message_count_path);
        String circleId = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_PRIMARY_CIRCLE, "");

        HashMap<String, String> params = getUserIdParamsMap();
        params.put("circleId", circleId);
        mTjssRepository.getUnreadMessages(path, getHeaders(), params, mUnreadMessageRequestCallback);
    }

    public void getServiceProviders(Location location) {
        if (mServiceProviderRepository == null)
            mServiceProviderRepository = new ServiceProviderRepository();
        String path = getApplication().getString(R.string.service_providers_list_path);
        HashMap<String, String> params = new HashMap<>(2);
        params.put("latitude", String.valueOf(location.getLatitude()));
        params.put("longitude", String.valueOf(location.getLongitude()));
        mServiceProviderRepository.getServiceProviders(path, getHeaders(), params, mServiceProvidersCallback);
    }

    public void getSafetyCircles() {
        String userId = PreferenceHelper.getString(UserHomeViewModel.this.getApplication(), PreferenceHelper.KEY_USER_ID, "");
        String authToken = PreferenceHelper.getString(UserHomeViewModel.this.getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
        if (mSafetyCircleRepository == null) mSafetyCircleRepository = new SafetyCircleRepository();
        mSafetyCircleRepository.getSimpleSafetyCircles(getApplication().getString(R.string.user_s_safety_circle_link_path),
                authToken, userId, mSafetyCircleRequestCallback);
    }

    public void getFavPlaces() {
        if (mFavoritePlacesRepository == null)
            mFavoritePlacesRepository = new FavoritePlacesRepository();
        String userId = PreferenceHelper.getString(UserHomeViewModel.this.getApplication(), PreferenceHelper.KEY_USER_ID, "");
        String authToken = PreferenceHelper.getString(UserHomeViewModel.this.getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
        mFavoritePlacesRepository.getFavoritePlaces(getApplication().getString(R.string.user_fav_places),
                authToken, userId, mFavoritePlaceRequestCallback);
    }

    public void getActiveCircleMembers() {
        if (mSafetyCircleRepository == null) mSafetyCircleRepository = new SafetyCircleRepository();
        String path = getApplication().getString(R.string.safety_circle_members_list_path);
        String userId = PreferenceHelper.getString(this.getApplication(),
                PreferenceHelper.KEY_USER_ID, "");
        String primaryCircleId = PreferenceHelper.getString(this.getApplication(),
                PreferenceHelper.KEY_PRIMARY_CIRCLE, "");
        String authToken = PreferenceHelper.getString(this.getApplication(),
                PreferenceHelper.KEY_ACCESS_TOKEN, "");
        mSafetyCircleRepository.getCircleMembers(path, authToken, userId, primaryCircleId,
                mActiveCircleMembersCallback);
    }

    public void getIncidentLog() {
        if (mIncidentsRepository == null) mIncidentsRepository = new IncidentsRepository();
        String userId = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_USER_ID, "");
        String path = getApplication().getString(R.string.incident_log_path);
        String authToken = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
        mIncidentsRepository.getIncidentLog(path, authToken, userId, mIncidentsCallback);
    }


    public void switchSafetyCircle(String circleId, String circleName) {
        String path = getApplication().getString(R.string.switch_safety_circle_path);
        HashMap<String, String> params = getUserIdParamsMap();
        params.put("circleId", circleId);
        params.put("circleName", circleName);
        if (mSafetyCircleRepository == null) mSafetyCircleRepository = new SafetyCircleRepository();
        mSafetyCircleRepository.switchCircle(path, getHeaders(), params, mCircleSwitchRequestCallback);
    }

    public void updateLocation(LatLng latLng) {
        String path = getApplication().getString(R.string.update_user_location);
        mTjssRepository.updateLocation(path, getToken(), getUserId(), latLng.latitude, latLng.longitude, mLocationUpdateCallback);
    }

    public MutableLiveData<Incident[]> getIncidentsLogData() {
        return mIncidentsLogData;
    }

    public MutableLiveData<SafetyCircleMember[]> getActiveSafetyCircleMembersData() {
        return mActiveSafetyCircleMembersData;
    }

    public MutableLiveData<List<SafetyCircle>> getSafetyCirclesData() {
        return mSafetyCirclesData;
    }

    public MutableLiveData<List<FavoritePlace>> getFavoritePlacesData() {
        return mFavoritePlacesData;
    }

    public MutableLiveData<NearbySearchResponse> getHospitalsData() {
        return mHospitalsData;
    }

    public MutableLiveData<NearbySearchResponse> getFireStationsData() {
        return mFireStationsData;
    }

    public MutableLiveData<NearbySearchResponse> getPoliceStationData() {
        return mPoliceStationData;
    }

    public MutableLiveData<String> getError() {
        return mError;
    }

    public MutableLiveData<CircleSwitchResponse> getSafetyCircleSwitchData() {
        return mSafetyCircleSwitchData;
    }

    @SuppressWarnings("unchecked")
    public static class UserHomeViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;


        public UserHomeViewModelFactory(Application mApplication) {
            this.mApplication = mApplication;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new UserHomeViewModel(mApplication);
        }
    }

}
