package com.innoviussoftwaresolution.tjss.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;

import com.bugsnag.android.Bugsnag;

/**
 * @author Sony Raj on 30/06/17.
 *         <p>
 *         <p>
 *         Activities that don't have toolbar should implement this class
 */

public abstract class BaseActivity extends AppCompatActivity {

    public void setHomeAsUp() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void hideHomeAsUp() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    abstract void onBaseCreate(Bundle savedInstanceState);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Perform any app specific checks or initializations here

        onBaseCreate(savedInstanceState);
        Bugsnag.init(this);
        try {
            // Some potentially crashy code
        } catch (Throwable exception) {
            Bugsnag.notify(exception);
        }
      /*  Bugsnag.notify(new RuntimeException("Test error"));*/
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
}
