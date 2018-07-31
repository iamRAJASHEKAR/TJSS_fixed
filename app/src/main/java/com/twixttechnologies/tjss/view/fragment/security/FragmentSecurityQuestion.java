package com.twixttechnologies.tjss.view.fragment.security;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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

import com.chaos.view.PinView;
import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.SecurityQuestion;
import com.twixttechnologies.tjss.model.network.response.SelectedSecurityQuestionResponse;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.utils.PreferenceHelper;
import com.twixttechnologies.tjss.view.activity.HomeActivity;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.viewmodel.SecurityQuestionsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 31-10-17.
 */

public class FragmentSecurityQuestion extends BaseFragment implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "securityQuestion";

    public static final String REDIRECT = "redirect";
    @BindView(R.id.cboSecurityQuestions)
    AppCompatSpinner mCboSecurityQuestions;
    @BindView(R.id.txtSecurityAnswer)
    TextInputEditText mTxtSecurityAnswer;
    @BindView(R.id.pinSecurityQuestion)
    PinView mPinSecurityQuestion;
    @BindView(R.id.btnSubmitSecurity)
    AppCompatButton mBtnSubmitSecurity;
    Unbinder unbinder;

    private SecurityQuestion mSelectedSecurityQuestion = null;

    private Observer<StatusMessage> mStatusObserver;
    private boolean redirect;
    private SecurityQuestionsViewModel mViewModel;
    private Observer<SelectedSecurityQuestionResponse> mSelectedSecurityQuestionResponseObserver
            = new Observer<SelectedSecurityQuestionResponse>() {
        @Override
        public void onChanged(@Nullable SelectedSecurityQuestionResponse selectedSecurityQuestionResponse) {
            if (selectedSecurityQuestionResponse == null) return;
            List<SecurityQuestion> securityQuestions = mViewModel.getSecurityQuestionsData().getValue();
            if (securityQuestions != null) {
                int selected = -1;
                for (int i = 0; i < securityQuestions.size(); i++) {
                    if (securityQuestions.get(i).id == selectedSecurityQuestionResponse.id) {
                        mSelectedSecurityQuestion = securityQuestions.get(i);
                        selected = i;
                        break;
                    }
                }
                if (selected >= 0) {
                    try {
                        mCboSecurityQuestions.setSelection(selected);
                        mTxtSecurityAnswer.setText(selectedSecurityQuestionResponse.securityAnswer);
                        mPinSecurityQuestion.setText(selectedSecurityQuestionResponse.securityPin);
                    } catch (Exception e) {
                        M.log(e.getMessage());
                    }
                }
            }
        }
    };

    public static FragmentSecurityQuestion newInstance(boolean redirect) {
        Bundle args = new Bundle();
        args.putBoolean(REDIRECT, redirect);
        FragmentSecurityQuestion fragment = new FragmentSecurityQuestion();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        redirect = getArguments().getBoolean(REDIRECT, true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_security_question, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new SecurityQuestionsViewModel.SecurityQuestionsViewModelFactory(getActivity().getApplication()))
                .get(SecurityQuestionsViewModel.class);

        mViewModel.getSecurityQuestionsData().observe(this, new Observer<List<SecurityQuestion>>() {
            @Override
            public void onChanged(@Nullable List<SecurityQuestion> securityQuestions) {
                if (securityQuestions == null) return;
                if (securityQuestions.size() <= 0) return;
                ArrayAdapter<SecurityQuestion> securityQuestionArrayAdapter
                        = new ArrayAdapter<>(getActivity(), R.layout.extra_simple_spinner_item,
                        R.id.lblSpinnerLabel, securityQuestions);

                mCboSecurityQuestions.setAdapter(securityQuestionArrayAdapter);
                mCboSecurityQuestions.setOnItemSelectedListener(FragmentSecurityQuestion.this);
            }
        });

        mViewModel.getSelectedSecurityQuestionData().observe(this,
                mSelectedSecurityQuestionResponseObserver);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("Security Question");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSubmitSecurity)
    public void onViewClicked() {

        if (mSelectedSecurityQuestion == null) {
            M.showToast(getActivity(), "Please select a security question");
            return;
        }

        CharSequence answer = mTxtSecurityAnswer.getText();
        if (TextUtils.isEmpty(answer)) {
            M.showToast(getActivity(), "Please enter your answer");
            return;
        }

        CharSequence pin = mPinSecurityQuestion.getText();
        if (TextUtils.isEmpty(pin)) {
            M.showToast(getActivity(), "Please enter a pin");
            return;
        }

        if (pin.length() < 4) {
            M.showToast(getActivity(), "Pin should contain 4 digits");
            return;
        }

        if (mStatusObserver == null) {
            mStatusObserver = new Observer<StatusMessage>() {
                @Override
                public void onChanged(@Nullable StatusMessage statusMessage) {
                    dismissDialog();
                    if (statusMessage == null || statusMessage.status == null ||
                            statusMessage.status.equals("") ||
                            !statusMessage.status.toLowerCase().contains("success")) {
                        M.showAlert(getActivity(), "Update Security Question",
                                "An error occurred please try again later",
                                "OK", null, null, null, false);
                    } else {
                        M.showToast(getActivity(), "Updated successfully");
                        PreferenceHelper.saveBoolean(getActivity(), PreferenceHelper.KEY_ADDED_SECURITY, true);
                        if (redirect) {
                            Intent home = new Intent(getActivity(), HomeActivity.class);
                            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(home);
                            getActivity().finishAffinity();
                        } else {
                            getActivity().finish();
                        }
                    }
                }
            };
            mViewModel.getStatusMessageData().observe(this, mStatusObserver);
        }

        initProgress();
        initErrorObserver();
        mViewModel.update(mSelectedSecurityQuestion, answer.toString(), pin.toString());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position < 0) return;
        if (mViewModel == null) return;
        List<SecurityQuestion> securityQuestions = mViewModel.getSecurityQuestionsData().getValue();
        if (securityQuestions == null) return;
        mSelectedSecurityQuestion = securityQuestions.get(position);
        M.log(mSelectedSecurityQuestion.toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
