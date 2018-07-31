package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.model.repository.LocationRepository;
import com.twixttechnologies.tjss.model.repository.SafetyCircleRepository;
import com.twixttechnologies.tjss.utils.PreferenceHelper;

import java.util.HashMap;

/**
 * @author Sony Raj on 19-09-17.
 */
public class LocationSharingViewModel extends ViewModelBase {

    private final MutableLiveData<StatusMessage> mStatusMessageData
            = new MutableLiveData<>();
    private final RequestCallback<StatusMessage> mStatusMessageCallback
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
    private MutableLiveData<SafetyCircleMember[]> mSafetyCircleMembersData
            = new MutableLiveData<>();
    private RequestCallback<SafetyCircleMember[]> mSafetyCircleMembersListCallback
            = new RequestCallback<SafetyCircleMember[]>() {
        @Override
        public void onSuccess(SafetyCircleMember[] result) {
            mSafetyCircleMembersData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private LocationSharingViewModel(Application application) {
        super(application);
        getCircleMembers();
    }

    public MutableLiveData<SafetyCircleMember[]> getSafetyCircleMembersData() {
        return mSafetyCircleMembersData;
    }

    public MutableLiveData<StatusMessage> getStatusMessageData() {
        return mStatusMessageData;
    }

    public void update(boolean updateLocation) {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("locationSharingstatus", updateLocation ? "1" : "0");
        params.put("userId", getUserId());

        String url = getApplication().getString(R.string.location_sharing_path);

        new LocationRepository().updateLocationSharing(url, getHeaders(), params, mStatusMessageCallback);

    }


    private void getCircleMembers() {
        String circleId = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_PRIMARY_CIRCLE, "");
        SafetyCircleRepository repository = new SafetyCircleRepository();
        String url = getApplication().getString(R.string.safety_circle_members_list_path);
        String userId = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_USER_ID, "");
        String authToken = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
        repository.getCircleMembers(url, authToken, userId, circleId, mSafetyCircleMembersListCallback);
    }


    @SuppressWarnings("unchecked")
    public static class LocationSharingViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public LocationSharingViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new LocationSharingViewModel(mApplication);
        }
    }


}