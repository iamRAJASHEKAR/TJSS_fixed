package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.UserValidationResponse;
import com.innoviussoftwaresolution.tjss.model.repository.TjssRepository;

import java.util.HashMap;

/**
 * @author Sony Raj on 16-10-17.
 */
public class SplashViewModel extends ViewModelBase {

    private final TjssRepository mRepository;

    private final MutableLiveData<UserValidationResponse> mValidationData
            = new MutableLiveData<>();

    private final RequestCallback<UserValidationResponse> mValidationCallback
            = new RequestCallback<UserValidationResponse>() {
        @Override
        public void onSuccess(UserValidationResponse result) {
            mValidationData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private SplashViewModel(Application application) {
        super(application);
        mRepository = new TjssRepository();
        String path = getApplication().getString(R.string.user_validation_response_path);
        HashMap<String, String> params = new HashMap<>(2);
        params.put("userId", getUserId());
        params.put("token", getToken());
        mRepository.validateUser(path, getHeaders(), params, mValidationCallback);
    }

    public MutableLiveData<UserValidationResponse> getValidationData() {
        return mValidationData;
    }

    @SuppressWarnings("unchecked")
    public static class SplashViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public SplashViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new SplashViewModel(mApplication);
        }
    }


}