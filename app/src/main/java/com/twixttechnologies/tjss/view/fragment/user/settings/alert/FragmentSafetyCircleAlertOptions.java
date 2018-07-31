package com.twixttechnologies.tjss.view.fragment.user.settings.alert;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
//import com.twixttechnologies.tjss.R;
import com.twixttechnologies.tjss.model.network.response.CircleAlertsData;
import com.twixttechnologies.tjss.model.network.response.SafetyCircle;
import com.twixttechnologies.tjss.view.activity.SafetyCircleAlertOptionActivity;
import com.twixttechnologies.tjss.view.fragment.user.settings.SettingsFragmentBase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 25-07-17.
 */

public class FragmentSafetyCircleAlertOptions extends SettingsFragmentBase {

    public static final String TAG = "FragmentSafetyCircleAlertOptions";
    @BindView(R.id.chkEmail)
    AppCompatCheckBox mChkEmail;
    @BindView(R.id.chkNotifications)
    AppCompatCheckBox mChkNotifications;
    @BindView(R.id.chkSms)
    AppCompatCheckBox mChkSms;
    private final Observer<CircleAlertsData> mCircleAlertsDataObserver
            = new Observer<CircleAlertsData>() {
        @Override
        public void onChanged(@Nullable CircleAlertsData circleAlertsData) {
            if (circleAlertsData == null) return;
            if (circleAlertsData.circleAlerts == null) return;
            mChkEmail.setChecked(circleAlertsData.circleAlerts.contains("1"));
            mChkNotifications.setChecked(circleAlertsData.circleAlerts.contains("2"));
            mChkSms.setChecked(circleAlertsData.circleAlerts.contains("3"));
        }
    };
    Unbinder unbinder;
    private SafetyCircle mSafetyCircle;

    public static FragmentSafetyCircleAlertOptions newInstance(SafetyCircle safetyCircle) {

        Bundle args = new Bundle();
        args.putParcelable(SafetyCircleAlertOptionActivity.SAFETY_CIRCLE, safetyCircle);
        FragmentSafetyCircleAlertOptions fragment = new FragmentSafetyCircleAlertOptions();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        Bundle args = getArguments();
        mSafetyCircle = args.getParcelable(SafetyCircleAlertOptionActivity.SAFETY_CIRCLE);
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
            initProgress();
            mViewModel.updateAlertOptionsforCircle(mSafetyCircle.circleId,
                    mChkEmail.isChecked(),
                    mChkNotifications.isChecked(),
                    mChkSms.isChecked());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(mSafetyCircle.circleName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_safety_circle_alert_options, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.getCircleAlertsData().observe(this, mCircleAlertsDataObserver);
        if (mSafetyCircle != null)
            mViewModel.getCircleAlertsData(mSafetyCircle);
    }

    @Override
    public void onStatusChanged() {
        dismissDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
