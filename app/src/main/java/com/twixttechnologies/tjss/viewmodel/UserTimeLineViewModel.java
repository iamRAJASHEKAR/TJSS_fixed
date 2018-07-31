package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.response.UserTimeLineItem;
import com.twixttechnologies.tjss.model.repository.UserProfileRepository;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 07-10-17.
 */
public class UserTimeLineViewModel extends ViewModelBase {


    private final UserProfileRepository mRepository = new UserProfileRepository();

    private MutableLiveData<List<UserTimeLineItem>> mTimeLineData
            = new MutableLiveData<>();

    private final RequestCallback<List<UserTimeLineItem>> mCallback
            = new RequestCallback<List<UserTimeLineItem>>() {
        @Override
        public void onSuccess(List<UserTimeLineItem> result) {
            mTimeLineData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private UserTimeLineViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<List<UserTimeLineItem>> getTimeLineData() {
        return mTimeLineData;
    }

    public void getTimeLine(String userId) {
        String url = getApplication().getString(R.string.user_time_line_data_path);
        HashMap<String,String> params = new HashMap<>(1);
        params.put("userId",userId);

        mRepository.getTimeLine(url, getHeaders(), params, mCallback);
    }


    @SuppressWarnings("unchecked")
    public static class UserTimeLineViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public UserTimeLineViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new UserTimeLineViewModel(mApplication);
        }
    }


}