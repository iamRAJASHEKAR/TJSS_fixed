package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;
import com.twixttechnologies.tjss.model.repository.SafetyCircleRepository;
import com.twixttechnologies.tjss.utils.PreferenceHelper;

/**
 * @author Sony Raj on 02-10-17.
 */
abstract class CreateChatViewModelBase extends ViewModelBase {

    final MutableLiveData<SafetyCircleMember[]> mSafetyCircleMembersData
            = new MutableLiveData<>();
    final RequestCallback<SafetyCircleMember[]> mSafetyCircleMembersRequestCallback
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
    private final SafetyCircleRepository mSafetyCircleRepository;

    CreateChatViewModelBase(Application application) {
        super(application);
        mSafetyCircleRepository = new SafetyCircleRepository();
        getSafetyCircleMembers();
    }

    public MutableLiveData<SafetyCircleMember[]> getSafetyCircleMembersData() {
        return mSafetyCircleMembersData;
    }

    private void getSafetyCircleMembers() {
        String url = getApplication().getString(R.string.safety_circle_members_list_path);
        String authToken = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
        String circleId = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_PRIMARY_CIRCLE, "");
        mSafetyCircleRepository.getCircleMembers(url, authToken, getUserId(), circleId, mSafetyCircleMembersRequestCallback);
    }

}