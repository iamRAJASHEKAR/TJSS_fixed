package com.twixttechnologies.tjss.view.fragment.user.settings.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.activity.ChangePasswordActivity;
import com.twixttechnologies.tjss.view.activity.DeleteAccountActivity;
import com.twixttechnologies.tjss.view.activity.LocationUpdatesActivity;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 25-07-17.
 */

public class FragmentAccountSettings extends BaseFragment {

    Unbinder unbinder;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnAccountSettingsChangePassword)
    public void onMBtnAccountSettingsChangePasswordClicked() {
        startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
    }

    @OnClick(R.id.btnAccountSettingsDeleteAccount)
    public void onMBtnAccountSettingsDeleteAccountClicked() {
        startActivity(new Intent(getActivity(), DeleteAccountActivity.class));
    }

    @OnClick(R.id.btnAccountSettingsLocationUpdates)
    public void onMBtnAccountSettingsLocationUpdatesClicked() {
        startActivity(new Intent(getActivity(), LocationUpdatesActivity.class));
    }
}
