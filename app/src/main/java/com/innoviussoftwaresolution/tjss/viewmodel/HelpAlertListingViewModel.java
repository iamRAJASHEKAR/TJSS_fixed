package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.HelpAlert;
import com.innoviussoftwaresolution.tjss.model.repository.HelpAlertRepository;

import java.util.List;

/**
 * @author Sony Raj on 02-11-17.
 */
public class HelpAlertListingViewModel extends ViewModelBase {

    private final HelpAlertRepository mRepository;

    private final MutableLiveData<List<HelpAlert>> mHelpAlertHistoryData
            = new MutableLiveData<>();

    private final RequestCallback<List<HelpAlert>> mHelpAlertHistoryRequestCallback
            = new RequestCallback<List<HelpAlert>>() {
        @Override
        public void onSuccess(List<HelpAlert> result) {
            mHelpAlertHistoryData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private HelpAlertListingViewModel(Application application) {
        super(application);
        mRepository = new HelpAlertRepository();
        getHistory();
    }

    public MutableLiveData<List<HelpAlert>> getHelpAlertHistoryData() {
        return mHelpAlertHistoryData;
    }

    public void getHistory() {
        String path = getApplication().getString(R.string.help_alerts_listing_path);
        mRepository.getHelpAlertHistory(path, getHeaders(), getUserIdParamsMap(), mHelpAlertHistoryRequestCallback);
    }


    @SuppressWarnings("unchecked")
    public static class HelpAlertListingViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public HelpAlertListingViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new HelpAlertListingViewModel(mApplication);
        }
    }


}