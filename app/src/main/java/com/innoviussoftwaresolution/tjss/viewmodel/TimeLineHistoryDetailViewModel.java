package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.PathInfo;
import com.innoviussoftwaresolution.tjss.model.repository.UserProfileRepository;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 09-10-17.
 */
public class TimeLineHistoryDetailViewModel extends ViewModelBase {

    private final UserProfileRepository mRepository = new UserProfileRepository();

    private final MutableLiveData<List<PathInfo>> mPathData
            = new MutableLiveData<>();

    private RequestCallback<List<PathInfo>> mPathRequestCallback
            = new RequestCallback<List<PathInfo>>() {
        @Override
        public void onSuccess(List<PathInfo> result) {
            mPathData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private TimeLineHistoryDetailViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<List<PathInfo>> getPathData() {
        return mPathData;
    }

    public void getPath(String historyId,String userId) {
        String path = getApplication().getString(R.string.user_time_line_path_info_path);
        HashMap<String, String> ids = new HashMap<>(2);
        ids.put("historyId", historyId);
        ids.put("userId", userId);
        mRepository.getPath(path, getHeaders(), ids, mPathRequestCallback);
    }


    @SuppressWarnings("unchecked")
    public static class TimeLineHistoryDetailViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public TimeLineHistoryDetailViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new TimeLineHistoryDetailViewModel(mApplication);
        }
    }


}