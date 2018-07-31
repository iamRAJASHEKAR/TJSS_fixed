package com.twixttechnologies.tjss.view.fragment.user.settings.alert;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.GeneralAlerts;
import com.twixttechnologies.tjss.model.network.response.SafetyCircle;
import com.twixttechnologies.tjss.view.adapter.listadapter.SafetyCircleListAdapterForSettings;
import com.twixttechnologies.tjss.view.fragment.user.settings.SettingsFragmentBase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 25-07-17.
 */

public class FragmentAlertOptionsSettings extends SettingsFragmentBase {

    @BindView(R.id.lstSafetyCircles)
    RecyclerView mLstSafetyCircles;
    Unbinder unbinder;
    @BindView(R.id.chkCrimeInMyArea)
    AppCompatCheckBox mChkCrimeInMyArea;
    @BindView(R.id.chkAlertSound)
    AppCompatCheckBox mChkAlertSound;
    private Observer<List<SafetyCircle>> mSafetyCirclesObserver
            = new Observer<List<SafetyCircle>>() {
        @Override
        public void onChanged(@Nullable List<SafetyCircle> safetyCircles) {
            if (safetyCircles == null || safetyCircles.size() <= 0) return;

            SafetyCircleListAdapterForSettings adapterForSettings
                    = new SafetyCircleListAdapterForSettings(safetyCircles, getActivity());

            mLstSafetyCircles.setAdapter(adapterForSettings);
            mLstSafetyCircles.setLayoutManager(new LinearLayoutManager(getActivity()));
            mLstSafetyCircles.addItemDecoration(new DividerItemDecoration(getActivity(),
                    LinearLayoutManager.VERTICAL));
        }
    };

    private boolean ignoreAlert = false;
    private boolean ignoreCrime = false;
    private Observer<GeneralAlerts> mGeneralAlertsObserver
            = new Observer<GeneralAlerts>() {
        @Override
        public void onChanged(@Nullable GeneralAlerts generalAlerts) {
            if (generalAlerts == null) return;
            if (generalAlerts.generalAlerts == null) return;
            if (generalAlerts.generalAlerts.equals("")) {
                mChkAlertSound.setChecked(false);
                mChkCrimeInMyArea.setChecked(false);
            } else {
                if (generalAlerts.generalAlerts.contains("1")) {
                    ignoreCrime = true;
                    mChkCrimeInMyArea.setChecked(true);
                }
                if (generalAlerts.generalAlerts.contains("2")) {
                    ignoreAlert = true;
                    mChkAlertSound.setChecked(true);
                }
            }
        }
    };

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert_options_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.getSafetyCircles();
        mViewModel.getSafetyCirclesData().observe(this, mSafetyCirclesObserver);
        mViewModel.getGeneralAlertsData().observe(this, mGeneralAlertsObserver);
        mViewModel.populateGeneralAlerts();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStatusChanged() {
        dismissDialog();
    }

    @OnCheckedChanged(R.id.chkCrimeInMyArea)
    public void onCrimeInMyAreaChanged() {
        if (ignoreCrime) {
            ignoreCrime = false;
            return;
        }
        update();
    }


    @OnCheckedChanged(R.id.chkAlertSound)
    public void onAlertSoundChanged() {
        if (ignoreAlert) {
            ignoreAlert = false;
            return;
        }
        update();
    }

    private void update() {
        String options = "";
        if (mChkCrimeInMyArea.isChecked()) {
            options += "1";
        }

        if (mChkAlertSound.isChecked()) {
            if (options.length() <= 0) {
                options += "2";
            } else {
                options += ",2";
            }
        }

        if (mViewModel == null) return;
        mViewModel.updateGeneralAlerts(options.trim());
    }
}
