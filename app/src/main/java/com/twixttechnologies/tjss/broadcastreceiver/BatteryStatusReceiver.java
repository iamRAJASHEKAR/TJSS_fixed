package com.twixttechnologies.tjss.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.repository.TjssRepository;
import com.twixttechnologies.tjss.utils.PreferenceHelper;

import java.util.HashMap;

/**
 * @author Sony Raj on 11-08-17.
 */
public class BatteryStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean isLoggedIn = !PreferenceHelper.getString(context, PreferenceHelper.KEY_USER_ID, "").equals("");
        if (!isLoggedIn) return;
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;
        boolean isDischarging = status == BatteryManager.BATTERY_STATUS_DISCHARGING;

        int chargingType = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean isUsb = chargingType == BatteryManager.BATTERY_PLUGGED_USB;
        boolean isAc = chargingType == BatteryManager.BATTERY_PLUGGED_AC;

        int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float currentBatteryLevel = (batteryLevel / (float) scale) * 100;
        float lastUpdate = PreferenceHelper.getFloat(context, PreferenceHelper.LAST_UPDATED_LEVEL, 0);

        if (Math.abs(lastUpdate - currentBatteryLevel) > .05)
        {
            String url = context.getString(R.string.update_battery_path);
            HashMap<String, String> headerMap = new HashMap<>(2);
            headerMap.put("api_token", PreferenceHelper.getString(context, PreferenceHelper.KEY_ACCESS_TOKEN, ""));
            headerMap.put("userid", PreferenceHelper.getString(context, PreferenceHelper.KEY_USER_ID, ""));

            HashMap<String, String> params = new HashMap<>(2);
            params.put("userId", PreferenceHelper.getString(context, PreferenceHelper.KEY_USER_ID, ""));
            params.put("battery", String.valueOf(currentBatteryLevel));

            new TjssRepository().updateBattery(url, headerMap, params);

            PreferenceHelper.saveFloat(context, PreferenceHelper.LAST_UPDATED_LEVEL, currentBatteryLevel);
        }

    }
}