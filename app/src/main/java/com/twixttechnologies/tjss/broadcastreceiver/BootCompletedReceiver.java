package com.twixttechnologies.tjss.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.twixttechnologies.tjss.TjssApplication;
import com.twixttechnologies.tjss.service.BatteryStatusMonitorService;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.utils.PreferenceHelper;

public class BootCompletedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        M.log("Starting battery status monitor");
        boolean isLoggedIn = !PreferenceHelper.getString(context, PreferenceHelper.KEY_USER_ID, "").equals("");
        if (!isLoggedIn) return;
        context.startService(new Intent(context, BatteryStatusMonitorService.class));
        ((TjssApplication) context.getApplicationContext()).startLocationUpdates();
    }
}
