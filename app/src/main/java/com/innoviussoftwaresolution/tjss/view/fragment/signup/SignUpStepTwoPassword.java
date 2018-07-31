package com.innoviussoftwaresolution.tjss.view.fragment.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.broadcastreceiver.EditorBroadCastReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 07-07-17.
 */

public class SignUpStepTwoPassword extends SignUpFragmentBase {

    @BindView(R.id.txtRegistrationPassword)
    TextInputLayout mTxtRegistrationPassword;

    @BindView(R.id.txtRegistrationConformPassword)
    TextInputLayout txtConfromPassword;

    Unbinder unbinder;

    private String mPassword = "", mConfrimPassword = "";

    public String getPasswordFromFragment() {
        if ((mPassword == null || mPassword.equals(""))) {
            if (mTxtRegistrationPassword.getEditText() == null) {
                return null;
            }
            if (mTxtRegistrationPassword.getEditText().getText() == null) {
                return null;
            }
            return mTxtRegistrationPassword.getEditText().getText().toString();
        } else {
                return mPassword;
        }

    }
    public String getCPasswordFromFragment() {
        if ((mConfrimPassword == null || mConfrimPassword.equals(""))) {
            if (txtConfromPassword.getEditText() == null) {

                return null;
            }
            if (txtConfromPassword.getEditText().getText() == null) {
                return null;
            }

            return txtConfromPassword.getEditText().getText().toString();
        } else {
                return mConfrimPassword;
        }
    }


    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_step_two_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mTxtRegistrationPassword.getEditText() != null)
            mTxtRegistrationPassword.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    Intent intent = new Intent(EditorBroadCastReceiver.EDITOR_ACTION);
                    LocalBroadcastManager.getInstance(getActivity())
                            .sendBroadcast(intent);
                    return true;
                }
            });
    }

    @Override
    void setDataOnPause() {
        //noinspection ConstantConditions
        if (mTxtRegistrationPassword.getEditText().getText() != null)  {
            mPassword = mTxtRegistrationPassword.getEditText().getText().toString();
        }
        if(txtConfromPassword.getEditText().getText()!=null)
        {
            mConfrimPassword=txtConfromPassword.getEditText().getText().toString();
        }
    }

    @Override
    void setDataOnResume() {
        if (mPassword != null)
            if (mTxtRegistrationPassword.getEditText() != null)
                mTxtRegistrationPassword.getEditText().setText(mPassword);
        if(txtConfromPassword.getEditText().getText()!=null)
        {
            mConfrimPassword=txtConfromPassword.getEditText().getText().toString();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
