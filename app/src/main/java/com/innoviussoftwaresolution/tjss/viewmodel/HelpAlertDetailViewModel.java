package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.HelpAlert;
import com.innoviussoftwaresolution.tjss.model.network.response.HelpAlertDetail;
import com.innoviussoftwaresolution.tjss.model.repository.HelpAlertRepository;

import java.util.HashMap;

/**
 * @author Sony Raj on 02-11-17.
 */
public class HelpAlertDetailViewModel extends ViewModelBase {


    private final HelpAlertRepository mRepository;

    private final MutableLiveData<HelpAlertDetail> mHelpAlertDetailData
            = new MutableLiveData<>();

    private final RequestCallback<HelpAlertDetail> mHelpAlertDetailRequestCallback
            = new RequestCallback<HelpAlertDetail>() {
        @Override
        public void onSuccess(HelpAlertDetail result) {
            mHelpAlertDetailData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private HelpAlertDetailViewModel(Application application) {
        super(application);
        mRepository = new HelpAlertRepository();
    }

    public MutableLiveData<HelpAlertDetail> getHelpAlertDetailData() {
        return mHelpAlertDetailData;
    }

    public void getDetails(HelpAlert helpAlert) {
        String path = getApplication().getString(R.string.help_alert_detail_path);
        HashMap<String, String> params = getUserIdParamsMap();
        params.put("alertId", String.valueOf(helpAlert.alertId));
        mRepository.getHelpAlertDetail(path, getHeaders(), params, mHelpAlertDetailRequestCallback);
    }

    @SuppressWarnings("unchecked")
    public static class HelpAlertDetailViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public HelpAlertDetailViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new HelpAlertDetailViewModel(mApplication);
        }
    }


}