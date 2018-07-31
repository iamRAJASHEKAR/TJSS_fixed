package com.innoviussoftwaresolution.tjss.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.innoviussoftwaresolution.tjss.TjssApplication;
import com.innoviussoftwaresolution.tjss.utils.LocationUtil2;

/**
 * @author Sony Raj on 21/11/17.
 */

public abstract class LocationCustomerFragmentBase extends BaseFragment {

    private LocationUtil2 mLocationUtil;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mLocationUtil = ((TjssApplication) getActivity().getApplication()).getLocationUtil();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != LocationUtil2.REQUEST_CHECK_SETTINGS) return;
        if (resultCode != Activity.RESULT_OK) return;
        onSettingsActivityResult(data);

    }

    protected void checkSettings() {
        mLocationUtil.checkForSufficientSettings(getActivity());
    }



    protected abstract void onSettingsActivityResult(Intent data);


}
