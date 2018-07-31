package com.twixttechnologies.tjss.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.Incident;
import com.twixttechnologies.tjss.view.fragment.incident.FragmentIncidentDetails;

/**
 * @author Sony Raj on 31-10-17.
 */

public class IncidentDetailActivity extends BaseActivityWithToolbar {


    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Incident incident = getIntent().getParcelableExtra(FragmentIncidentDetails.INCIDENT_DETAIL);


        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(FragmentIncidentDetails.TAG);

        if (fragment == null)
            fragment = FragmentIncidentDetails.newInstance(incident);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, fragment, FragmentIncidentDetails.TAG)
                .commit();

    }
}
