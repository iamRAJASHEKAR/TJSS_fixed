package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.ServiceProviderSubCategory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Sony Raj on 23-10-17.
 */
public class ServiceProvidersSubCategoriesViewModel extends ServiceProviderViewModelBase {

    private final MutableLiveData<ArrayList<ServiceProviderSubCategory>> mSubCategoriesData
            = new MutableLiveData<>();

    private final RequestCallback<ArrayList<ServiceProviderSubCategory>> mSubcategoriesRequestCallback
            = new RequestCallback<ArrayList<ServiceProviderSubCategory>>() {
        @Override
        public void onSuccess(ArrayList<ServiceProviderSubCategory> result) {
            mSubCategoriesData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private ServiceProvidersSubCategoriesViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<ArrayList<ServiceProviderSubCategory>> getSubCategoriesData() {
        return mSubCategoriesData;
    }

    public void getSubCategories(String categoryId) {
        String path = getApplication().getString(R.string.service_provider_Sub_category_path);
        HashMap<String, String> params = getUserIdParamsMap();
        params.put("categoryId", categoryId);
        mRepository.getSubCategories(path, getHeaders(), params, mSubcategoriesRequestCallback);
    }


    @SuppressWarnings("unchecked")
    public static class ServiceProvidersSubCategoriesViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public ServiceProvidersSubCategoriesViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ServiceProvidersSubCategoriesViewModel(mApplication);
        }
    }


}