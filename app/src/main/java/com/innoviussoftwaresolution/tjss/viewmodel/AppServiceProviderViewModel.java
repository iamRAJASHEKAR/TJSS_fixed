package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.AppServiceProvider;
import com.innoviussoftwaresolution.tjss.model.repository.AppServiceProvidersRepository;

import java.util.List;

/**
 * @author Sony Raj on 20/11/17.
 */
public class AppServiceProviderViewModel extends ViewModelBase {

    private final AppServiceProvidersRepository mRepository;

    private final MutableLiveData<List<AppServiceProvider>> mAppServiceProvidersData
            = new MutableLiveData<>();

    private final RequestCallback<List<AppServiceProvider>> mRequestCallback
            = new RequestCallback<List<AppServiceProvider>>() {
        @Override
        public void onSuccess(List<AppServiceProvider> result) {
            mAppServiceProvidersData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    public MutableLiveData<List<AppServiceProvider>> getAppServiceProvidersData() {
        return mAppServiceProvidersData;
    }

    private AppServiceProviderViewModel(Application application) {
        super(application);
        mRepository = new AppServiceProvidersRepository();
        getAppServiceProviders();
    }


    private void getAppServiceProviders() {
        String path = getApplication().getString(R.string.app_service_providers_list_path);
        mRepository.get(path, getHeaders(), getUserIdParamsMap(), mRequestCallback);
    }


    @SuppressWarnings("unchecked")
    public static class AppServiceProviderViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public AppServiceProviderViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new AppServiceProviderViewModel(mApplication);
        }
    }
}