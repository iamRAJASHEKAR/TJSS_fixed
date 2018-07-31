package com.twixttechnologies.tjss.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.innoviussoftwaresolution.tjss.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sony Raj on 07-08-17.
 */

public class LoginOrSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btnHomeSignUp, R.id.btnHomeSignIn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnHomeSignUp:
                startActivity(new Intent(this, SignUpActivity.class));
                finish();
                break;
            case R.id.btnHomeSignIn:
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                break;
        }
    }
}
