package com.innoviussoftwaresolution.tjss.view.fragment.safetycircle;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chaos.view.PinView;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.view.activity.HomeActivity;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment;
import com.innoviussoftwaresolution.tjss.viewmodel.SafetyCircleViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 21-07-17.
 */

public class FragmentJoinCircle extends BaseFragment {

    public static final String TAG = "fragmentJoinCircle";
    @BindView(R.id.txtSecurityCode)
    PinView mTxtSecurityCode;
    @BindView(R.id.btnSubmitCircleInviteCode)
    AppCompatButton mBtnSubmitCircleInviteCode;

    AppCompatButton btnJoinCircle;
    Unbinder unbinder;

    private SafetyCircleViewModel mViewModel;
    private ProgressDialog mProgressDialog;
    private Observer<StatusMessage> mStatusMessageObserver
            = new Observer<StatusMessage>() {
        @Override
        public void onChanged(@Nullable StatusMessage statusMessage) {

            dismissProgressDialog();

            if (statusMessage == null) {
                M.showAlert(getActivity(), "Join Circle", "Unable to join circle, please try again later",
                        "OK", null, null, null, false);
                return;
            }
            if (statusMessage.status.toLowerCase().trim().equals("success")) {
                M.showToast(getActivity(), "Joined successfully!");
                Intent home = new Intent(getContext(), HomeActivity.class);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(home);
                getActivity().finish();
            } else {
                M.showToast(getActivity(), statusMessage.status);
            }

        }
    };
    private Observer<String> mErrorObserver
            = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            dismissProgressDialog();
            if (s == null || s.equals("")) return;
            M.showAlert(getActivity(), "Join Circle", s, "OK", null, null, null, false);
        }
    };

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_circle, container, false);
        unbinder = ButterKnife.bind(this, view);
        btnJoinCircle=(AppCompatButton)view.findViewById(R.id.btnRequestCode);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new SafetyCircleViewModel.SafetyCircleViewModelFactory(getActivity().getApplication()))
                .get(SafetyCircleViewModel.class);

        mViewModel.getStatusData().observe(this, mStatusMessageObserver);
        mViewModel.getError().observe(this, mErrorObserver);

        if(TAG.equalsIgnoreCase("fragmentJoinCircle"))
        {
            btnJoinCircle.setVisibility(View.GONE);
        }

    }

    private void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSubmitCircleInviteCode)
    public void onViewClicked() {
        if (TextUtils.isEmpty(mTxtSecurityCode.getText().toString())) {
            M.showAlert(getActivity(), "Join Circle",
                    "Please enter the join code or ask the circle admin for the join code",
                    "OK", null, null, null, false);
            return;
        }
        String joinCode = mTxtSecurityCode.getText().toString();
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Joining...");
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
        mViewModel.join(joinCode);
    }
}
