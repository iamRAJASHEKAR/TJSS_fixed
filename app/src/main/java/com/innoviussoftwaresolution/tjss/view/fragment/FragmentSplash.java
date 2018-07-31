package com.innoviussoftwaresolution.tjss.view.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.Activities.ServiceProviderHome;
import com.innoviussoftwaresolution.tjss.model.network.response.UserValidationResponse;
import com.innoviussoftwaresolution.tjss.utils.ConnectivityDetector;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;
import com.innoviussoftwaresolution.tjss.view.activity.HomeActivity;
import com.innoviussoftwaresolution.tjss.view.activity.LoginOrSignUpActivity;
import com.innoviussoftwaresolution.tjss.viewmodel.SplashViewModel;

/**
 * @author Sony Raj on 16-10-17.
 */

public class FragmentSplash extends BaseFragment {

    public static final String TAG = "Splash";
    private String role;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SplashViewModel viewModel = ViewModelProviders
                .of(this, new SplashViewModel.SplashViewModelFactory(getActivity().getApplication()))
                .get(SplashViewModel.class);

        role= PreferenceHelper.getString(getContext(),PreferenceHelper.USER_ROLE,"");

        if (ConnectivityDetector.IS_INTERNET_AVAILABLE(getActivity())) {
            viewModel.getValidationData().observe(this, new Observer<UserValidationResponse>() {
                @Override
                public void onChanged(@Nullable UserValidationResponse userValidationResponse) {
                    if (userValidationResponse == null || userValidationResponse.status != 1) {
                        Intent loginOrSignUp = new Intent(getActivity(), LoginOrSignUpActivity.class);
                        loginOrSignUp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginOrSignUp);
                        getActivity().finish();
                    } else {
                        if(role.equalsIgnoreCase("service provider"))
                        {
                            Intent providerHome = new Intent(getActivity(), ServiceProviderHome.class);
                            providerHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(providerHome);
                            getActivity().finish();

                        }
                        else {
                            Intent home = new Intent(getActivity(), HomeActivity.class);
                            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(home);
                            getActivity().finish();
                        }
                    }

                }
            });
        } else {
            M.showToast(getActivity(), "Please check Internet connection");
        }

    }
}
