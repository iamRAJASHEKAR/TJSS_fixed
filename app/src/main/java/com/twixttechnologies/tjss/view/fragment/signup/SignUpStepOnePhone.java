package com.twixttechnologies.tjss.view.fragment.signup;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatSpinner;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.broadcastreceiver.EditorBroadCastReceiver;
import com.twixttechnologies.tjss.model.network.response.IsdCode;
import com.twixttechnologies.tjss.viewmodel.StepOnePhoneViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 07-07-17.
 */

public class SignUpStepOnePhone extends SignUpFragmentBase implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.cboRegistrationIsdCodes)
    AppCompatSpinner mCboRegistrationIsdCodes;
    @BindView(R.id.txtRegistrationPhone)
    TextInputLayout mTxtRegistrationPhone;
    Unbinder unbinder;
    private StepOnePhoneViewModel mViewModel;
    private ArrayAdapter<IsdCode> mIsdCodesAdapter;

    private IsdCode mSelectedIsdCode;
    private Observer<List<IsdCode>> mIsdCodesObserver
            = new Observer<List<IsdCode>>() {
        @Override
        public void onChanged(@Nullable List<IsdCode> isdCodes) {
            if (isdCodes == null) return;
            mIsdCodesAdapter = new ArrayAdapter<>(getActivity(), R.layout.extra_simple_spinner_item, R.id.lblSpinnerLabel, isdCodes);
            if (mCboRegistrationIsdCodes != null) {
                mCboRegistrationIsdCodes.setAdapter(mIsdCodesAdapter);
                mCboRegistrationIsdCodes.setOnItemSelectedListener(SignUpStepOnePhone.this);
            }
        }
    };

    public String getIsdCode() {
        return mSelectedIsdCode == null ? "" : mSelectedIsdCode.isdCode;
    }

    public String getPhoneNumber() {
        if (mViewModel.getPhoneNumber() == null || mViewModel.getPhoneNumber().equals("")) {
            if (mTxtRegistrationPhone.getEditText() == null) return null;
            if (mTxtRegistrationPhone.getEditText().getText() == null) return null;
            return mTxtRegistrationPhone.getEditText().getText().toString();
        } else {
            return mViewModel.getPhoneNumber();
        }
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_step_one_phone, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = ViewModelProviders
                .of(this, new StepOnePhoneViewModel.StepOnePhoneViewModeFactory(getActivity().getApplication()))
                .get(StepOnePhoneViewModel.class);

        mViewModel.getIsdCodesData().observe(this, mIsdCodesObserver);

        if (mTxtRegistrationPhone.getEditText() != null)
            mTxtRegistrationPhone.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position < 0) return;
        mSelectedIsdCode = (IsdCode) parent.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    void setDataOnPause() {
        if (mSelectedIsdCode != null)
            mViewModel.setSelectedIsdCode(mSelectedIsdCode);

        //noinspection ConstantConditions
        if (mTxtRegistrationPhone.getEditText().getText() != null)
            mViewModel.setPhoneNumber(mTxtRegistrationPhone.getEditText().getText().toString());

    }

    @Override
    void setDataOnResume() {
        List<IsdCode> isdCodes = mViewModel.getIsdCodesData().getValue();
        if (isdCodes != null) {
            if (mIsdCodesAdapter == null) {
                mIsdCodesAdapter = new ArrayAdapter<>(getActivity(), R.layout.extra_simple_spinner_item, R.id.lblSpinnerLabel, isdCodes);
            }
            if (mCboRegistrationIsdCodes != null) {
                mCboRegistrationIsdCodes.setAdapter(mIsdCodesAdapter);
                mCboRegistrationIsdCodes.setOnItemSelectedListener(SignUpStepOnePhone.this);
            }

            mSelectedIsdCode = mViewModel.getSelectedIsdCode();
            if (mSelectedIsdCode != null) {
                for (int i = 0; i < isdCodes.size(); i++) {
                    if (isdCodes.get(i).isdCode.equals(mSelectedIsdCode.isdCode)) {
                        mCboRegistrationIsdCodes.setSelection(i);
                        break;
                    }
                }
            }
        }

        String phone = mViewModel.getPhoneNumber();
        if (phone != null)
            //noinspection ConstantConditions
            mTxtRegistrationPhone.getEditText().setText(phone);

    }
}
