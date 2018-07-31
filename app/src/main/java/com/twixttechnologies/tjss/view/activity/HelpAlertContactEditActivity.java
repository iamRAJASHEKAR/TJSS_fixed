package com.twixttechnologies.tjss.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleContact;
import com.twixttechnologies.tjss.view.fragment.helpalert.FragmentEditContact;

/**
 * @author Sony Raj on 13/11/17.
 */

public class HelpAlertContactEditActivity extends BaseActivityWithToolbar {

    public static final String HELP_ALERT_CONTACT = "helpAlertContact";

    @Override
    void onBaseCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        Intent callingIntent = getIntent();
        SafetyCircleContact contact = callingIntent.getParcelableExtra(HELP_ALERT_CONTACT);

        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, FragmentEditContact.newInstance(contact), FragmentEditContact.TAG)
                    .commit();

    }
}
