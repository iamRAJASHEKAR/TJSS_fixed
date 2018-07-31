package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.response.ServiceProviderCategory;

import java.util.ArrayList;

/**
 * @author Sony Raj on 21-10-17.
 */
public class ServiceProviderCategoriesViewModel extends ServiceProviderViewModelBase {

    private final MutableLiveData<ArrayList<ServiceProviderCategory>> mCategoriesData
            = new MutableLiveData<>();

    private final RequestCallback<ArrayList<ServiceProviderCategory>> mCategoriesRequestCallback
            = new RequestCallback<ArrayList<ServiceProviderCategory>>() {
        @Override
        public void onSuccess(ArrayList<ServiceProviderCategory> result) {
            mCategoriesData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private ServiceProviderCategoriesViewModel(Application application) {
        super(application);
        getCategories();
    }

    public MutableLiveData<ArrayList<ServiceProviderCategory>> getCategoriesData() {
        return mCategoriesData;
    }

    private void getCategories() {
        String path = getApplication().getString(R.string.service_provider_category_path);
        mRepository.getCategories(path, getHeaders(), getUserIdParamsMap(), mCategoriesRequestCallback);
    }


    @SuppressWarnings("unchecked")
    public static class ServiceProviderCategoriesViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public ServiceProviderCategoriesViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ServiceProviderCategoriesViewModel(mApplication);
        }
    }


}