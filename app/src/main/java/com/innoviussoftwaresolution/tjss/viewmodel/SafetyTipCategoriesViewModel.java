package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyTipCategory;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;
import com.innoviussoftwaresolution.tjss.model.repository.SafetyTipRepository;

import java.util.List;

/**
 * @author Sony Raj on 16-09-17.
 */

public class SafetyTipCategoriesViewModel extends ViewModelBase {

    private final SafetyTipRepository mRepository;

    private final MutableLiveData<List<SafetyTipCategory>> mSafetyCategoriesData
            = new MutableLiveData<>();

    private final RequestCallback<List<SafetyTipCategory>> mSafetyTipCategoryRequestCallback
            = new RequestCallback<List<SafetyTipCategory>>() {
        @Override
        public void onSuccess(List<SafetyTipCategory> result) {
            mSafetyCategoriesData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };
    private final MutableLiveData<StatusMessage> mUpdateSelectedCategoryData
            = new MutableLiveData<>();
    private final RequestCallback<StatusMessage> mUpdateSelectedCategoriesCallback
            = new RequestCallback<StatusMessage>() {
        @Override
        public void onSuccess(StatusMessage result) {
            mUpdateSelectedCategoryData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private SafetyTipCategoriesViewModel(Application application) {
        super(application);
        mRepository = new SafetyTipRepository();
        getSafetyTipCategories();
    }

    public MutableLiveData<List<SafetyTipCategory>> getSafetyCategoriesData() {
        return mSafetyCategoriesData;
    }

    public MutableLiveData<StatusMessage> getUpdateSelectedCategoryData() {
        return mUpdateSelectedCategoryData;
    }

    private void getSafetyTipCategories() {
        String path = getApplication().getString(R.string.safety_tip_category_listing_path);
        mRepository.getSafetyTipCategories(path, getHeaders(), getUserIdParamsMap(), mSafetyTipCategoryRequestCallback);

    }

    public void updateCategories(String selectedIds) {
        if (selectedIds == null || selectedIds.equals("")) {
            mError.setValue("Please select at-least one category");
            return;
        }

        String path = getApplication().getString(R.string.safety_tip_selected_category_update_path);
        mRepository.updateSelectedCategories(path, getHeaders(), getUserId(),
                selectedIds, mUpdateSelectedCategoriesCallback);
    }


    @SuppressWarnings("unchecked")
    public static class SafetyTipCategoriesViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public SafetyTipCategoriesViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new SafetyTipCategoriesViewModel(mApplication);
        }
    }

}
