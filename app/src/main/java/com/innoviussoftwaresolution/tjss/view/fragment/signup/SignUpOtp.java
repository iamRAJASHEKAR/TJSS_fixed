package com.innoviussoftwaresolution.tjss.view.fragment.signup;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 4/3/2018.
 */

public class SignUpOtp {

    @SerializedName("phone")
    String phone;

    @SerializedName("OTP")
    String OTP;

    @SerializedName("isd_code")
    String code;


    public SignUpOtp() {
    }

    public SignUpOtp(String phone, String OTP, String code) {
        this.phone = phone;
        this.OTP = OTP;
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
