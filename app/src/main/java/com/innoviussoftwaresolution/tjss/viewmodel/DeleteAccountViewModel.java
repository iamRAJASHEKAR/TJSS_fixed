package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.DeleteAccountResponse;
import com.innoviussoftwaresolution.tjss.model.repository.DeleteAccountRepository;

/**
 * @author Sony Raj on 30-10-17.
 */
public class DeleteAccountViewModel extends ViewModelBase {

    private final DeleteAccountRepository mRepository;

    private final MutableLiveData<DeleteAccountResponse> mDeleteData
            = new MutableLiveData<>();

    private final RequestCallback<DeleteAccountResponse> mRequestCallback
            = new RequestCallback<DeleteAccountResponse>() {
        @Override
        public void onSuccess(DeleteAccountResponse result) {
            mDeleteData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private DeleteAccountViewModel(Application application) {
        super(application);
        mRepository = new DeleteAccountRepository();
    }

    public MutableLiveData<DeleteAccountResponse> getDeleteData() {
        return mDeleteData;
    }

    public void delete() {
        String path = getApplication().getString(R.string.delete_user_path);
        mRepository.delete(path, getHeaders(), getUserIdParamsMap(), mRequestCallback);
    }

    @SuppressWarnings("unchecked")
    public static class DeleteAccountViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public DeleteAccountViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new DeleteAccountViewModel(mApplication);
        }
    }


}