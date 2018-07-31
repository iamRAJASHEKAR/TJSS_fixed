package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.GroupMember;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.repository.GroupDetailRepository;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 28-10-17.
 */
public class GroupDetailViewModel extends ViewModelBase {

    private final GroupDetailRepository mRepository;

    private MutableLiveData<List<GroupMember>> mMembersData
            = new MutableLiveData<>();

    private RequestCallback<List<GroupMember>> mMembersREquestCallback
            = new RequestCallback<List<GroupMember>>() {
        @Override
        public void onSuccess(List<GroupMember> result) {
            mMembersData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };


    private GroupDetailViewModel(Application application) {
        super(application);
        mRepository = new GroupDetailRepository();
    }

    public MutableLiveData<List<GroupMember>> getMembersData() {
        return mMembersData;
    }

    public void getGroupMembers(String groupId) {
        String path = getApplication().getString(R.string.group_members_listing_path);
        HashMap<String, String> params = getUserIdParamsMap();
        params.put("groupId", groupId);

        mRepository.listMembers(path, getHeaders(), params, mMembersREquestCallback);

    }


    @SuppressWarnings("unchecked")
    public static class GroupDetailViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public GroupDetailViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new GroupDetailViewModel(mApplication);
        }
    }


}