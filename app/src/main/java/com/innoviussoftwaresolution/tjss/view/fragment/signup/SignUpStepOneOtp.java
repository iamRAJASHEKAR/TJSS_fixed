package com.innoviussoftwaresolution.tjss.view.fragment.signup;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bugsnag.android.Bugsnag;
import com.chaos.view.PinView;
import com.innoviussoftwaresolution.tjss.APIcalls.APIClient;
import com.innoviussoftwaresolution.tjss.APIcalls.EndpointsServices;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.utils.M;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Sony Raj on 07-07-17.
 */

public class SignUpStepOneOtp extends SignUpFragmentBase {


    @BindView(R.id.txtOTPCode)
    PinView txtOTPCode;

    Unbinder unbinder;

    private CountDownTimer timer;
    private String strTxtOTPCode;

    @BindView(R.id.tvCount)
    TextView tvCount;

    @BindView(R.id.tvResend)
    TextView tvResend;

    public static String resendOtp = null;

    public String getSecurityCode() {
        return txtOTPCode != null ? txtOTPCode.getText().toString() : strTxtOTPCode;
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_step_one_otp, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhoneNoGetOtp();
//                timer = new CountDownTimer(300000, 1000) {
//
//                    public void onTick(long millisUntilFinished) {
//                        Log.e("seconds remaining: ", "" + millisUntilFinished / 1000);
//
//                        otp = SessionManager.getSignUpOtp(getActivity());
//                        //here you can have your logic to set text to edittext
//                    }
//
//                    public void onFinish() {
//                        otp = "";
//                        Log.e("Time out: ", "Please try again send OTP");
//                        // showAlert("Time out", "Time out. Click on resend OTP to get new OTP.");
//                    }
//
//                }.start();


            }
        });
        return view;
    }

    String otp;

    private void callPhoneNoGetOtp() {

//        String otpPin = mPagerAdapter.getOtpCode();
        String phoneNo = SessionManager.getSignUpPhoneNo(getActivity());
//        String phoneNo = mPagerAdapter.getPhone();

        SharedPreferences preferences = getActivity().getSharedPreferences("isdCode", 0);

        final SignUpOtp signUpOtp = new SignUpOtp();
        signUpOtp.setPhone(phoneNo);
        signUpOtp.setCode(preferences.getString("code", ""));
        preferences.getString("code", "");
        M.showToast(getContext(), signUpOtp.getCode());
        Log.d("isdCode", signUpOtp.getCode());
//        SignUpOtp responceSignUpOtp = new SignUpOtp();
//        responceSignUpOtp.setOTP("123456");
//        responceSignUpOtp.setPhone("1234567890");
//        Log.e("Responce==>", "Otp==>" + responceSignUpOtp);
//        SessionManager.saveUserDetails(getActivity(), responceSignUpOtp);
//


        EndpointsServices apiInterFace = APIClient.getClient().create(EndpointsServices.class);
        Call<SignUpOtp> call = apiInterFace.signUpOtp(signUpOtp);

        call.enqueue(new Callback<SignUpOtp>() {
            @Override
            public void onResponse(Call<SignUpOtp> call, Response<SignUpOtp> response) {

                if (response != null) {
                    if (response.body() != null) {
                        SignUpOtp responceSignUpOtp = new SignUpOtp();
                        responceSignUpOtp.setOTP(response.body().getOTP());
                        responceSignUpOtp.setPhone(response.body().getPhone());
                        Log.e("Responce==>", "Otp==>" + responceSignUpOtp);
                        SessionManager.saveUserDetails(getActivity(), responceSignUpOtp);

                        resendOtp = SessionManager.getSignUpOtp(getActivity());
                        M.showToast(getContext(), "OTP successfully sent");
                    }
                } else {
                    showAlert("Error", "An error occurred.Please try again");
                }
                Log.e("Otp response", "=====>" + response);

            }

            @Override
            public void onFailure(Call<SignUpOtp> call, Throwable t) {
//                    isOtpSuccess = false;
                Bugsnag.notify(new RuntimeException(t));
                M.showToast(getContext(), "Failed to send OTP. Try again latter");
                //Log.e("onFailure ===> ","" +call);

            }


        });


        timer = new CountDownTimer(300000, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.e("seconds remaining: ", "" + millisUntilFinished / 1000);

                otp = SessionManager.getSignUpOtp(getActivity());
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                otp = "";
                // showAlert("Time out","Tap on resend OTP to get new OTP.");
                Log.e("Time out: ", "Please resend otp");
            }

        }.start();

    }

    @Override
    void setDataOnPause() {
        //if (mTxtSecurityCode != null)
        //mSecurityCode = mTxtSecurityCode.getSecurityCode();
    }

    @Override
    void setDataOnResume() {
        //if (mSecurityCode != null)
        //if (mTxtSecurityCode != null)
        //mTxtSecurityCode.setSecurityCode(mSecurityCode);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
