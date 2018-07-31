package com.innoviussoftwaresolution.tjss.view.fragment.user.alert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 03-11-17.
 */

@SuppressWarnings("ConstantConditions")
public class FirstAndLastNameDialog extends AppCompatDialogFragment {

    @BindView(R.id.txtFirstName)
    TextInputLayout mTxtFirstName;
    @BindView(R.id.txtLastName)
    TextInputLayout mTxtLastName;
    @BindView(R.id.btnSubmit)
    AppCompatButton mBtnSubmit;
    Unbinder unbinder;
    private OnNameUpdateListener mListener;


    public void setListener(OnNameUpdateListener listener) {
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firs_and_last_name, container, false);
        unbinder = ButterKnife.bind(this, view);
        mTxtFirstName.getEditText().setText(PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_FIRST_NAME, ""));
        mTxtLastName.getEditText().setText(PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_LAST_NAME, ""));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {

        if (TextUtils.isEmpty(mTxtFirstName.getEditText().getText()) && TextUtils.isEmpty(mTxtLastName.getEditText().getText())) {
            M.showToast(getActivity(), "Please enter first name");
            return;
        }

        if (TextUtils.isEmpty(mTxtFirstName.getEditText().getText())) {
            M.showToast(getActivity(), "Please enter first name");
        }

        String lastName;

        if (TextUtils.isEmpty(mTxtLastName.getEditText().getText())) {
            lastName = "";
        } else {
            lastName = mTxtLastName.getEditText().getText().toString();
        }

        mListener.onUpdate(mTxtFirstName.getEditText().getText().toString(), lastName);
        dismiss();
    }


    public interface OnNameUpdateListener {
        void onUpdate(String firstName, String lastName);
    }


}
