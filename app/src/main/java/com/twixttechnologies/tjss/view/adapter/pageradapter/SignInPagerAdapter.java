package com.twixttechnologies.tjss.view.adapter.pageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Patterns;

import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.view.fragment.signin.FragmentSignInPassword;
import com.twixttechnologies.tjss.view.fragment.signin.FragmentSignInPhone;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @author Sony Raj on 15-07-17.
 */

public class SignInPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<BaseFragment> mFragments = new ArrayList<>(2);

    public SignInPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments.add(new FragmentSignInPhone());
        mFragments.add(new FragmentSignInPassword());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }


    public String getIsdCode() {
        return getFragmentSignInPhone().getIsdCode() == null ?
                null : getFragmentSignInPhone().getIsdCode().isdCode;
    }

    public String getPhone() {
        return getFragmentSignInPhone().getPhone();
    }

    public String getPassword() {
        return getFragmentSignInPassword().getPassword();
    }

    private FragmentSignInPhone getFragmentSignInPhone() {
        return (FragmentSignInPhone) mFragments.get(0);
    }

    private FragmentSignInPassword getFragmentSignInPassword() {
        return (FragmentSignInPassword) mFragments.get(1);
    }

//    public int checkHasValidInput() {
//        int valid = -1;
//        if (getPhone() == null || getPhone().equals("")) {
//            valid = 0;
//        }
//        if ((getPhone() != null || !getPhone().equals(""))) {
//            if (getPhone().matches("[0-9]+")) {
//                if (getPhone().length() < 6 || getPhone().length() > 13) {
//
//                    valid = 2;
//                }
//            } else if (!Patterns.EMAIL_ADDRESS.matcher(getPhone()).matches()) {
//                valid = 3;
//            }
//
//        }
//        if (getIsdCode() == null || getIsdCode().equals("")) {
//            valid = 0;
//        }
////        if (getPasswordFromFragment() == null || getPasswordFromFragment().equals("")) {
////            valid = 1;
////        }
//        return valid;
//    }

    public int checkHasValidInput() {
        int valid = -1;
        if (getPhone() == null || getPhone().equals(""))
        {
            valid = 0;
        }
        else if (getIsdCode() == null || getIsdCode().equals(""))
        {
            valid = 0;
        }
        else if (getPassword() == null || getPassword().equals(""))
        {
            valid = 1;
        }
        else {
            if ((getPhone() != null || !getPhone().equals("")))
            {
                if (getPhone().matches("[0-9]+"))
                {
                    if (getPhone().length() < 6 || getPhone().length() > 13)
                    {
                        valid = 2;
                    }
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(getPhone()).matches()) {
                    valid = 3;
                }

            }

        }

        return valid;
    }
//    public int checkPassword() {
//        int code = -1;
//
//        if (getPasswordFromFragment() == null || getPasswordFromFragment().equals("")) {
//            code = 1;
//        }
//
//        return code;
//    }

}
