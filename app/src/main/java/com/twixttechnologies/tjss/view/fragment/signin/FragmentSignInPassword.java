package com.twixttechnologies.tjss.view.fragment.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatButton;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.broadcastreceiver.EditorBroadCastReceiver;
import com.twixttechnologies.tjss.model.network.request.ForgotPasswordRequest;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.utils.PreferenceHelper;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.view.fragment.signin.alert.FragmentForgotPasswordDialog;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 15-07-17.
 */

public class FragmentSignInPassword extends BaseFragment implements FragmentForgotPasswordDialog.OnSubmitListener {

    @BindView(R.id.btnSignForgotPassword)
    AppCompatButton mBtnSignForgotPassword;
    Unbinder unbinder;
    @BindView(R.id.txtLoginPassword)
    TextInputEditText mTxtLoginPassword;

    private String mPassword;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mTxtLoginPassword != null)
            if (mTxtLoginPassword.getText() != null)
                mPassword = mTxtLoginPassword.getText().toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mTxtLoginPassword != null && mPassword != null)
            mTxtLoginPassword.setText(mPassword);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_in_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTxtLoginPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Intent intent = new Intent(EditorBroadCastReceiver.EDITOR_ACTION);
                LocalBroadcastManager.getInstance(getActivity())
                        .sendBroadcast(intent);
                return true;
            }
        });
    }

    public String getPassword() {
        return mTxtLoginPassword == null ? mPassword :
                (mTxtLoginPassword.getText() == null ? null : mTxtLoginPassword.getText().toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSignForgotPassword)
    public void onViewClicked() {
        FragmentForgotPasswordDialog dialog = new FragmentForgotPasswordDialog();
        dialog.setCancelable(true);
        dialog.setOnSubmitListener(this);
        dialog.show(getChildFragmentManager(), "forgotPassword");

    }

    @Override
    public void onSubmit(String email) {
        String path = getString(R.string.forgot_password_path);
        HashMap<String, String> params = new HashMap<>(1);
        params.put("email", email);


        HashMap<String, String> headers = new HashMap<>(2);
        headers.put("userid", PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_USER_ID, ""));
        headers.put("api_token", PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_ACCESS_TOKEN, ""));

        new ForgotPasswordRequest(TjssNetworkInterface.class, new RequestCallback<StatusMessage>() {
            @Override
            public void onSuccess(StatusMessage result) {
                if (result == null || result.status == null || result.status.equals("")) {
                    M.showAlert(getActivity(), "Forgot Password", "An error occurred, Please try again later",
                            "OK", null, null, null, false);
                } else
                    M.showAlert(getActivity(), "Forgot Password", result.status, "OK",
                            null, null, null, false);
            }

            @Override
            public void onFailure(String reason) {
                M.showAlert(getActivity(), "Forgot Password", "An error occurred, Please try again later",
                        "OK", null, null, null, false);
            }
        }).send(path, headers, params);
    }
}
