package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.response.SafetyTip;
import com.twixttechnologies.tjss.model.network.response.SafetyTipCategory;
import com.twixttechnologies.tjss.model.repository.SafetyTipRepository;

import java.util.List;

/**
 * @author Sony Raj on 16-09-17.
 */

public class SafetyTipViewModel extends ViewModelBase {

    private final SafetyTipRepository mRepository;

    //region Live data

    private final MutableLiveData<List<SafetyTip>> mSafetyTipSData
            = new MutableLiveData<>();
    private final MutableLiveData<List<SafetyTipCategory>> mSelectedCategoriesData
            = new MutableLiveData<>();

    //endregion

    //region Request callback
    private final RequestCallback<List<SafetyTip>> mSafetyTipRequestCallback
            = new RequestCallback<List<SafetyTip>>() {
        @Override
        public void onSuccess(List<SafetyTip> result) {
            mSafetyTipSData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private final RequestCallback<List<SafetyTipCategory>> mSelectedCategoriesCallback
            = new RequestCallback<List<SafetyTipCategory>>() {
        @Override
        public void onSuccess(List<SafetyTipCategory> result) {
            mSelectedCategoriesData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    //endregion

    // region Constructor

    private SafetyTipViewModel(Application application) {
        super(application);
        mRepository = new SafetyTipRepository();
        getSelectedCategories();
    }

    //endregion

    //region Live data getters

    public MutableLiveData<List<SafetyTip>> getSafetyTipSData() {
        return mSafetyTipSData;
    }

    public MutableLiveData<List<SafetyTipCategory>> getSelectedCategoriesData() {
        return mSelectedCategoriesData;
    }

    //endregion

    //region Repository calls

    public void getSafetyTips(String categoryId) {
        String path = getApplication().getString(R.string.safety_tip_listing_path);
        mRepository.getSafetyTips(path, categoryId, getHeaders(), mSafetyTipRequestCallback);
    }

    private void getSelectedCategories() {
        String path = getApplication().getString(R.string.safety_tip_category_listing_path);
        mRepository.getSelectedSafetyTipCategory(path, getHeaders(), getUserIdParamsMap(), mSelectedCategoriesCallback);
    }

    //endregion

    @SuppressWarnings("unchecked")
    public static class SafetyTipViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public SafetyTipViewModelFactory(Application application) {
            this.mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new SafetyTipViewModel(mApplication);
        }
    }

}
