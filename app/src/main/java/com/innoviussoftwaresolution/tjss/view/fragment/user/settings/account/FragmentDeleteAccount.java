package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.account;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.TjssApplication;
import com.innoviussoftwaresolution.tjss.model.network.response.DeleteAccountResponse;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;
import com.innoviussoftwaresolution.tjss.view.activity.LoginOrSignUpActivity;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment;
import com.innoviussoftwaresolution.tjss.viewmodel.DeleteAccountViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 25-07-17.
 */

public class FragmentDeleteAccount extends BaseFragment {

    @BindView(R.id.btnDeleteAccount)
    AppCompatButton mBtnDeleteAccount;
    Unbinder unbinder;
    DeleteAccountViewModel viewModel;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this,
                new DeleteAccountViewModel.DeleteAccountViewModelFactory(getActivity().getApplication()))
                .get(DeleteAccountViewModel.class);


        viewModel.getDeleteData().observe(this, new Observer<DeleteAccountResponse>() {
            @Override
            public void onChanged(@Nullable DeleteAccountResponse deleteAccountResponse) {
                dismissDialog();
                if (deleteAccountResponse == null) {
                    M.showAlert(getActivity(), "Delete Account", "An error occurred, please try again later", "OK",
                            null, null, null, false);
                    return;
                }

                if (deleteAccountResponse.userId == null || deleteAccountResponse.userId.equals("")) {
                    M.showAlert(getActivity(), "Delete Account", deleteAccountResponse.status, "OK",
                            null, null, null, false);
                    return;
                }
                PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_FIRST_NAME, "");
                PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_EMAIL, "");
                PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_PRIMARY_CIRCLE, "");
                PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_PROFILE_IMAGE, "");
                PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
                PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_REFRESH_TOKEN, "");
                PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_PHONE, "");
                PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_USER_ID, "");
                PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_SUBSCRIPTION_PLAN_ID, "");
                PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_MAP_OPTIONS, "");
                PreferenceHelper.saveBoolean(getActivity().getApplication(), PreferenceHelper.KEY_USER_LOGGED_IN, false);
                ((TjssApplication) getActivity().getApplication()).stopLocationUpdates();
                ((TjssApplication) getActivity().getApplication()).stopBatteryMonitor();
                Intent signInOrSignUp = new Intent(getActivity(), LoginOrSignUpActivity.class);
                signInOrSignUp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signInOrSignUp);
                getActivity().finishAffinity();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnDeleteAccount)
    public void onViewClicked() {
        initProgress();
        initErrorObserver();
        viewModel.delete();
    }
}
