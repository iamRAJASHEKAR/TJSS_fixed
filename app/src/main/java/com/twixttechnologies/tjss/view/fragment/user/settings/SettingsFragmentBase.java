package com.twixttechnologies.tjss.view.fragment.user.settings;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.activity.SettingsActivity;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.viewmodel.SettingsViewModel;

/**
 * @author Sony Raj on 24-08-17.
 */

public abstract class SettingsFragmentBase extends BaseFragment {

    protected SettingsViewModel mViewModel;

    protected Observer<StatusMessage> mStatusMessageObserver
            = new Observer<StatusMessage>() {
        @Override
        public void onChanged(@Nullable StatusMessage statusMessage) {
            onStatusChanged();
            if (statusMessage != null || statusMessage.status.equalsIgnoreCase("success"))
            {
                Intent intent=new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
            else
            {

            }
            M.showToast(getActivity(), statusMessage.status);
            updateValue();
        }
    };


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new SettingsViewModel.SettingsViewModelFactory(getActivity().getApplication()))
                .get(SettingsViewModel.class);
        mViewModel.getStatusMessageData().observe(this, mStatusMessageObserver);
    }

    public abstract void onStatusChanged();

    protected void updateValue() {

    }


}
