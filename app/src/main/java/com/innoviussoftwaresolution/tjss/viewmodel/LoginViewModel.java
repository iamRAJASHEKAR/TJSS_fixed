package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.repository.LoginRepository;

/**
 * @author Sony Raj on 08-08-17.
 */

public class LoginViewModel extends LoginSignUpViewModelBase {

    private LoginViewModel(Application application) {
        super(application);
    }
/*
    public void login(String userPhone, String password){
        Log.e("Log in :",userPhone+password);
        LoginRepository loginRepository = new LoginRepository();
        loginRepository.login(getApplication().getString(R.string.login_path),userPhone,password,
                mSignUpLoginRequestCallback);
    }*/
    public void newLogin(String userPhone, String password, String firebaseId) {
        LoginRepository loginRepository = new LoginRepository();
        loginRepository.login(getApplication().getString(R.string.login_path), userPhone, password,firebaseId, mSignUpLoginRequestCallback);
    }
    @SuppressWarnings("unchecked")
    public static class LoginViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public LoginViewModelFactory(Application application) {
            this.mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new LoginViewModel(mApplication);
        }
    }

}
