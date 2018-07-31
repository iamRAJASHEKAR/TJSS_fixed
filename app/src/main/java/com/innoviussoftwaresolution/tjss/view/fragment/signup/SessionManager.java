package com.innoviussoftwaresolution.tjss.view.fragment.signup;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bugsnag.android.Bugsnag;

public class SessionManager {


    private static final String PREF_NAME = "TJSS_APP_SESSION";
    private static final String PARAM_PHONE_NO = "phone";
    private static final String PARAM_OTP = "otp";

    public static void saveUserDetails(Context context, SignUpOtp vo) {
        try {
            SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            Log.e("Session ", " 1 " +vo.getPhone());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(PARAM_PHONE_NO, vo.getPhone());
            editor.putString(PARAM_OTP, vo.getOTP());

            editor.apply();
        } catch (Exception e) {
            Bugsnag.notify(new RuntimeException(e));
            // TODO: handle exception
            e.printStackTrace();
        }
    }

//    public static SignUpOtp getSignUpOtp(Context context) {
//        SignUpOtp getOtp = new SignUpOtp();
//        try {
//            SharedPreferences pref = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//            getOtp.setPhone(pref.getString(PARAM_PHONE_NO, ""));
//            getOtp.setOTP(pref.getString(PARAM_OTP, ""));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return getOtp;
//    }

    public static String getSignUpOtp(Context context) {
        String otpId = "";
        try {
            SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            otpId = preferences.getString(PARAM_OTP, "");
        } catch (Exception e) {
            Bugsnag.notify(new RuntimeException(e));
            // TODO: handle exception
            e.printStackTrace();
        }
        return otpId;
    }

    public static String getSignUpPhoneNo(Context context) {
        String phoneNo = "";
        try {
            SharedPreferences preferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            phoneNo = preferences.getString(PARAM_PHONE_NO, "");
        } catch (Exception e) {
            Bugsnag.notify(new RuntimeException(e));
            // TODO: handle exception
            e.printStackTrace();
        }
        return phoneNo;
    }

}
