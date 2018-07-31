package com.innoviussoftwaresolution.tjss.view.fragment.signin;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
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
import com.innoviussoftwaresolution.tjss.broadcastreceiver.EditorBroadCastReceiver;
import com.innoviussoftwaresolution.tjss.model.network.response.IsdCode;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment;
import com.innoviussoftwaresolution.tjss.view.viewutils.AdapterViewItemSelectedAdapter;
import com.innoviussoftwaresolution.tjss.viewmodel.StepOnePhoneViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 15-07-17.
 */

public class FragmentSignInPhone extends BaseFragment {

    @BindView(R.id.cboSignInIsdCodes)
    AppCompatSpinner mCboSignInIsdCodes;

    @BindView(R.id.txtSignInPhone)
    TextInputEditText mTxtSignInPhone;

    Unbinder unbinder;

    private StepOnePhoneViewModel mViewModel;

    private IsdCode mSelectedIsdCode;
    private String mPhone;
    private int mSelectedIndex = 0;
    private Observer<List<IsdCode>> mIsdCodesObserver
            = new Observer<List<IsdCode>>() {
        @Override
        public void onChanged(@Nullable List<IsdCode> isdCodes) {
            if (isdCodes == null) return;
            if (isdCodes.size() <= 0) return;
            ArrayAdapter<IsdCode> mAdapter = new ArrayAdapter<>(getActivity(), R.layout.extra_simple_spinner_item, R.id.lblSpinnerLabel, isdCodes);
            if (mCboSignInIsdCodes != null) {
                mCboSignInIsdCodes.setAdapter(mAdapter);
            }

        }
    };

    @Override
    public void onPause() {
        super.onPause();
        if (mTxtSignInPhone != null)
            if (mTxtSignInPhone.getText() != null)
                mPhone = mTxtSignInPhone.getText().toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mTxtSignInPhone != null && mPhone != null)
            mTxtSignInPhone.setText(mPhone);
        if (mCboSignInIsdCodes != null)
            mCboSignInIsdCodes.setSelection(mSelectedIndex);
    }

    public String getPhone() {
        return mTxtSignInPhone == null ? mPhone :
                (mTxtSignInPhone.getText() == null ? null : mTxtSignInPhone.getText().toString());
    }

    public IsdCode getIsdCode() {
        return mSelectedIsdCode;
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_in_phone, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = ViewModelProviders.of(this, new StepOnePhoneViewModel.StepOnePhoneViewModeFactory(getActivity().getApplication()))
                .get(StepOnePhoneViewModel.class);
        mViewModel.getIsdCodesData().observe(this, mIsdCodesObserver);

        mCboSignInIsdCodes.setOnItemSelectedListener(new AdapterViewItemSelectedAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                super.onItemSelected(parent, view, position, id);
                if (position < 0) return;
                mSelectedIndex = position;
                if (mViewModel.getIsdCodesData().getValue() == null) return;
                if (mViewModel.getIsdCodesData().getValue().size() <= 0) return;
                mSelectedIsdCode = mViewModel.getIsdCodesData().getValue().get(position);
            }
        });

        mTxtSignInPhone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
}
