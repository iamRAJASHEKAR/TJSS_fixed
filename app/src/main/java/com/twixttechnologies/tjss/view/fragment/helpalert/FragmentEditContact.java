package com.twixttechnologies.tjss.view.fragment.helpalert;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.IsdCode;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleContact;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.activity.HelpAlertContactEditActivity;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.view.viewutils.AdapterViewItemSelectedAdapter;
import com.twixttechnologies.tjss.viewmodel.EditHelpAlertViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 13/11/17.
 */

public class FragmentEditContact extends BaseFragment {

    public static final String TAG = "editHelpAlert";
    @BindView(R.id.txtAddContactName)
    TextInputEditText mTxtAddContactName;
    @BindView(R.id.cboAddContactIsdCode)
    AppCompatSpinner mCboAddContactIsdCode;
    @BindView(R.id.txtAddContactPhone)
    TextInputEditText mTxtAddContactPhone;
    @BindView(R.id.btnNext)
    AppCompatButton mBtnNext;
    Unbinder unbinder;

    private SafetyCircleContact mHelpAlertContact;

    private Observer<StatusMessage> mStatusMessageObserver;

    private EditHelpAlertViewModel mViewModel;

    private IsdCode mSelectedIsdCode;

    private final Observer<List<IsdCode>> mIsdCodesObserver
            = new Observer<List<IsdCode>>() {
        @Override
        public void onChanged(@Nullable List<IsdCode> isdCodes) {
            if (isdCodes == null || isdCodes.size() <= 0) return;
            ArrayAdapter<IsdCode> mAdapter = new ArrayAdapter<>(getActivity(), R.layout.extra_simple_spinner_item, R.id.lblSpinnerLabel, isdCodes);
            if (mCboAddContactIsdCode != null) {
                mCboAddContactIsdCode.setAdapter(mAdapter);
            }
            for (int i = 0; i < isdCodes.size(); i++) {
                IsdCode isdCode = isdCodes.get(i);
                if (isdCode == null) continue;
                if (isdCode.isdCode == null) continue;

                String tempPhone = mHelpAlertContact.phone.replace("+", "");
                String tempCode = isdCode.isdCode.replace("+", "");
                if (tempPhone.substring(0, tempCode.length()).equals(tempCode)) {
                    mSelectedIsdCode = isdCode;
                    mCboAddContactIsdCode.setSelection(i);
                    if (!TextUtils.isEmpty(mTxtAddContactPhone.getText())) {
                        String phone = tempPhone.substring(tempCode.length()).trim();
                        mTxtAddContactPhone.setText(phone);
                    }
                    break;
                }
            }
        }
    };


    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        mHelpAlertContact = getArguments().getParcelable(HelpAlertContactEditActivity.HELP_ALERT_CONTACT);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnNext.setText("Update");

        if (mHelpAlertContact != null) {
            mTxtAddContactName.setText(mHelpAlertContact.name);
            mTxtAddContactPhone.setText(mHelpAlertContact.phone);
        }

        mViewModel = ViewModelProviders.of(this,
                new EditHelpAlertViewModel.EditHelpAlertViewModelFactory(getActivity().getApplication()))
                .get(EditHelpAlertViewModel.class);

        mViewModel.getIsdCodesData().observe(this, mIsdCodesObserver);
        mCboAddContactIsdCode.setOnItemSelectedListener(new AdapterViewItemSelectedAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                super.onItemSelected(parent, view, position, id);
                if (position < 0) return;
                if (mViewModel.getIsdCodesData().getValue() == null) return;
                if (mViewModel.getIsdCodesData().getValue().size() <= 0) return;
                mSelectedIsdCode = mViewModel.getIsdCodesData().getValue().get(position);
            }
        });


    }

    public static FragmentEditContact newInstance(SafetyCircleContact contact) {
        Bundle args = new Bundle();
        args.putParcelable(HelpAlertContactEditActivity.HELP_ALERT_CONTACT, contact);
        FragmentEditContact fragment = new FragmentEditContact();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnNext)
    public void onViewClicked() {
        if (mHelpAlertContact == null) {
            M.showAlert(getActivity(), "Edit Contact", "An error occurred please try again later", "OK",
                    null, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getActivity().finish();
                        }
                    }, null, false);
            return;
        }

        if (TextUtils.isEmpty(mTxtAddContactName.getText())) {
            mTxtAddContactName.requestFocus();
            M.showAlert(getActivity(), "Edit Contact", "Please enter a name", "OK",
                    null, null, null, false);
            return;
        }
        if (TextUtils.isEmpty(mTxtAddContactPhone.getText())) {
            mTxtAddContactName.requestFocus();
            M.showAlert(getActivity(), "Edit Contact", "Please enter phone number", "OK",
                    null, null, null, false);
            return;
        }

        if (mSelectedIsdCode == null) {
            M.showAlert(getActivity(), "Edit Contact", "Please select Isd Code", "OK",
                    null, null, null, false);
            mCboAddContactIsdCode.requestFocus();
            return;
        }

        if (mStatusMessageObserver == null) {
            mStatusMessageObserver = new Observer<StatusMessage>() {
                @Override
                public void onChanged(@Nullable StatusMessage statusMessage) {
                    dismissDialog();
                    if (statusMessage == null || statusMessage.status == null || statusMessage.status.equals("")) {
                        M.showAlert(getActivity(), "Edit Contact", "An error occurred please try again later", "OK",
                                null, null, null, false);
                    } else if (statusMessage.status.toLowerCase().contains("success")) {
                        M.showAlert(getActivity(), "Edit Contact",
                                "Updated Successfully.", "OK",
                                null,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        getActivity().finish();
                                    }
                                },
                                null, false);
                    }
                }
            };

            mViewModel.getStatusMessageData().observe(this, mStatusMessageObserver);
        }


        String name = mTxtAddContactName.getText().toString();
        String phone = mTxtAddContactPhone.getText().toString();

        if (mSelectedIsdCode.isdCode.contains("91")) {
            if (phone.length() != 10) {
                M.showToast(getContext(), "Please enter a valid phone number");
                return;
            }
        } else {
            if (phone.length() != 11) {
                M.showToast(getContext(), "Please enter a valid phone number");
                return;
            }
        }
        initProgress();
        initErrorObserver();
        mHelpAlertContact.name = name;
        mHelpAlertContact.phone = mSelectedIsdCode.isdCode + " " + phone;
        mViewModel.edit(mHelpAlertContact);
    }
}
