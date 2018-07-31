package com.innoviussoftwaresolution.tjss.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.CheckStatusRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.CheckStatusResponseModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.NearByServiceProviderRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.ProviderList;
import com.innoviussoftwaresolution.tjss.model.repository.HelpAlertRepository;
import com.innoviussoftwaresolution.tjss.view.fragment.serviceprovider.FragmentServiceProviders;

import java.util.ArrayList;

/**
 * @author Sony Raj on 25-09-17.
 */

public class ServiceProvidersListingActivity extends BaseActivityWithToolbar {

    private String status, userId;
    private Intent intent;
    private NearByServiceProviderRequestModel model;
    private ArrayList<ProviderList> nearByProviderList, selectedProviderList;
    private SharedPreferences preferences;
    private HelpAlertRepository mHelpAlertRepository;
    private SharedPreferences.Editor editor;
    CheckStatusRequestModel requestModel;
    CheckStatusResponseModel responseModel;
    private String providerId;


    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FragmentServiceProviders.TAG);
        Bugsnag.init(this);
        //   Bugsnag.notify(new RuntimeException("Test error"));
        if (fragment == null)
            fragment = new FragmentServiceProviders();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentServiceProviders.TAG)
                .commit();


    }


}
