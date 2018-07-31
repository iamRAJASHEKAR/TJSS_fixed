package com.twixttechnologies.tjss.view.fragment.user.settings.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.utils.PreferenceHelper;
import com.twixttechnologies.tjss.view.adapter.helper.SeekBarProgressAdapter;
import com.twixttechnologies.tjss.view.fragment.user.settings.SettingsFragmentBase;

import java.text.MessageFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 25-07-17.
 */

public class FragmentLocationUpdates extends SettingsFragmentBase {

    @BindView(R.id.skLocationUpdateDuration)
    AppCompatSeekBar mSkLocationUpdateDuration;
    @BindView(R.id.lblLocationUpdateDuration)
    AppCompatTextView mLblLocationUpdateDuration;
    @BindView(R.id.btnUpdateLocationDuration)
    AppCompatButton mBtnUpdateLocationDuration;
    Unbinder unbinder;

    private SeekBarProgressAdapter mSeekBarProgressAdapter
            = new SeekBarProgressAdapter() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            super.onProgressChanged(seekBar, progress, fromUser);
            if (seekBar.getProgress() < 5) {
                seekBar.setProgress(5);
                return;
            }

            if (mLblLocationUpdateDuration != null)
                mLblLocationUpdateDuration.setText(MessageFormat.format("{0} Mins.", progress));

        }
    };

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_updates, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSkLocationUpdateDuration.setOnSeekBarChangeListener(mSeekBarProgressAdapter);

        int interval = PreferenceHelper.getInt(getActivity(), PreferenceHelper.KEY_UPDATE_INTERVAL, 5);
        mSkLocationUpdateDuration.setProgress(interval);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onStatusChanged() {
        mBtnUpdateLocationDuration.setEnabled(true);
    }

    @OnClick(R.id.btnUpdateLocationDuration)
    public void onViewClicked() {
        mBtnUpdateLocationDuration.setEnabled(false);
        mViewModel.updateLocationDuration(mSkLocationUpdateDuration.getProgress());
    }

    @Override
    protected void updateValue() {
        PreferenceHelper.saveInt(getActivity(), PreferenceHelper.KEY_UPDATE_INTERVAL, mSkLocationUpdateDuration.getProgress());
    }
}
