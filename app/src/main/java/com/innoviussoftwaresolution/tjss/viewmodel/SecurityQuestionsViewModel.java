package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.SecurityQuestion;
import com.innoviussoftwaresolution.tjss.model.network.response.SelectedSecurityQuestionResponse;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;
import com.innoviussoftwaresolution.tjss.model.repository.SecurityQuestionsRepository;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 31-10-17.
 */
public class SecurityQuestionsViewModel extends ViewModelBase {

    private final SecurityQuestionsRepository mRepository;

    private final MutableLiveData<List<SecurityQuestion>> mSecurityQuestionsData
            = new MutableLiveData<>();

    private final MutableLiveData<StatusMessage> mStatusMessageData
            = new MutableLiveData<>();

    private final MutableLiveData<SelectedSecurityQuestionResponse> mSelectedSecurityQuestionData
            = new MutableLiveData<>();
    private RequestCallback<StatusMessage> mUpdateStatusCallback;
    private RequestCallback<SelectedSecurityQuestionResponse> mSelectedSecurityQuestionsCallback
            = new RequestCallback<SelectedSecurityQuestionResponse>() {
        @Override
        public void onSuccess(SelectedSecurityQuestionResponse result) {
            mSelectedSecurityQuestionData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {

        }
    };
    private final RequestCallback<List<SecurityQuestion>> mSecurityQuestionsRequestCallback
            = new RequestCallback<List<SecurityQuestion>>() {
        @Override
        public void onSuccess(List<SecurityQuestion> result) {
            mSecurityQuestionsData.setValue(result);
            getSelected();
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };


    private SecurityQuestionsViewModel(Application application) {
        super(application);
        mRepository = new SecurityQuestionsRepository();
        getSecurityQuestions();
    }

    public MutableLiveData<List<SecurityQuestion>> getSecurityQuestionsData() {
        return mSecurityQuestionsData;
    }

    public MutableLiveData<StatusMessage> getStatusMessageData() {
        return mStatusMessageData;
    }

    public MutableLiveData<SelectedSecurityQuestionResponse> getSelectedSecurityQuestionData() {
        return mSelectedSecurityQuestionData;
    }

    private void getSecurityQuestions() {
        String path = getApplication().getString(R.string.security_questions_listing_path);
        mRepository.listSecurityQuestions(path, mSecurityQuestionsRequestCallback);
    }

    public void update(SecurityQuestion securityQuestion, String answer, String pin) {
        String path = getApplication().getString(R.string.update_security_questions_path);
        HashMap<String, String> params = getUserIdParamsMap();
        params.put("questionId", String.valueOf(securityQuestion.id));
        params.put("securityAnswer", answer);
        params.put("securityPin", pin);

        if (mUpdateStatusCallback == null) {
            mUpdateStatusCallback = new RequestCallback<StatusMessage>() {
                @Override
                public void onSuccess(StatusMessage result) {
                    mStatusMessageData.setValue(result);
                }

                @Override
                public void onFailure(String reason) {
                    mError.setValue(reason);
                }
            };
        }
        mRepository.update(path, getHeaders(), params, mUpdateStatusCallback);
    }

    private void getSelected() {
        String path = getApplication().getString(R.string.selected_security_questions_path);
        mRepository.getSelected(path, getHeaders(), getUserIdParamsMap(), mSelectedSecurityQuestionsCallback);
    }


    @SuppressWarnings("unchecked")
    public static class SecurityQuestionsViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public SecurityQuestionsViewModelFactory(Application application) {
            mApplication = application;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new SecurityQuestionsViewModel(mApplication);
        }
    }


}