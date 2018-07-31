package com.twixttechnologies.tjss.view.fragment.user.settings.serviceprovider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.twixttechnologies.tjss.view.fragment.BaseFragment;

/**
 * @author Sony Raj on 21-10-17.
 */

abstract class ServiceProviderSelectionFrgamentBase extends BaseFragment {


    static final String CATEGORY_CHANGE_ACTION = "com.twixttechnologies.tjss.view.fragment.serviceprovider.pager.CATEGORY_CHANGE";
    static final String SUB_CATEGORY_CHANGE_ACTION = "com.twixttechnologies.tjss.view.fragment.serviceprovider.pager.SUB_CATEGORY_CHANGE";
    static final String SERVICE_PROVIDER_SELECTED_ACTION = "com.twixttechnologies.tjss.view.fragment.serviceprovider.pager.SERVICE_PROVIDER_SELECTED";
    private SelectionChangedReceiver mSelectionChangeReceiver;

    abstract void onSelectionChanged(Intent intent);

    @Override
    public void onResume() {
        super.onResume();
        if (mSelectionChangeReceiver == null)
            mSelectionChangeReceiver = new SelectionChangedReceiver();
        IntentFilter intentFilter = new IntentFilter(CATEGORY_CHANGE_ACTION);
        intentFilter.addAction(SUB_CATEGORY_CHANGE_ACTION);
        intentFilter.addAction(SERVICE_PROVIDER_SELECTED_ACTION);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mSelectionChangeReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSelectionChangeReceiver != null)
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mSelectionChangeReceiver);
    }

    private class SelectionChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.getAction().equals(CATEGORY_CHANGE_ACTION) &&
                    !intent.getAction().equals(SUB_CATEGORY_CHANGE_ACTION) &&
                    !intent.getAction().equals(SERVICE_PROVIDER_SELECTED_ACTION))
                return;
            onSelectionChanged(intent);
        }
    }


}
