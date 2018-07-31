package com.innoviussoftwaresolution.tjss.view.fragment.signup;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
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

import com.bugsnag.android.Bugsnag;
import com.google.gson.GsonBuilder;
import com.innoviussoftwaresolution.tjss.APIcalls.APIClient;
import com.innoviussoftwaresolution.tjss.APIcalls.EndpointsServices;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.InviteCodeVerifyResponse;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.view.activity.CreateCircleActivity;
import com.innoviussoftwaresolution.tjss.view.activity.SignInActivity;
import com.innoviussoftwaresolution.tjss.view.adapter.pageradapter.SignUpPagerAdapter;
import com.innoviussoftwaresolution.tjss.view.viewutils.ViewPagerPageChangedAdapter;
import com.innoviussoftwaresolution.tjss.viewmodel.SignUpViewModel;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.SharedPreferences.*;
import static com.innoviussoftwaresolution.tjss.view.fragment.signup.SignUpStepOneOtp.resendOtp;

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


    private static CountDownTimer timer;
    private final Handler mHandler = new Handler();
    @SuppressWarnings("HardCodedStringLiteral")
    private final String[] mProgressTexts = new String[]{"Phone Number", "OTP", "Password", "Secure Your Account",
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
                String deviceId = UUID.randomUUID().toString();
                mViewModel.signUp(mPagerAdapter.getName(), mPagerAdapter.getEmail(),
                        mPagerAdapter.getPhone(), mPagerAdapter.getIsdCode(), mPagerAdapter.getPassword(),
                        inviteCodeVerifyResponse.userslist, inviteCodeVerifyResponse.circle,
                        mPagerAdapter.getImage(), deviceId); //UUID.randomUUID().toString());
                // Toast.makeText(getActivity(), UUID.randomUUID().toString(), Toast.LENGTH_LONG).show();
                M.showToast(getActivity(), mPagerAdapter.getImage());
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
                if (mVpSignUp.getCurrentItem() != 5)
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

    boolean isOtpSuccess = false;
    String otp;
    public String otpPin;

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
                        callPhoneNoGetOtp();
                        valid = true;
                    }
                } else if (isdCode.contains("234")) {
                    if (phone.length() != 10) {
                        M.showToast(getActivity(), "Please enter a valid phone number");
                    } else {
                        callPhoneNoGetOtp();
                        valid = true;
                    }
                } else if (isdCode.contains("44")) {
                    if (phone.length() != 10) {
                        M.showToast(getActivity(), "Please enter a valid phone number");
                    } else {
                        callPhoneNoGetOtp();
                        valid = true;
                    }
                } else if (isdCode.contains("1")) {
                    if (phone.length() != 10) {
                        M.showToast(getActivity(), "Please enter a valid phone number");
                    } else {
                        callPhoneNoGetOtp();
                        valid = true;
                    }
                } else if (isdCode.contains("33")) {
                    if (phone.length() != 9) {
                        M.showToast(getActivity(), "Please enter a valid phone number");
                    } else {
                        callPhoneNoGetOtp();
                        valid = true;
                    }
                } else {
                    callPhoneNoGetOtp();
                    valid = true;
                }
                break;
            }

            case 1: {

                if (resendOtp == null) {
                    otpPin = mPagerAdapter.getOtpCode();
                } else {
                    otpPin = resendOtp;
                }

//                String phoneNo = mPagerAdapter.getPhone();

//                String otpPin = "123456";
//                String phoneNo = "1234567890";

                if (TextUtils.isEmpty(otpPin)) {
                    M.showToast(getActivity(), "Please enter correct OTP");
                } else if (otpPin.length() < 6) {
                    M.showToast(getActivity(), "Otp should be at-least 6 characters");
                } else {

                    if (otp.equalsIgnoreCase(otpPin)) {
                        isOtpSuccess = true;
                    } else {
                        isOtpSuccess = false;
                        M.showToast(getActivity(), "Please enter correct OTP");
                    }

                    if (isOtpSuccess == true) {
                        valid = true;
                        M.showToast(getActivity(), "OTP successfully verified");
                        timer.cancel();
                    }
                }
                break;
            }


            case 2: {
                String password = mPagerAdapter.getPassword();
                String cpassword = mPagerAdapter.getCPassword();
                if (TextUtils.isEmpty(password)) {
                    M.showToast(getActivity(), "Please enter password");
                } else if (password.length() < 6) {
                    M.showToast(getActivity(), "Password should be at-least 6 characters");
                } else if (TextUtils.isEmpty(cpassword)) {
                    M.showToast(getActivity(), "Please enter Confirm Password");
                } else if (cpassword.length() < 6) {
                    M.showToast(getActivity(), "confirm Password should be at-least 6 characters");
                } else if (!cpassword.equals(password)) {
                    M.showToast(getActivity(), "Password are not match");
                } else {
                    valid = true;
                }
                break;
            }

            case 3: {
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

            case 4: {
                String filePath = mPagerAdapter.getImage();
                if (TextUtils.isEmpty(filePath)) {
                    M.showToast(getActivity(), "Please select a profile pic");
                    return;
                }

                String name = mPagerAdapter.getName();
                if (TextUtils.isEmpty(name)) {
                    M.showToast(getActivity(), "Please enter your name");
                    return;
                } else {
                    SharedPreferences preferences = getActivity().getSharedPreferences("userName", 0);
                    Editor editor = preferences.edit();
                    editor.putString("name", name);
                    editor.commit();
                }
                valid = true;
                break;
            }

            case 5: {
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

    private void callPhoneNoGetOtp() {

//        String otpPin = mPagerAdapter.getOtpCode();
        String phoneNo = mPagerAdapter.getPhone();
//        String phoneNo = mPagerAdapter.getPhone();
        String isdCode = mPagerAdapter.getIsdCode();
        SignUpOtp signUpOtp = new SignUpOtp();
        signUpOtp.setPhone(phoneNo);
        signUpOtp.setCode(isdCode);
        SharedPreferences preferences = getActivity().getSharedPreferences("isdCode", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("code", isdCode);
        editor.commit();
        Log.d("isdcode", isdCode);
        EndpointsServices apiInterFace = APIClient.getClient().create(EndpointsServices.class);
        Call<SignUpOtp> call = apiInterFace.signUpOtp(signUpOtp);

        call.enqueue(new Callback<SignUpOtp>() {
            @Override
            public void onResponse(Call<SignUpOtp> call, Response<SignUpOtp> response) {
                Log.e("Response", new GsonBuilder().setPrettyPrinting().create().toJson(response));
                Log.e("Otp response", "=====>" + response);

                if (response != null) {
                    if (response.body() != null) {
                        SignUpOtp responceSignUpOtp = new SignUpOtp();
                        responceSignUpOtp = response.body();
                        if (responceSignUpOtp.getOTP() != null) {
                            Log.e("Responce==>", "Otp==>" + responceSignUpOtp);
                            SessionManager.saveUserDetails(getActivity(), responceSignUpOtp);
                            M.showToast(getContext(), "OTP successfully sent");
                        } else {
                            showAlert("Error", "An error occurred. Please try again.");
                        }
                        timer = new CountDownTimer(300000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                Log.e("seconds remaining: ", "" + millisUntilFinished / 1000);

                                otp = SessionManager.getSignUpOtp(getActivity());
                                //here you can have your logic to set text to edittext
                            }

                            public void onFinish() {
                                otp = "";

                                Log.e("Time out: ", "Please try again send OTP");
                                 showAlert("Time out", "Time out. Click on resend OTP to get new OTP.");
                            }

                        }.start();

                    } else {
                        showAlert("Error", "An error occurred. Please try again.");
                    }
                }

            }

            @Override
            public void onFailure(Call<SignUpOtp> call, Throwable t) {
//                    isOtpSuccess = false;
                Bugsnag.notify(new RuntimeException(t));
                M.showToast(getContext(), "Failed to send OTP.");
                //Log.e("onFailure ===> ","" +call);

            }


        });
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

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog;
        dialog = builder.create();
        dialog.show();
    }
}
