package com.innoviussoftwaresolution.tjss.view.fragment;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.view.activity.BaseActivity;
import com.innoviussoftwaresolution.tjss.view.fragment.generalalerts.PermissionsDetailListDialog;
import com.innoviussoftwaresolution.tjss.view.viewutils.Permissions;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author Sony Raj on 30/06/17.
 */

public abstract class BaseFragment extends Fragment {


    protected ProgressDialog mProgressDialog;
    private final Runnable mProgressDismissRunnable
            = new Runnable() {
        @Override
        public void run() {
            dismissDialog();
            onDialogDismissed();
        }
    };
    protected Observer<String> mErrorObserver;
    @Nullable
    @BindView(R.id.toolBar)
    protected Toolbar mToolbar;
    private Handler mHandler;

    protected void initProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage("Please wait...");
        }

        if (mHandler == null) mHandler = new Handler();

        mProgressDialog.show();

        mHandler.postDelayed(mProgressDismissRunnable, 30 * 1000);//30 seconds
    }

    protected void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    /**
     * This default implementation shows a toast,
     * Override this message to show other alert types
     */
    protected void initErrorObserver() {
        mErrorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                M.showToast(getActivity(), s);
                onDialogDismissed();
            }
        };
    }

    //Override this method to do anything after the dialog is dismissed
    //This method will be called only if the response is not received after 30 seconds
    protected void onDialogDismissed() {

    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    public abstract void onBaseCreate(Bundle SavedInstanceState);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


        //Perform any app specific initializations here

        onBaseCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mToolbar == null) return;
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity == null) return;
        activity.setSupportActionBar(mToolbar);
        activity.setHomeAsUp();
    }

    protected void disableUp()
    {

        BaseActivity activity = (BaseActivity) getActivity();
        if (activity == null) return;
        activity.hideHomeAsUp();
    }


    protected void setTitle(String title) {
        if (mToolbar == null) return;
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity == null) return;
        activity.setTitle(title);
    }


    protected boolean havePermission(String... permissions) {
        return Permissions.havePermissionFor(getActivity(), permissions) == null;
    }

    protected ArrayList<String> shouldShowPermissionReationale(String... permissions) {
        return Permissions.shouldShowPermissionRequestRationale(getActivity(), permissions);
    }

    protected void requestPermission(int permissionRequestCode, String... permissions) {
        Permissions.requestPermissionFromFragment(this, permissionRequestCode, permissions);
    }

    protected void showPermissionDetails(ArrayList<String> details,
                                         PermissionsDetailListDialog.PermissionsDialogCallback callback) {
        PermissionsDetailListDialog dialog = PermissionsDetailListDialog.newInstance(details);
        dialog.setCallback(callback);
        dialog.setCancelable(false);
        dialog.show(getChildFragmentManager(), "PermissionDetailsDialog");
    }


    protected void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();
        if (view != null)
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
    }

    protected void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();
        if (view != null)
            if (inputMethodManager != null)
                inputMethodManager.showSoftInput(view, 0);
    }



}
