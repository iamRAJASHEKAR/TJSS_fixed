package com.innoviussoftwaresolution.tjss.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.innoviussoftwaresolution.tjss.broadcastreceiver.BatteryStatusReceiver;
import com.innoviussoftwaresolution.tjss.utils.M;

public class BatteryStatusMonitorService extends Service {


    BatteryStatusReceiver batteryStatusReceiver;

    public BatteryStatusMonitorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        batteryStatusReceiver = new BatteryStatusReceiver();
        registerReceiver(batteryStatusReceiver, intentFilter);
        M.log("Battery status monitoring started");
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryStatusReceiver);
    }
}


