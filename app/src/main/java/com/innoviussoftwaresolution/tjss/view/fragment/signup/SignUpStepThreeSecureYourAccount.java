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

public class SignUpStepThreeSecureYourAccount extends SignUpFragmentBase {


    @BindView(R.id.txtRegistrationEmail)
    TextInputLayout mTxtRegistrationEmail;
    Unbinder unbinder;

    private String mEmail;

    public String getEmail() {

        if (mEmail == null || mEmail.equals("")) {
            if (mTxtRegistrationEmail.getEditText() == null) return null;
            if (mTxtRegistrationEmail.getEditText().getText() == null) return null;
            return mTxtRegistrationEmail.getEditText().getText().toString();
        } else {
            return mEmail;
        }
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_step_three_secure_your_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mTxtRegistrationEmail.getEditText() != null)
            mTxtRegistrationEmail.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        if (mTxtRegistrationEmail.getEditText() != null)
            if (mTxtRegistrationEmail.getEditText().getText() != null)
                mEmail = mTxtRegistrationEmail.getEditText().getText().toString();
    }

    @Override
    void setDataOnResume() {
        if (mEmail != null)
            if (mTxtRegistrationEmail.getEditText() != null)
                mTxtRegistrationEmail.getEditText().setText(mEmail);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
