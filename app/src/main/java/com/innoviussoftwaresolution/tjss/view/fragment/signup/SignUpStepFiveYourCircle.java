package com.innoviussoftwaresolution.tjss.view.fragment.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaos.view.PinView;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.RequestCircleCodeModule.CircleMembersRequestActivity;
import com.innoviussoftwaresolution.tjss.RequestCircleCodeModule.ContactsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 07-07-17.
 */

public class SignUpStepFiveYourCircle extends SignUpFragmentBase {


    @BindView(R.id.txtSecurityCode)
    PinView mTxtSecurityCode;

    private AppCompatButton btnRequestCode;
    private ArrayList<ContactsModel> contactList;
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
        btnRequestCode=(AppCompatButton)view.findViewById(R.id.btnRequestCode);
        btnRequestCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchContacts();
            }
        });
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

    private void fetchContacts()
    {
        Intent intent=new Intent(getActivity(), CircleMembersRequestActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
