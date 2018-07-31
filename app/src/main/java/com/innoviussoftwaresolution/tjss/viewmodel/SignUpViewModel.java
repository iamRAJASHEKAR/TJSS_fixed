package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.InviteCodeVerifyResponse;
import com.innoviussoftwaresolution.tjss.model.repository.SignUpRepository;

/**
 * @author Sony Raj on 05-08-17.
 */

public class SignUpViewModel extends LoginSignUpViewModelBase implements RequestCallback<InviteCodeVerifyResponse> {

    private final MutableLiveData<InviteCodeVerifyResponse> mInviteCodeVerifyLiveData = new MutableLiveData<>();

    private SignUpRepository mSignUpRepository;

    private SignUpViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<InviteCodeVerifyResponse> getInviteCodeVerifyLiveData() {
        return mInviteCodeVerifyLiveData;
    }

    public void verify(String inviteCode) {
        if (mSignUpRepository == null)
            mSignUpRepository = new SignUpRepository();

        mSignUpRepository.verify(getApplication().getString(R.string.verify_circle_code), inviteCode, this);

    }

    public void signUp(String firstName, String email, String phone, String isdCode, String password,
                       String userList, String circleId, String profileImage, String deviceId) {
        if (mSignUpRepository == null) mSignUpRepository = new SignUpRepository();
        mSignUpRepository.signUp(getApplication().getString(R.string.join_using_circle_invitation_code),
                firstName, email, phone, isdCode, password, userList, circleId, profileImage, deviceId,
                mSignUpLoginRequestCallback);
    }

    public void signUpByPayment(String firstName, String email, String phone, String isdCode, String password,
                                String userList, String circleId, String profileImage, String deviceId) {
        if (mSignUpRepository == null) mSignUpRepository = new SignUpRepository();
        mSignUpRepository.signUp(getApplication().getString(R.string.sign_up_by_payment_path),
                firstName, email, phone, isdCode, password, userList, circleId, profileImage, deviceId,
                mSignUpLoginRequestCallback);
    }



    @Override
    public void onSuccess(InviteCodeVerifyResponse result) {
        mInviteCodeVerifyLiveData.setValue(result);
    }

    @Override
    public void onFailure(String reason) {
        mError.setValue(reason);
    }


    @SuppressWarnings("unchecked")
    public static class SignUpViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public SignUpViewModelFactory(Application mApplication) {
            this.mApplication = mApplication;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new SignUpViewModel(mApplication);
        }
    }

}
