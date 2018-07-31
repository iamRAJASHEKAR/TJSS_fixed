package com.twixttechnologies.tjss.view.fragment.helpalert;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.innoviussoftwaresolution.tjss.R;
//import com.twixttechnologies.tjss.R;
import com.twixttechnologies.tjss.model.internal.InternalContact;
import com.twixttechnologies.tjss.model.network.response.IsdCode;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.view.viewutils.AdapterViewItemSelectedAdapter;
import com.twixttechnologies.tjss.viewmodel.ContactsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 20-07-17.
 */

public class FragmentAddContact extends BaseFragment {

    @BindView(R.id.txtAddContactName)
    TextInputEditText mTxtAddContactName;
    @BindView(R.id.cboAddContactIsdCode)
    AppCompatSpinner mCboAddContactIsdCode;
    private final Observer<List<IsdCode>> mIsdCodesObserver
            = new Observer<List<IsdCode>>() {
        @Override
        public void onChanged(@Nullable List<IsdCode> isdCodes) {
            if (isdCodes == null || isdCodes.size() <= 0) return;
            ArrayAdapter<IsdCode> mAdapter = new ArrayAdapter<>(getActivity(), R.layout.extra_simple_spinner_item, R.id.lblSpinnerLabel, isdCodes);
            if (mCboAddContactIsdCode != null) {
                mCboAddContactIsdCode.setAdapter(mAdapter);
            }
        }
    };
    @BindView(R.id.txtAddContactPhone)
    TextInputEditText mTxtAddContactPhone;
    Unbinder unbinder;
    private ContactsViewModel mViewModel;
    private IsdCode mSelectedIsdCode;

    private Observer<StatusMessage> mStatusMessageObserver = new Observer<StatusMessage>() {
        @Override
        public void onChanged(@Nullable StatusMessage statusMessage) {
            dismissDialog();
            if (statusMessage == null || statusMessage.status == null || !statusMessage.status.toLowerCase().contains("success")) {
                M.showAlert(getActivity(), "Add contact", "An error occurred please try again later",
                        "OK", null, null, null, false);
            } else {
                M.showToast(getActivity(), "Added Successfully");
                getActivity().finish();
            }
        }
    };

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new ContactsViewModel.ContactsViewModelFactory(getActivity().getApplication()))
                .get(ContactsViewModel.class);
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
        mViewModel.getStatusMessageData().observe(this, mStatusMessageObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnNext)
    public void onViewClicked() {

        String name = mTxtAddContactName.getText().toString();
        String phone = mTxtAddContactPhone.getText().toString();
        if (TextUtils.isEmpty(name)) {
            M.showToast(getActivity(), "Please enter a name");
            mTxtAddContactName.requestFocus();
            return;
        }
        if (mSelectedIsdCode == null) {
            M.showToast(getActivity(), "Please select isd code");
            mCboAddContactIsdCode.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            M.showToast(getActivity(), "Please enter phone number");
            mTxtAddContactPhone.requestFocus();
            return;
        }

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
        InternalContact contact = new InternalContact("", name,mSelectedIsdCode.isdCode+" "+ phone);
        mViewModel.addContact(contact);

    }
}
