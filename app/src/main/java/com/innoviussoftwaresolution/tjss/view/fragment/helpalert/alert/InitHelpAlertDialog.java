package com.innoviussoftwaresolution.tjss.view.fragment.helpalert.alert;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bugsnag.android.Bugsnag;
import com.chaos.view.PinView;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.SelectedSecurityQuestionResponse;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.viewmodel.AppServiceProvidersViewModel;
import com.innoviussoftwaresolution.tjss.viewmodel.SecurityQuestionsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 03-11-17.
 */

public class InitHelpAlertDialog extends AppCompatDialogFragment {

    @BindView(R.id.txtSecurityAnswer)
    TextInputEditText mTxtSecurityAnswer;
    @BindView(R.id.pinSecurityQuestion)
    PinView mPinSecurityQuestion;
    Unbinder unbinder;
    @BindView(R.id.lblSecurityQuestion)
    AppCompatTextView mLblSecurityQuestion;
    private OnStatusListener mListener;


    private SecurityQuestionsViewModel mViewModel;

    private Observer<SelectedSecurityQuestionResponse> mSelectedSecurityQuestionResponseObserver
            = new Observer<SelectedSecurityQuestionResponse>() {
        @Override
        public void onChanged(@Nullable SelectedSecurityQuestionResponse selectedSecurityQuestionResponse) {
            if (selectedSecurityQuestionResponse == null) return;
            try {
                mLblSecurityQuestion.setText(selectedSecurityQuestionResponse.question);
            } catch (Exception e) {
                Bugsnag.notify(new RuntimeException(e));
                M.log(e.getMessage());
            }
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new SecurityQuestionsViewModel.SecurityQuestionsViewModelFactory(getActivity().getApplication()))
                .get(SecurityQuestionsViewModel.class);

        mViewModel.getSelectedSecurityQuestionData().observe(this,
                mSelectedSecurityQuestionResponseObserver);
        Bugsnag.init(getContext());
    }


    public void setListener(OnStatusListener listener) {
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_init_help_alert, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSubmitSecurity)
    public void onViewClicked() {

        SelectedSecurityQuestionResponse selectedSecurityQuestionResponse = mViewModel.getSelectedSecurityQuestionData().getValue();
        if (selectedSecurityQuestionResponse == null) {
            M.log("selected security question is null");
        } else {
            if (TextUtils.isEmpty(mTxtSecurityAnswer.getText())) {
                mListener.onStatus(false);
                return;
            }
            if (TextUtils.isEmpty(mPinSecurityQuestion.getText())) {
                mListener.onStatus(false);
                return;
            }

            String answer = mTxtSecurityAnswer.getText().toString();
            if (!selectedSecurityQuestionResponse.securityAnswer.equals(answer)) {
                mListener.onStatus(false);
                return;
            }

            String pin = mPinSecurityQuestion.getText().toString();

            if (!String.valueOf(selectedSecurityQuestionResponse.securityPin).equals(pin)) {
                mListener.onStatus(false);
                return;
            }
            mListener.onStatus(true);
            dismiss();
           // providersViewModel=new AppServiceProvidersViewModel();


        }

    }

    public interface OnStatusListener {
        void onStatus(boolean success);
    }

}
