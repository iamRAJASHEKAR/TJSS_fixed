package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.CheckInHistory;
import com.innoviussoftwaresolution.tjss.model.repository.TjssRepository;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 01-11-17.
 */
public class CheckInViewModel extends ViewModelBase {

    private final TjssRepository mRepository;

    private MutableLiveData<List<CheckInHistory>> mHistoryData
            = new MutableLiveData<>();

    private final RequestCallback<List<CheckInHistory>> mHistoryDataCallback
            = new RequestCallback<List<CheckInHistory>>() {
        @Override
        public void onSuccess(List<CheckInHistory> result) {
            mHistoryData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private CheckInViewModel(Application application) {
        super(application);
        mRepository = new TjssRepository();
    }

    public MutableLiveData<List<CheckInHistory>> getHistoryData() {
        return mHistoryData;
    }

    public void getHistory(String memberId) {
        String path = getApplication().getString(R.string.check_in_history_path);
        HashMap<String, String> params = new HashMap<>(1);
        params.put("userId", memberId);
        mRepository.getCheckInChistory(path, getHeaders(), params, mHistoryDataCallback);
    }


    @SuppressWarnings("unchecked")
    public static class CheckInViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public CheckInViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new CheckInViewModel(mApplication);
        }
    }


}