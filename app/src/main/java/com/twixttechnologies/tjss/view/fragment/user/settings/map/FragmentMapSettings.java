package com.twixttechnologies.tjss.view.fragment.user.settings.map;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.MapOptionsAndCrime;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.utils.PreferenceHelper;
import com.twixttechnologies.tjss.view.fragment.user.settings.SettingsFragmentBase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 25-07-17.
 */

public class FragmentMapSettings extends SettingsFragmentBase {

    private static final String[] DURATIONS
            = {"Off", "1 Week", "1 Month", "3 Months", "6 Months", "12 Months"};
    @BindView(R.id.chkHospitals)
    AppCompatCheckBox mChkHospitals;
    @BindView(R.id.chkFireStations)
    AppCompatCheckBox mChkFireStations;
    @BindView(R.id.chkPoliceStations)
    AppCompatCheckBox mChkPoliceStations;
    @BindView(R.id.lblSelectedCrimeUpdateDuration)
    AppCompatTextView mLblSelectedCrimeUpdateDuration;
    Unbinder unbinder;
    private int mSelectedDurationIndex = -1;
    private int mLastSelectedIndex = -1;
    private Observer<MapOptionsAndCrime> mMapOptionsAndCrimeObserver
            = new Observer<MapOptionsAndCrime>() {
        @Override
        public void onChanged(@Nullable MapOptionsAndCrime mapOptionsAndCrime) {
            if (mapOptionsAndCrime == null) return;

            mChkHospitals.setChecked(mapOptionsAndCrime.mapoptions.contains("1"));
            mChkFireStations.setChecked(mapOptionsAndCrime.mapoptions.contains("2"));
            mChkPoliceStations.setChecked(mapOptionsAndCrime.mapoptions.contains("3"));
            try {
                mSelectedDurationIndex = Integer.parseInt(mapOptionsAndCrime.crime);
                mLblSelectedCrimeUpdateDuration.setText(DURATIONS[mSelectedDurationIndex]);
            } catch (Exception e) {
                M.log(e.getMessage());
            }
        }
    };

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            mViewModel.updateMapOptions(mChkHospitals.isChecked(),
                    mChkFireStations.isChecked(),
                    mChkPoliceStations.isChecked());
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.getMapOptionsData().observe(this, mMapOptionsAndCrimeObserver);
        mViewModel.populate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnCrimeUpdateDuration)
    public void onViewClicked() {
        mLastSelectedIndex = mSelectedDurationIndex;
        new AlertDialog.Builder(getActivity())
                .setTitle("Show crimes from")
                .setSingleChoiceItems(DURATIONS, mSelectedDurationIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which < 0) return;
                        mSelectedDurationIndex = which;
                    }
                })
                .setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mSelectedDurationIndex < 0) return;
                        mLblSelectedCrimeUpdateDuration.setText(DURATIONS[mSelectedDurationIndex]);
                        mViewModel.updateCrimeAndOffenders(String.valueOf(mSelectedDurationIndex));
                    }
                })
                .setCancelable(true)
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSelectedDurationIndex = mLastSelectedIndex;
                        if (mSelectedDurationIndex >= 0)
                            mLblSelectedCrimeUpdateDuration.setText(DURATIONS[mSelectedDurationIndex]);
                    }
                })
                .show();

    }

    @Override
    public void onStatusChanged() {

    }

    @Override
    protected void updateValue() {
        super.updateValue();
        String selected = "";
        if (mChkHospitals.isChecked()) {
            selected += "1";
        }

        if (mChkFireStations.isChecked()) {
            if (TextUtils.isEmpty(selected)) {
                selected += "2";
            } else {
                selected += ",2";
            }
        }

        if (mChkPoliceStations.isChecked()) {
            if (TextUtils.isEmpty(selected)) {
                selected += "3";
            } else {
                selected += ",3";
            }
        }
        PreferenceHelper.saveString(getActivity(), PreferenceHelper.KEY_MAP_OPTIONS, selected);

    }
}
