package com.twixttechnologies.tjss.view.fragment.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaos.view.PinView;
import com.innoviussoftwaresolution.tjss.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 07-07-17.
 */

public class SignUpStepFiveYourCircle extends SignUpFragmentBase {


    @BindView(R.id.txtSecurityCode)
    PinView mTxtSecurityCode;
    Unbinder unbinder;
    private String mSecurityCode;

    public String getSecurityCode() {
        return mTxtSecurityCode != null ? mTxtSecurityCode.getText().toString() : mSecurityCode;
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_step_five_your_circle, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    void setDataOnPause() {
        //if (mTxtSecurityCode != null)
        //mSecurityCode = mTxtSecurityCode.getSecurityCode();
    }

    @Override
    void setDataOnResume() {
        //if (mSecurityCode != null)
        //if (mTxtSecurityCode != null)
        //mTxtSecurityCode.setSecurityCode(mSecurityCode);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
