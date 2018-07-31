package com.twixttechnologies.tjss.view.fragment.signin.alert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.utils.M;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 03-11-17.
 */

public class FragmentForgotPasswordDialog extends AppCompatDialogFragment {

    @BindView(R.id.txtSignInPhone)
    TextInputEditText mTxtSignInPhone;
    @BindView(R.id.btnSubmit)
    AppCompatButton mBtnSubmit;
    Unbinder unbinder;
    private OnSubmitListener mOnSubmitListener;

    public void setOnSubmitListener(OnSubmitListener onSubmitListener) {
        mOnSubmitListener = onSubmitListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSubmit)
    public void onViewClicked() {
        if (TextUtils.isEmpty(mTxtSignInPhone.getText())) {
            M.showToast(getActivity(), "Please enter your email");
            return;
        }

        mOnSubmitListener.onSubmit(mTxtSignInPhone.getText().toString());
        dismiss();
    }


    public interface OnSubmitListener {
        void onSubmit(String email);
    }


}
