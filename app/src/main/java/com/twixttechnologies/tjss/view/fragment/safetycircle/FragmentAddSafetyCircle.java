package com.twixttechnologies.tjss.view.fragment.safetycircle;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.CreateSafetyCircleResponse;
import com.twixttechnologies.tjss.model.network.response.SignUpAndLoginResponse;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.utils.PreferenceHelper;
import com.twixttechnologies.tjss.view.activity.HomeActivity;
import com.twixttechnologies.tjss.view.activity.PlansListingActivity;
import com.twixttechnologies.tjss.view.activity.ShareInviteCodeActivity;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.view.fragment.signup.SignUpFragment;
import com.twixttechnologies.tjss.viewmodel.SafetyCircleViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 15-07-17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class FragmentAddSafetyCircle extends BaseFragment {

    public static final String TAG = "AddSafetyCircle";
    public static final String FINISH_ACTIVITY = "finishActivity";
    public static final String CIRCLE_CREATE_BUNDLE = "bundle";
    @BindView(R.id.chkCreateSafetyCirclePrimaryOrNot)
    AppCompatCheckBox mChkCreateSafetyCirclePrimaryOrNot;

    @BindView(R.id.txtSafetyCircleName)
    TextInputLayout mTxtSafetyCircleName;

    @BindView(R.id.safetyCircleNameFriends)
    AppCompatButton mSafetyCircleNameFriends;

    @BindView(R.id.safetyCircleNameColleagues)
    AppCompatButton mSafetyCircleNameColleagues;

    @BindView(R.id.safetyCircleNameGymBuddies)
    AppCompatButton mSafetyCircleNameGymBuddies;

    @BindView(R.id.safetyCircleNameSiblings)
    AppCompatButton mSafetyCircleNameSiblings;

    @BindView(R.id.safetyCircleNameSpecialPersons)
    AppCompatButton mSafetyCircleNameSpecialPersons;

    @BindView(R.id.btnDone)
    AppCompatButton mBtnDone;

    Unbinder unbinder;

    private Bundle mUserdata;

    private Handler mHandler = new Handler();

    private Runnable mDismissRunnable;

    private SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private SafetyCircleViewModel mViewModel;
    private ProgressDialog mProgressDialog;
    private Observer<String> mErrorObserver = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            if (s == null || s.equals("")) return;
            M.showToast(getActivity(), s);
        }
    };
    private Observer<CreateSafetyCircleResponse> mCreateSafetyCircleResponseObserver
            = new Observer<CreateSafetyCircleResponse>() {
        @Override
        public void onChanged(@Nullable final CreateSafetyCircleResponse createSafetyCircleResponse) {

            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
            try {
                mHandler.removeCallbacks(mDismissRunnable);
            } catch (Exception e) {
                M.log(e.getMessage());
            }

            if (createSafetyCircleResponse == null) {
                M.showAlert(getActivity(), "Create safety circle", "An error occurred", "OK", null, null,
                        null, true);
                return;
            }
            if (createSafetyCircleResponse.status.trim().toLowerCase().equals("success")) {

                if (createSafetyCircleResponse.primary.equals("1")) {
                    PreferenceHelper.saveString(getActivity(), PreferenceHelper.KEY_PRIMARY_CIRCLE, createSafetyCircleResponse.circleId);
                }

                if (mUserdata == null) {
                    M.showAlert(getActivity(), "Share Invite Code", "Your new safety circle created " +
                            "successfully, Do you want to invite new members to your safety " +
                            "circle", "INVITE", "NO,THANKS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String inviteCode = createSafetyCircleResponse.inviteCode;

                            Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(getActivity());
                            taskStackBuilder.addParentStack(HomeActivity.class);
                            taskStackBuilder.addNextIntent(homeIntent);


                            Intent shareIntent = new Intent(getActivity(), ShareInviteCodeActivity.class);
                            shareIntent.putExtra(ShareInviteCodeActivity.INVITE_CODE, inviteCode);
                            taskStackBuilder.addNextIntent(shareIntent);
                            taskStackBuilder.startActivities();
                            getActivity().finish();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent home = new Intent(getActivity(), HomeActivity.class);
                            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(home);
                            getActivity().finish();
                        }
                    }, false);


                } else {
                    //Create an artificial back stack with home as the first activity
                    Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
                    Intent plansIntent = new Intent(getActivity(), PlansListingActivity.class);
                    plansIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivities(new Intent[]{homeIntent, plansIntent});
                    getActivity().finishAffinity();
                }
            } else {
                M.showAlert(getActivity(), "Create circle", createSafetyCircleResponse.status, "OK",
                        null, null, null, false);
            }

        }
    };

    public static FragmentAddSafetyCircle newInstance(Boolean finishActivity,
                                                      @Nullable Bundle userData) {
        Bundle args = new Bundle();
        args.putBoolean(FINISH_ACTIVITY, finishActivity);
        args.putBundle(SignUpFragment.USER, userData);
        FragmentAddSafetyCircle fragment = new FragmentAddSafetyCircle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        Bundle args = getArguments();
        if (args == null) return;
        mUserdata = args.getBundle(SignUpFragment.USER);

        if (mUserdata != null) {
            //perform a registration silently?
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_safety_circle, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new SafetyCircleViewModel.SafetyCircleViewModelFactory(getActivity().getApplication()))
                .get(SafetyCircleViewModel.class);

        mViewModel.getError().observe(this, mErrorObserver);
        mViewModel.getCreateSafetyCircleData().observe(this, mCreateSafetyCircleResponseObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.safetyCircleNameFriends, R.id.safetyCircleNameColleagues,
            R.id.safetyCircleNameGymBuddies, R.id.safetyCircleNameSiblings,
            R.id.safetyCircleNameSpecialPersons, R.id.btnDone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.safetyCircleNameFriends:
            case R.id.safetyCircleNameColleagues:
            case R.id.safetyCircleNameGymBuddies:
            case R.id.safetyCircleNameSiblings:
            case R.id.safetyCircleNameSpecialPersons:
                if (mTxtSafetyCircleName.getEditText() != null) {
                    mTxtSafetyCircleName.getEditText().setText(view.getTag().toString());
                    mTxtSafetyCircleName.getEditText().setSelection(view.getTag().toString().length());
                }
                break;
            case R.id.btnDone:
                if (mTxtSafetyCircleName.getEditText() != null) {
                    if (TextUtils.isEmpty(mTxtSafetyCircleName.getEditText().getText())) {
                        M.showToast(getActivity(), "Please enter a name for your safety circle " +
                                "or select one from the chatItems");
                        return;
                    }
                    String name = mTxtSafetyCircleName.getEditText().getText().toString().trim();
                    if (name.equals("")) {
                        M.showToast(getActivity(), "Please enter a name for your safety circle " +
                                "or select one from the chatItems");
                        return;
                    }

                    if (mProgressDialog == null) {
                        mProgressDialog = new ProgressDialog(getActivity());
                        mProgressDialog.setCancelable(false);
                    }
                    mProgressDialog.setMessage("Creating circle :" + mTxtSafetyCircleName.getEditText().getText().toString());
                    mProgressDialog.show();
                    if (mUserdata == null) {
                        createSafetyCircle();
                    } else {
                        Observer<SignUpAndLoginResponse> mSignUpResponseObserver = new Observer<SignUpAndLoginResponse>() {
                            @Override
                            public void onChanged(@Nullable SignUpAndLoginResponse signUpAndLoginResponse) {
                                if (mProgressDialog != null) mProgressDialog.dismiss();
                                if (signUpAndLoginResponse == null) {
                                    M.showSnackBar(
                                            mBtnDone, "An error occurred, Please try again later");
                                } else {
                                    if (signUpAndLoginResponse.message != null && !signUpAndLoginResponse.message.equals("Success")) {
                                        M.showAlert(getActivity(), "TJSS", signUpAndLoginResponse.message, "OK", null, null, null, false);
                                    } else if (signUpAndLoginResponse.message != null && signUpAndLoginResponse.message.equals("Success")) {
                                        createSafetyCircle();
                                    } else {
                                        M.showAlert(getActivity(), "TJSS", "An error occurred, Please try again later", "OK", null, null, null, false);
                                    }

                                }
                            }
                        };
                        mViewModel.signUp(FragmentAddSafetyCircle.this, mUserdata, mSignUpResponseObserver);
                    }

                }
                break;
        }
    }

    private void createSafetyCircle() {
        if (mDismissRunnable == null) {
            mDismissRunnable = new Runnable() {
                @Override
                public void run() {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                        try {
                            M.showAlert(getActivity(), "TJSS",
                                    "Seems like you are not connected to the internet,Please " +
                                            "check your internet connection and try again", "OK",
                                    null, null, null, false);
                        } catch (Exception e) {
                            M.log(e.getMessage());
                        }
                    }
                }
            };
        }
        mHandler.postDelayed(mDismissRunnable, 30 * 1000);

        mViewModel.create(mTxtSafetyCircleName.getEditText().getText().toString(),
                mUserdata != null || mChkCreateSafetyCirclePrimaryOrNot.isChecked());
    }

}
