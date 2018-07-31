package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.response.AppServiceProvider;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.model.repository.AppServiceProvidersRepository;
import com.twixttechnologies.tjss.model.repository.HelpAlertRepository;
import com.twixttechnologies.tjss.utils.LocationUtil;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.utils.PreferenceHelper;

import java.util.HashMap;
import java.util.List;


/**
 * @author Sony Raj on 25-09-17.
 */
public class AppServiceProvidersViewModel extends ViewModelBase {

    private final AppServiceProvidersRepository mRepository;
    private final MutableLiveData<List<AppServiceProvider>> mServiceProvidersData
            = new MutableLiveData<>();

    private final MutableLiveData<StatusMessage> mStatusMessageData
            = new MutableLiveData<>();


    public MutableLiveData<StatusMessage> getStatusMessageData() {
        return mStatusMessageData;
    }

    private final RequestCallback<List<AppServiceProvider>> mRequestCallabck
            = new RequestCallback<List<AppServiceProvider>>() {
        @Override
        public void onSuccess(List<AppServiceProvider> result) {
            mServiceProvidersData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };
    private final RequestCallback<StatusMessage> mRequestCallback
            = new RequestCallback<StatusMessage>() {
        @Override
        public void onSuccess(StatusMessage result) {
            //M.log(result.status);
        }

        @Override
        public void onFailure(String reason) {
            M.log(reason);
        }
    };

    private final RequestCallback<StatusMessage> mDeleteRequestCallback
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


    private HelpAlertRepository mHelpAlertRepository;


    private AppServiceProvidersViewModel(Application application) {
        super(application);
        mRepository = new AppServiceProvidersRepository();
        getAppServiceProviders();
    }

    public MutableLiveData<List<AppServiceProvider>> getServiceProvidersData() {
        return mServiceProvidersData;
    }

    private void getAppServiceProviders() {
        String path = getApplication().getString(R.string.app_service_providers_list_path);
        mRepository.get(path, getHeaders(), getUserIdParamsMap(), mRequestCallabck);
    }

    public void sendHelpAlert() {
        LocationUtil locationUtil = new LocationUtil(this.getApplication());
        android.location.Location location = locationUtil.getFineLocation();
        if (location == null) {
            mError.setValue("Please enable location");
            return;
        }
        String path = getApplication().getString(R.string.help_alert_path);
        String phone = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_PHONE, "");
        if (phone.equals("")) return;
        HashMap<String, String> params = new HashMap<>(4);
        params.put("userId", getUserId());
        params.put("phone", phone);
        params.put("latitude", String.valueOf(location.getLatitude()));
        params.put("longitude", String.valueOf(location.getLongitude()));
        if (mHelpAlertRepository == null) mHelpAlertRepository = new HelpAlertRepository();
        mHelpAlertRepository.sendHelpAlert(path, getHeaders(), params, mRequestCallback);
    }

    public void delete(AppServiceProvider provider){
        String path = getApplication().getString(R.string.app_service_providers_delete_path);
        HashMap<String,String> params = getUserIdParamsMap();
        params.put("deleteId", String.valueOf(provider.deleteId));
        mRepository.delete(path,getHeaders(),params,mDeleteRequestCallback);
    }



    @SuppressWarnings("unchecked")
    public static class AppServiceProvidersViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public AppServiceProvidersViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new AppServiceProvidersViewModel(mApplication);
        }
    }


}