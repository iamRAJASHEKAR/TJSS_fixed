package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.view.fragment.user.settings.SettingsFragmentBase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 25-07-17.
 */

public class FragmentChangePassword extends SettingsFragmentBase {


    @BindView(R.id.txtOldPassword)
    TextInputEditText mTxtOldPassword;
    @BindView(R.id.txtNewPassword)
    TextInputEditText mTxtNewPassword;
    @BindView(R.id.btnSave)
    AppCompatButton mBtnSave;
    Unbinder unbinder;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        if (TextUtils.isEmpty(mTxtOldPassword.getText())) {
            M.showToast(getActivity(), "Old Password Required");
            return;
        }

        if (TextUtils.isEmpty(mTxtNewPassword.getText())) {
            M.showToast(getActivity(), "Please enter new password");
            return;
        }

        mViewModel.changePassword(mTxtOldPassword.getText().toString(),
                mTxtNewPassword.getText().toString());

        mBtnSave.setEnabled(false);

    }

    @Override
    public void onStatusChanged() {
        mBtnSave.setEnabled(true);
    }
}
