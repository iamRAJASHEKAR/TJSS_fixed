package com.twixttechnologies.tjss.view.fragment.signin;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.activity.SignUpActivity;
import com.twixttechnologies.tjss.view.adapter.pageradapter.SignInPagerAdapter;
import com.twixttechnologies.tjss.view.fragment.signup.LoginSignUpFragmentBase;
import com.twixttechnologies.tjss.viewmodel.LoginViewModel;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 15-07-17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class FragmentSignIn extends LoginSignUpFragmentBase {

    public static final String TAG = "SignIn";
    @BindView(R.id.lblSignUpSignIn)
    AppCompatTextView mLblSignUpSignIn;

    @BindView(R.id.lblSignUpSignUp)
    AppCompatTextView mLblSignUpSignUp;

    @BindView(R.id.vpSignIn)
    ViewPager mVpSignIn;

    @BindView(R.id.btnNext)
    AppCompatButton mBtnNext;

    Unbinder unbinder;

    private LoginViewModel mViewModel;
    private SignInPagerAdapter signInPagerAdapter;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        super.onBaseCreate(SavedInstanceState);
    }

    @Override
    protected boolean canGoBack() {
        int index = mVpSignIn.getCurrentItem();
        if (index > 0)
            mVpSignIn.setCurrentItem(index - 1);
        return index == 0;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        disableUp();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signInPagerAdapter = new SignInPagerAdapter(getChildFragmentManager());
        mVpSignIn.setAdapter(signInPagerAdapter);

        mViewModel = ViewModelProviders.of(this, new LoginViewModel.LoginViewModelFactory(getActivity().getApplication()))
                .get(LoginViewModel.class);

        mViewModel.getSignUpResponseLiveData().observe(this, mSignUpResponseObserver);
        mViewModel.getError().observe(this, mErrorObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.lblSignUpSignUp)
    public void onMLblSignUpSignUpClicked() {
        Intent signUpIntent = new Intent(getActivity(), SignUpActivity.class);
        signUpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(signUpIntent);
        getActivity().finishAffinity();
    }

    @OnClick(R.id.btnNext)
    public void onMBtnNextClicked() {
        if (mVpSignIn.getCurrentItem() == 0) {
            mVpSignIn.setCurrentItem(1, true);
        } else if (mVpSignIn.getCurrentItem() == mVpSignIn.getAdapter().getCount() - 1) {

            if (signInPagerAdapter == null) {
                //just to be on the safe side
                Toast.makeText(getActivity(), "An Error occurred Please try again later", Toast.LENGTH_SHORT).show();
                return;
            }

            int index = signInPagerAdapter.checkHasValidInput();
            //int code = signInPagerAdapter.checkPassword();
            if (index < 0) {
                hideKeyboard();
                String phone = signInPagerAdapter.getPhone();
                String isdCode = signInPagerAdapter.getIsdCode();
                String password = signInPagerAdapter.getPassword();

                if (phone.contains("@")) {
                    isdCode = "";
                }
                //Toast.makeText(getActivity(), phone + " " + password, Toast.LENGTH_LONG).show();
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setMessage("Logging in...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                mViewModel.login(MessageFormat.format( "{0} {1}",isdCode.trim(), phone.toString().trim()), password);

            } else {
                mVpSignIn.setCurrentItem(index, true);
                if (index == 0) {
                    showErrorToast("Enter phone/email");
                    mVpSignIn.setCurrentItem(0, true);

                } else if (index == 2) {
                    showErrorToast("Enter valid phone");
                    mVpSignIn.setCurrentItem(0, true);
                } else if (index == 3) {
                    showErrorToast("Enter valid Email");
                    mVpSignIn.setCurrentItem(0, true);
                }


            }


        }
    }

    private void showErrorToast(String message) {
        Toast t = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        t.setGravity(Gravity.TOP, 0, 200);
        t.show();
    }


    @Override
    public void showNext() {
        try {
            int index = mVpSignIn.getCurrentItem();
            if (index == 0) {
                String phone = signInPagerAdapter.getPhone();
                String isdCode = signInPagerAdapter.getIsdCode();
                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(isdCode)) {
                    showErrorToast("Isd Code and phone number are mandatory");
                    return;
                }
            } else if (index == 1) {
                String password = signInPagerAdapter.getPassword();
                if (TextUtils.isEmpty(password)) {
                    showErrorToast("Password required");
                    return;
                }
            }


            if (index < 1)
                mVpSignIn.setCurrentItem(index + 1);
            else if (index == 1)
                onMBtnNextClicked();
        } catch (Exception e) {
            M.log(e.getMessage());
        }
    }
}
