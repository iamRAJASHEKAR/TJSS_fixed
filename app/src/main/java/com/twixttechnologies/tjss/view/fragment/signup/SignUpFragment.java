package com.twixttechnologies.tjss.view.fragment.signup;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.InviteCodeVerifyResponse;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.activity.CreateCircleActivity;
import com.twixttechnologies.tjss.view.activity.SignInActivity;
import com.twixttechnologies.tjss.view.adapter.pageradapter.SignUpPagerAdapter;
import com.twixttechnologies.tjss.view.viewutils.ViewPagerPageChangedAdapter;
import com.twixttechnologies.tjss.viewmodel.SignUpViewModel;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 30/06/17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class SignUpFragment extends LoginSignUpFragmentBase {

    public static final String TAG = "SignUp";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PHONE = "phone";
    public static final String ISD_CODE = "isdCode";
    public static final String IMAGE = "image";
    public static final String USER = "user";


    private final Handler mHandler = new Handler();
    @SuppressWarnings("HardCodedStringLiteral")
    private final String[] mProgressTexts = new String[]{"Phone Number", "Password", "Secure Your Account",
            "Your Profile", "Your Circle", "Select Plan"};
    @BindView(R.id.lblSignUpSignIn)
    AppCompatTextView mLblSignUpSignIn;

    @BindView(R.id.lblSignUpSignUp)
    AppCompatTextView mLblSignUpSignUp;

    @BindView(R.id.btnSignUpBack)
    AppCompatButton mBtnSignUpBack;

    @BindView(R.id.lblSignUpProgress)
    TextSwitcher mLblSignUpProgress;

    @BindView(R.id.lblSignUpStepText)
    TextSwitcher mLblSignUpStepText;

    @BindView(R.id.pgrsSignUpProgress)
    ProgressBar mPgrsSignUpProgress;

    @BindView(R.id.vpSignUp)
    ViewPager mVpSignUp;

    @BindView(R.id.btnNext)
    AppCompatButton mBtnNext;

    @BindView(R.id.lblSignUpCreateCircle)
    AppCompatTextView mLblSignUpCreateCircle;

    Unbinder unbinder;

    private SignUpPagerAdapter mPagerAdapter;
    private ViewPagerPageChangedAdapter mPageChangedListener;
    private SignUpViewModel mViewModel;
    private Observer<InviteCodeVerifyResponse> mInviteCodeChangeObserver
            = new Observer<InviteCodeVerifyResponse>() {
        @Override
        public void onChanged(@Nullable InviteCodeVerifyResponse inviteCodeVerifyResponse) {

            if (inviteCodeVerifyResponse == null) {
                Toast t = Toast.makeText(getActivity(), "An error occurred. Please try again later.", Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP, 0, 200);
                t.show();
                dismissDialog();
            } else if (inviteCodeVerifyResponse.status == 1) {
                mViewModel.signUp(mPagerAdapter.getName(), mPagerAdapter.getEmail(),
                        mPagerAdapter.getPhone(), mPagerAdapter.getIsdCode(), mPagerAdapter.getPassword(),
                        inviteCodeVerifyResponse.userslist, inviteCodeVerifyResponse.circle,
                        mPagerAdapter.getImage(), UUID.randomUUID().toString());
                Toast.makeText(getActivity(),UUID.randomUUID().toString(),Toast.LENGTH_LONG).show();
            } else {
                if (mProgressDialog != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
                Toast t = Toast.makeText(getActivity(), inviteCodeVerifyResponse.message, Toast.LENGTH_LONG);
                t.setGravity(Gravity.TOP, 0, 200);
                t.show();
            }
        }
    };


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        disableUp();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mPageChangedListener == null) {
            mPageChangedListener = new ViewPagerPageChangedAdapter() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    mBtnSignUpBack.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
                    mLblSignUpProgress.setText(String.valueOf(position + 1));
                    mLblSignUpStepText.setText(mProgressTexts[position]);
                    mPgrsSignUpProgress.setProgress(position + 1);

                    if (position >= mPagerAdapter.getCount() - 1) {
                        mBtnNext.setText(R.string.submit);
                    } else {
                        mBtnNext.setText(R.string.next);
                    }

                    mHandler.postDelayed(new VisibilityRunnable(position == mPagerAdapter.getCount() - 1), 250);
                }
            };
        }

        Animation inAnimation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
        Animation outAnimation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);

        mLblSignUpProgress.setInAnimation(inAnimation);
        mLblSignUpProgress.setOutAnimation(outAnimation);

        mLblSignUpStepText.setInAnimation(inAnimation);
        mLblSignUpStepText.setOutAnimation(outAnimation);

        mLblSignUpProgress.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                @SuppressLint("InflateParams")
                TextView progressTextView = (TextView) getActivity().getLayoutInflater()
                        .inflate(R.layout.extra_text_switcher_text, null, false);
                progressTextView.setText(String.valueOf(mVpSignUp.getCurrentItem() + 1));
                progressTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
                return progressTextView;
            }
        });

        mLblSignUpStepText.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView progressStepTextView = new TextView(getActivity());
                progressStepTextView.setTextSize(15);
                progressStepTextView.setText(mProgressTexts[mVpSignUp.getCurrentItem()]);
                progressStepTextView.setGravity(Gravity.CENTER);
                progressStepTextView.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
                return progressStepTextView;
            }
        });

        if (mPagerAdapter == null) {
            mPagerAdapter = new SignUpPagerAdapter(getChildFragmentManager());
        }
        mVpSignUp.addOnPageChangeListener(mPageChangedListener);
        mVpSignUp.setAdapter(mPagerAdapter);

        mViewModel = ViewModelProviders.of(this,
                new SignUpViewModel.SignUpViewModelFactory(getActivity().getApplication()))
                .get(SignUpViewModel.class);

        mViewModel.getInviteCodeVerifyLiveData().observe(this, mInviteCodeChangeObserver);
        mViewModel.getError().observe(this, mErrorObserver);
        mViewModel.getSignUpResponseLiveData().observe(this, mSignUpResponseObserver);

        mVpSignUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.lblSignUpSignIn, R.id.btnSignUpBack, R.id.btnNext, R.id.lblSignUpCreateCircle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lblSignUpSignIn:
                Intent signIntent = new Intent(getActivity(), SignInActivity.class);
                signIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signIntent);
                getActivity().finishAffinity();
                break;
            case R.id.btnSignUpBack:
                //Show the previous fragment if the current index is > 0
                if (mVpSignUp.getCurrentItem() > 0)
                    mVpSignUp.setCurrentItem(mVpSignUp.getCurrentItem() - 1, true);
                break;
            case R.id.btnNext:
                if (mVpSignUp.getCurrentItem() != 4)
                    showNext();
                else {
                    String inviteCode = mPagerAdapter.getInviteCode();
                    if (inviteCode == null || inviteCode.equals("")) {
                        M.showToast(getActivity(), "Please enter an invite code or create new circle");
                    } else {
                        registerUser();
                    }
                }

                break;
            case R.id.lblSignUpCreateCircle:
                int index = mPagerAdapter.checkHasValidInput();
                if (index > 0) {
                    showErrorToast();
                    mVpSignUp.setCurrentItem(index, true);
                    break;
                }
                Intent createCircleIntent = new Intent(getActivity(), CreateCircleActivity.class);
                Bundle b = new Bundle();
                b.putString(NAME, mPagerAdapter.getName());
                b.putString(EMAIL, mPagerAdapter.getEmail());
                b.putString(PASSWORD, mPagerAdapter.getPassword());
                b.putString(PHONE, mPagerAdapter.getPhone());
                b.putString(ISD_CODE, mPagerAdapter.getIsdCode());
                b.putString(IMAGE, mPagerAdapter.getImage());
               // Toast.makeText(getActivity(),mPagerAdapter.getImage(),Toast.LENGTH_LONG).show();
                createCircleIntent.putExtra(USER, b);
                //startActivityForResult(new Intent(getActivity(), CreateCircleActivity.class), CREATE_SAFETY_CIRCLE_REQUEST_CODE);
                startActivity(createCircleIntent);
                break;
        }
    }

    private void registerUser() {
        int index = mPagerAdapter.checkHasValidInput();
        if (index >= 0) {
            showErrorToast();
            mVpSignUp.setCurrentItem(index, true);
            return;
        }

        initProgress();
        initErrorObserver();

        String inviteCode = mPagerAdapter.getInviteCode();
        mViewModel.verify(inviteCode);
    }

    private void showErrorToast() {
        Toast T = Toast.makeText(getActivity(), "Please review your input", Toast.LENGTH_SHORT);
        T.setGravity(Gravity.TOP, 0, 200);
        T.show();
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        super.onBaseCreate(SavedInstanceState);
    }

    @Override
    protected boolean canGoBack() {
        int index = mVpSignUp.getCurrentItem();
        if (index > 0)
            mVpSignUp.setCurrentItem(index - 1);
        return index == 0;
    }

    @Override
    public void showNext() {
        boolean valid = false;
        switch (mVpSignUp.getCurrentItem()) {
            case 0: {
                String phone = mPagerAdapter.getPhone();
                String isdCode = mPagerAdapter.getIsdCode();
                if (TextUtils.isEmpty(phone)) {
                    M.showToast(getActivity(), "Please enter phone number");
                } else if (!TextUtils.isDigitsOnly(phone)) {
                    M.showToast(getActivity(), "Please enter a valid phone number");
                } else if (isdCode.contains("91")) {
                    if (phone.length() != 10) {
                        M.showToast(getActivity(), "Please enter a valid phone number");
                    } else {
                        valid = true;
                    }
                } else if (isdCode.contains("234")) {
                    if (phone.length() != 11) {
                        M.showToast(getActivity(), "Please enter a valid phone number");
                    } else {
                        valid = true;
                    }
                } else {
                    valid = true;
                }
                break;
            }

            case 1: {
                String password = mPagerAdapter.getPassword();
                if (TextUtils.isEmpty(password)) {
                    M.showToast(getActivity(), "Please enter password");
                } else if (password.length() < 6) {
                    M.showToast(getActivity(), "Password should be at-least 6 characters");
                } else {
                    valid = true;
                }
                break;
            }

            case 2: {
                String email = mPagerAdapter.getEmail();
                if (TextUtils.isEmpty(email)) {
                    M.showToast(getActivity(), "Please enter email");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    M.showToast(getActivity(), "Please enter a valid email address");
                } else {
                    valid = true;
                }
                break;
            }

            case 3: {
                String filePath = mPagerAdapter.getImage();
                if (TextUtils.isEmpty(filePath)) {
                    M.showToast(getActivity(), "Please select a profile pic");
                    return;
                }

                String name = mPagerAdapter.getName();
                if (TextUtils.isEmpty(name)) {
                    M.showToast(getActivity(), "Please enter your name");
                    return;
                }
                valid = true;
                break;
            }

            case 4: {
                String inviteCode = mPagerAdapter.getInviteCode();
                if (TextUtils.isEmpty(inviteCode)) {
                    M.showToast(getActivity(), "Please enter an invite code or create new circle");
                } else
                    hideKeyboard();
                break;
            }
        }

        if (!valid) return;
        if (mVpSignUp.getCurrentItem() < mPagerAdapter.getCount() - 1) {
            mVpSignUp.setCurrentItem(mVpSignUp.getCurrentItem() + 1);
        /*else if (mVpSignUp.getCurrentItem() < mPagerAdapter.getCount() - 1) {
            String inviteCode = mPagerAdapter.getInviteCode();
            if (inviteCode == null || inviteCode.equals("")) {
                M.showToast(getActivity(), "Please enter an invite code or create new circle");
            } else {
                registerUser();
            }*/
        }/* else if (mVpSignUp.getCurrentItem() == mVpSignUp.getAdapter().getCount() - 1) {
            //register user and move the rest of the code to the registration callback
            registerUser();

        }*/

    }

    private class VisibilityRunnable implements Runnable {

        private final boolean show;

        VisibilityRunnable(boolean show) {
            this.show = show;
        }

        @Override
        public void run() {
            mLblSignUpCreateCircle.setVisibility(show ? View.VISIBLE : View.GONE);

        }
    }
}
