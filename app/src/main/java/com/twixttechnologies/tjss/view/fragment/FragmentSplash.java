package com.twixttechnologies.tjss.view.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.UserValidationResponse;
import com.twixttechnologies.tjss.view.activity.HomeActivity;
import com.twixttechnologies.tjss.view.activity.LoginOrSignUpActivity;
import com.twixttechnologies.tjss.viewmodel.SplashViewModel;

/**
 * @author Sony Raj on 16-10-17.
 */

public class FragmentSplash extends BaseFragment {

    public static final String TAG = "Splash";

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

        viewModel.getValidationData().observe(this, new Observer<UserValidationResponse>() {
            @Override
            public void onChanged(@Nullable UserValidationResponse userValidationResponse) {
                if (userValidationResponse == null || userValidationResponse.status != 1) {
                    Intent loginOrSignUp = new Intent(getActivity(), LoginOrSignUpActivity.class);
                    loginOrSignUp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginOrSignUp);
                } else {
                    Intent home = new Intent(getActivity(), HomeActivity.class);
                    home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(home);
                }
                getActivity().finish();
            }
        });

    }
}
