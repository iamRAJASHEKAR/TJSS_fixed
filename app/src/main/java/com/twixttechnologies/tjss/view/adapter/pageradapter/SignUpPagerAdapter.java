package com.twixttechnologies.tjss.view.adapter.pageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Patterns;
import android.widget.Toast;

import com.twixttechnologies.tjss.model.network.response.Log;
import com.twixttechnologies.tjss.model.network.response.Plan;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.view.fragment.signup.SignUpStepFiveYourCircle;
import com.twixttechnologies.tjss.view.fragment.signup.SignUpStepFourYourProfile;
import com.twixttechnologies.tjss.view.fragment.signup.SignUpStepOnePhone;
import com.twixttechnologies.tjss.view.fragment.signup.SignUpStepSixSelectPlan;
import com.twixttechnologies.tjss.view.fragment.signup.SignUpStepThreeSecureYourAccount;
import com.twixttechnologies.tjss.view.fragment.signup.SignUpStepTwoPassword;

import java.util.ArrayList;

/**
 * @author Sony Raj on 07-07-17.
 */

public class SignUpPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<BaseFragment> mFragments = new ArrayList<>();


    public SignUpPagerAdapter(FragmentManager fm) {
        super(fm);
        addFragments();
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    private void addFragments() {
        mFragments.add(new SignUpStepOnePhone());
        mFragments.add(new SignUpStepTwoPassword());
        mFragments.add(new SignUpStepThreeSecureYourAccount());
        mFragments.add(new SignUpStepFourYourProfile());
        mFragments.add(new SignUpStepFiveYourCircle());
        //mFragments.add(new SignUpStepSixSelectPlan());
    }

    public int checkHasValidInput() {
        String isdCode = getIsdCode();
        String phone = getPhone();

        if (isdCode.equals("") || phone == null || phone.equals("")) {
            return 0;
        }

        String password = getPassword();

        if (password == null || password.equals("") || password.length() < 6) {
            return 1;
        }

        String email = getEmail();

        if (email == null || email.equals("")) {
            return 2;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return 2;
        }

        String image = getImage();
        String name = getName();

        if (image == null || image.equals("") ||
                name == null || name.equals("")) {
            return 3;
        }

        /*String securityCode = getInviteCode();
        if (securityCode == null || securityCode.equals("")) {
            return 4;
        }*/
        return -1;
    }

    public boolean checkPlan() {
        Plan selectedPlan = getPlan();
        if (selectedPlan == null || selectedPlan.id == null || selectedPlan.id.equals("")) {
            return true;
        }
        return false;
    }

    public String getPhone() {
        return getSignUpStepOnePhone().getPhoneNumber();
    }

    public String getIsdCode() {
        return getSignUpStepOnePhone().getIsdCode();
    }

    private SignUpStepOnePhone getSignUpStepOnePhone() {
        return (SignUpStepOnePhone) mFragments.get(0);
    }

    public String getPassword() {
        SignUpStepTwoPassword two = (SignUpStepTwoPassword) mFragments.get(1);

        return two.getPasswordFromFragment();
    }

    public String getEmail() {
        SignUpStepThreeSecureYourAccount three = (SignUpStepThreeSecureYourAccount) mFragments.get(2);
        return three.getEmail();
    }

    public String getName() {
        return getSignUpStepFourYourProfile().getName();
    }

    public String getImage() {
        return getSignUpStepFourYourProfile().getImage();
    }

    private SignUpStepFourYourProfile getSignUpStepFourYourProfile() {
        return (SignUpStepFourYourProfile) mFragments.get(3);
    }

    public Plan getPlan() {
        SignUpStepSixSelectPlan six = (SignUpStepSixSelectPlan) mFragments.get(5);
        return six.getSelectedPlan();
    }

    public String getInviteCode() {
        SignUpStepFiveYourCircle five = (SignUpStepFiveYourCircle) mFragments.get(4);
        return five.getSecurityCode();
    }


}
