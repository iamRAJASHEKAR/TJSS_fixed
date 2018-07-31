package com.innoviussoftwaresolution.tjss.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.innoviussoftwaresolution.tjss.TjssApplication;
import com.innoviussoftwaresolution.tjss.service.BatteryStatusMonitorService;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;

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
