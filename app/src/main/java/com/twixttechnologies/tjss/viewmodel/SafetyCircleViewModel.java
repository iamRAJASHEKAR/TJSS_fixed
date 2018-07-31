package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.request.helper.RequestHelper;
import com.twixttechnologies.tjss.model.network.response.CreateSafetyCircleResponse;
import com.twixttechnologies.tjss.model.network.response.InviteCode;
import com.twixttechnologies.tjss.model.network.response.SafetyCircle;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;
import com.twixttechnologies.tjss.model.network.response.SignUpAndLoginResponse;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.model.network.response.UserIdAndStatus;
import com.twixttechnologies.tjss.model.repository.SafetyCircleRepository;
import com.twixttechnologies.tjss.utils.PreferenceHelper;
import com.twixttechnologies.tjss.view.fragment.signup.SignUpFragment;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Sony Raj on 09-08-17.
 */

public class SafetyCircleViewModel extends ViewModelBase {

    private SafetyCircleRepository mRepository;

    private SignUpViewModel mSignUpViewModel;

    private MutableLiveData<CreateSafetyCircleResponse> mCreateSafetyCircleData
            = new MutableLiveData<>();

    private MutableLiveData<List<SafetyCircle>> mSafetyCirclesListData
            = new MutableLiveData<>();

    private MutableLiveData<StatusMessage> mStatusData
            = new MutableLiveData<>();

    private MutableLiveData<SafetyCircleMember[]> mSafetyCircleMembersData
            = new MutableLiveData<>();

    private MutableLiveData<UserIdAndStatus> mDeleteResponseData
            = new MutableLiveData<>();

    private MutableLiveData<StatusMessage> mUpdateAdminStatusMessageData
            = new MutableLiveData<>();

    private MutableLiveData<StatusMessage> mSafetyCircleUpdateData
            = new MutableLiveData<>();

    private MutableLiveData<InviteCode> mInviteCodeData
            = new MutableLiveData<>();


    private RequestCallback<UserIdAndStatus> mDeleteCallback
            = new RequestCallback<UserIdAndStatus>() {
        @Override
        public void onSuccess(UserIdAndStatus result) {
            mDeleteResponseData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

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


    private RequestCallback<List<SafetyCircle>> mSafetyCircleListCallback
            = new RequestCallback<List<SafetyCircle>>() {
        @Override
        public void onSuccess(List<SafetyCircle> result) {
            mSafetyCirclesListData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private RequestCallback<CreateSafetyCircleResponse> mCreateSafetyCircleRequestCallback
            = new RequestCallback<CreateSafetyCircleResponse>() {
        @Override
        public void onSuccess(CreateSafetyCircleResponse result) {
            mCreateSafetyCircleData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };
    private RequestCallback<StatusMessage> mJoinRequestCallback
            = new RequestCallback<StatusMessage>() {
        @Override
        public void onSuccess(StatusMessage result) {
            mStatusData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private RequestCallback<StatusMessage> mUpdateAdminCallback
            = new RequestCallback<StatusMessage>() {
        @Override
        public void onSuccess(StatusMessage result) {
            mUpdateAdminStatusMessageData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private RequestCallback<StatusMessage> mUpdateSafetyCircleCallback
            = new RequestCallback<StatusMessage>() {
        @Override
        public void onSuccess(StatusMessage result) {
            mSafetyCircleUpdateData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };


    private RequestCallback<InviteCode> mInviteCodeRequestCallback
            = new RequestCallback<InviteCode>() {
        @Override
        public void onSuccess(InviteCode result) {
            mInviteCodeData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    //Constructor
    private SafetyCircleViewModel(Application application) {
        super(application);
        mRepository = new SafetyCircleRepository();
    }

    public void signUp(Fragment fragment, Bundle userData,
                       Observer<SignUpAndLoginResponse> signUpResponseObserver) {
        if (mSignUpViewModel == null) {
            mSignUpViewModel = ViewModelProviders.of(fragment,
                    new SignUpViewModel.SignUpViewModelFactory(getApplication()))
                    .get(SignUpViewModel.class);

            mSignUpViewModel.getSignUpResponseLiveData().observe((LifecycleOwner) fragment, signUpResponseObserver);
        }
        try {
            mSignUpViewModel.signUpByPayment(userData.getString(SignUpFragment.NAME),
                    userData.getString(SignUpFragment.EMAIL),
                    userData.getString(SignUpFragment.PHONE),
                    userData.getString(SignUpFragment.ISD_CODE),
                    userData.getString(SignUpFragment.PASSWORD),
                    "",
                    "",
                    userData.getString(SignUpFragment.IMAGE),
                    UUID.randomUUID().toString());
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void getCircleMembers(String circleId) {
        if (mRepository == null) mRepository = new SafetyCircleRepository();
        String url = getApplication().getString(R.string.safety_circle_members_list_path);
        String userId = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_USER_ID, "");
        String authToken = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
        mRepository.getCircleMembers(url, authToken, userId, circleId, mSafetyCircleMembersListCallback);
    }

    public MutableLiveData<List<SafetyCircle>> getSafetyCirclesListData() {
        return mSafetyCirclesListData;
    }

    public MutableLiveData<UserIdAndStatus> getDeleteResponseData() {
        return mDeleteResponseData;
    }

    public MutableLiveData<StatusMessage> getSafetyCircleUpdateData() {
        return mSafetyCircleUpdateData;
    }

    public MutableLiveData<InviteCode> getInviteCodeData() {
        return mInviteCodeData;
    }

    public void deleteMember(String circleId, String userId) {
        if (mRepository == null) mRepository = new SafetyCircleRepository();
        String url = getApplication().getString(R.string.delete_circle_member_path);
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("circleId", circleId);
        mRepository.deleteMemberFromCircle(url, getHeaders(), params, mDeleteCallback);
    }

    public void getSafetyCircleList() {
        String path = getApplication().getString(R.string.user_s_safety_circle_link_path);
        String userId = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_USER_ID, "");
        String authToken = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
        mRepository.getSimpleSafetyCircles(path, authToken, userId, mSafetyCircleListCallback);
    }

    public MutableLiveData<StatusMessage> getStatusData() {
        return mStatusData;
    }

    public MutableLiveData<SafetyCircleMember[]> getSafetyCircleMembersData() {
        return mSafetyCircleMembersData;
    }

    public MutableLiveData<CreateSafetyCircleResponse> getCreateSafetyCircleData() {
        return mCreateSafetyCircleData;
    }

    public MutableLiveData<StatusMessage> getUpdateAdminStatusMessageData() {
        return mUpdateAdminStatusMessageData;
    }

    public void create(String safetyCircleName, boolean isPrimary) {
        if (TextUtils.isEmpty(safetyCircleName)) {
            mError.setValue("Please enter a new name for your safety circle or select one from the chatItems");
            return;
        }

//        SharedPreferences preferences = getApplication().getSharedPreferences("userId", 0);
//        SharedPreferences.Editor editor = preferences.edit();

        SafetyCircleRepository repository = new SafetyCircleRepository();
        repository.createSafetyCircle(getApplication().getString(R.string.create_circle_path),
                PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, ""),
                PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_USER_ID, ""),
                //preferences.getString("userId",""),
                safetyCircleName, isPrimary, mCreateSafetyCircleRequestCallback);
//        editor.commit();
//        editor.clear();

    }

    public void join(String joinCode) {
        SafetyCircleRepository safetyCircleRepository = new SafetyCircleRepository();
        safetyCircleRepository.join(getApplication().getString(R.string.join_circle_by_invite_code_after_login_path),
                PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, ""),
                PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_USER_ID, ""),
                joinCode, mJoinRequestCallback);
    }


    public void updateAdminStatus(SafetyCircleMember safetyCircleMember, String circleId) {
        String path = getApplication().getString(R.string.safety_circle_admin_path);
        HashMap<String, String> params = new HashMap<>(2);
        params.put("circleId", circleId);
        params.put("userId", safetyCircleMember.userId);
        mRepository.updateAdminStatus(path, getHeaders(), params, mUpdateAdminCallback);
    }

    public void updateSafetyCircle(String imageLink, String circleName, String circleId) {
        String path = getApplication().getString(R.string.update_safety_circle_path);
        HashMap<String, RequestBody> params = new HashMap<>(2);
        params.put("circleId", RequestHelper.getRequestBody(circleId));
        params.put("circleName", RequestHelper.getRequestBody(circleName));
        MultipartBody.Part image = null;
        if (!TextUtils.isEmpty(imageLink))
            image = RequestHelper.multiPartBodyPart(new File(imageLink), "circleImage");

        mRepository.updateSafetyCircle(path, getHeaders(), params, image, mUpdateSafetyCircleCallback);
    }

    public void getInviteCode(String circleId) {
        String path = getApplication().getString(R.string.get_invite_code_path);
        HashMap<String, String> params = getUserIdParamsMap();
        params.put("circleId", circleId);
        mRepository.getInviteCode(path, getHeaders(), params, mInviteCodeRequestCallback);
    }


    @SuppressWarnings("unchecked")
    public static class SafetyCircleViewModelFactory implements ViewModelProvider.Factory {

        private final Application application;


        public SafetyCircleViewModelFactory(Application application) {
            this.application = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new SafetyCircleViewModel(application);
        }
    }

}
