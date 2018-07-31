package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.AppServiceProvider;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 23-10-17.
 */
public class ServiceProviderSettingsViewModel extends ServiceProviderViewModelBase {

    private final MutableLiveData<List<AppServiceProvider>> mProvidersData
            = new MutableLiveData<>();

    private final RequestCallback<List<AppServiceProvider>> mProvidersRequestCallback
            = new RequestCallback<List<AppServiceProvider>>() {
        @Override
        public void onSuccess(List<AppServiceProvider> result) {
            mProvidersData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };
    private final MutableLiveData<StatusMessage> mStatusMessageMutableLiveData
            = new MutableLiveData<>();
    private RequestCallback<StatusMessage> mStatusMessageRequestCallback;

    private ServiceProviderSettingsViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<List<AppServiceProvider>> getProvidersData() {
        return mProvidersData;
    }

    public MutableLiveData<StatusMessage> getStatusMessageMutableLiveData() {
        return mStatusMessageMutableLiveData;
    }

    public void getServiceProviders(String subCategoryId) {
        String path = getApplication().getString(R.string.service_providers_for_sub_category_path);
        HashMap<String, String> params = getUserIdParamsMap();
        params.put("subCategoryId", subCategoryId);
        mRepository.getAppServiceProviders(path, getHeaders(), params, mProvidersRequestCallback);
    }


    public void saveData(String categoryId, String subCategoryId, String providers) {
        if (mStatusMessageRequestCallback == null) {
            mStatusMessageRequestCallback = new RequestCallback<StatusMessage>() {
                @Override
                public void onSuccess(StatusMessage result) {
                    mStatusMessageMutableLiveData.setValue(result);
                }

                @Override
                public void onFailure(String reason) {
                    mError.setValue(reason);
                }
            };
        }

        String path = getApplication().getString(R.string.save_selected_service_providers_path);
        HashMap<String, String> params = getUserIdParamsMap();
        params.put("providerId", providers);
        params.put("providerCategoryId", categoryId);
        params.put("providerSubCategoryId", subCategoryId);


        mRepository.saveSelectedServiceProviders(path, getHeaders(), params, mStatusMessageRequestCallback);
    }


    @SuppressWarnings("unchecked")
    public static class ServiceProviderSettingsViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public ServiceProviderSettingsViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ServiceProviderSettingsViewModel(mApplication);
        }
    }


}