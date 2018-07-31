package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.util.Log;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.request.helper.RequestHelper;
import com.twixttechnologies.tjss.model.network.response.IsdCode;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.model.network.response.UserProfile;
import com.twixttechnologies.tjss.model.repository.IsdRepository;
import com.twixttechnologies.tjss.model.repository.UserProfileRepository;
import com.twixttechnologies.tjss.utils.PreferenceHelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Sony Raj on 09-08-17.
 */

public class UserProfileViewModel extends ViewModelBase {

    private final UserProfileRepository repository;

    private final MutableLiveData<UserProfile> mUserProfileData = new MutableLiveData<>();
    private final MutableLiveData<List<IsdCode>> mIsdCodesData = new MutableLiveData<>();

    private final MutableLiveData<StatusMessage> mProfileImageData
            = new MutableLiveData<>();

    private final MutableLiveData<StatusMessage> mFirstNameAndLastNameData
            = new MutableLiveData<>();

    private final RequestCallback<UserProfile> mUserProfileRequestCallback
            = new RequestCallback<UserProfile>() {
        @Override
        public void onSuccess(UserProfile result) {
            mUserProfileData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };


    private final RequestCallback<List<IsdCode>> mIsdCodesRequestCallback
            = new RequestCallback<List<IsdCode>>() {
        @Override
        public void onSuccess(List<IsdCode> result) {
            mIsdCodesData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private UserProfileViewModel(Application application) {
        super(application);
        repository = new UserProfileRepository();
        getUSerProfile();
    }

    public MutableLiveData<StatusMessage> getProfileImageData() {
        return mProfileImageData;
    }

    public MutableLiveData<StatusMessage> getFirstNameAndLastNameData() {
        return mFirstNameAndLastNameData;
    }

    public MutableLiveData<UserProfile> getUserProfileData() {
        return mUserProfileData;
    }

    public MutableLiveData<List<IsdCode>> getIsdCodesData() {
        return mIsdCodesData;
    }

    public void getUSerProfile() {
        repository.get(getApplication().getString(R.string.user_profile_path),
                PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, ""),
                PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_USER_ID, ""),
                mUserProfileRequestCallback);
    }

    public void updateImage(File image) {
        String path = getApplication().getString(R.string.profile_image_update_image);
        HashMap<String, RequestBody> params = new HashMap<>(1);
        params.put("userId", RequestHelper.getRequestBody(PreferenceHelper.getString(getApplication(), PreferenceHelper.KEY_USER_ID, "")));
        MultipartBody.Part dp = RequestHelper.multiPartBodyPart(image, "profileImage");

        repository.updateUserProfileImage(path, getHeaders(), params, dp, new RequestCallback<StatusMessage>() {
            @Override
            public void onSuccess(StatusMessage result) {
                mProfileImageData.setValue(result);
            }

            @Override
            public void onFailure(String reason) {
                mError.setValue(reason);
            }
        });
    }

    public void updateFirstAndlastName(String fNmae, String lName) {
        String path = getApplication().getString(R.string.first_and_last_name_update_path);
        HashMap<String, String> params = getUserIdParamsMap();
        params.put("fname", fNmae);
        params.put("lname", lName);

        repository.updateFirstAndLastName(path, getHeaders(), params, new RequestCallback<StatusMessage>() {
            @Override
            public void onSuccess(StatusMessage result) {
                mFirstNameAndLastNameData.setValue(result);
            }

            @Override
            public void onFailure(String reason) {
                mError.setValue(reason);
            }
        });
    }


    public void getIsdCodes() {
        IsdRepository repository = new IsdRepository();
        repository.get(getApplication().getString(R.string.isd_codes_url), mIsdCodesRequestCallback);
    }


    @SuppressWarnings("unchecked")
    public static class UserProfileViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public UserProfileViewModelFactory(Application appilction) {
            this.mApplication = appilction;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new UserProfileViewModel(mApplication);
        }
    }

}
