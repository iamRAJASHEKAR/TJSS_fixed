package com.innoviussoftwaresolution.tjss.view.fragment.signup;

import com.google.gson.annotations.SerializedName;

public class OTPresponseModel {

    @SerializedName("phone")
    String phone;

    @SerializedName("OTP")
    String OTP;

    public OTPresponseModel() {
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
}
