package com.innoviussoftwaresolution.tjss;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.multidex.MultiDex;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.SelectedSecurityQuestionResponse;
import com.innoviussoftwaresolution.tjss.model.repository.SecurityQuestionsRepository;
import com.innoviussoftwaresolution.tjss.service.BatteryStatusMonitorService;
import com.innoviussoftwaresolution.tjss.service.ChatListenerService;
import com.innoviussoftwaresolution.tjss.service.LocationUpdateServiceNew;
import com.innoviussoftwaresolution.tjss.service.TravelSpeedMonitorService;
import com.innoviussoftwaresolution.tjss.utils.LocationUtil2;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;
import com.innoviussoftwaresolution.tjss.view.viewutils.Permissions;

import java.util.HashMap;
import java.util.List;

import io.fabric.sdk.android.Fabric;

/**
 * @author Sony Raj on 05-08-17.
 */

public class TjssApplication extends Application {

    private static final String FIREBASE_JOB_ID = "fireBaseJobId";

    private Intent locationUpdateIntent;

    private RequestManager mGlide;

    private LocationUtil2 mLocationUtil;

    private String mCurrentRoom ="";

    public String getCurrentRoom() {
        return mCurrentRoom;
    }

    public void setCurrentRoom(String currentRoom) {
        mCurrentRoom = currentRoom;
    }

    private Location mPreviousLocation = null;
    private long mLastUpdateTime = 0L;

    public Location getPreviousLocation() {
        return mPreviousLocation;
    }

    public void setPreviousLocation(Location mPreviousLocation) {
        this.mPreviousLocation = mPreviousLocation;
    }

    public long getLastUpdateTime() {
        return mLastUpdateTime;
    }

    public void setLastUpdateTime(long mLastUpdateTime) {
        this.mLastUpdateTime = mLastUpdateTime;
    }

    private void initLocation() {
        if (mLocationUtil == null) {
            mLocationUtil = new LocationUtil2(this);
        }
    }

    public LocationUtil2 getLocationUtil() {
        return mLocationUtil;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        initLocation();
        Fabric.with(this, new Crashlytics());
        String userId = PreferenceHelper.getString(this, PreferenceHelper.KEY_USER_ID, "");
        mGlide = Glide.with(this);
        FirebaseApp.initializeApp(this);
        String token = FirebaseInstanceId.getInstance().getToken();
        if (token != null)
            M.log("Firebase Token", token);

        if (Permissions.havePermissionFor(this, Permissions.FINE_LOCATION) != null) return;
        startService(new Intent(this, TravelSpeedMonitorService.class));
        if (userId.equals("")) return;
        Intent intent = new Intent(this, ChatListenerService.class);
        startService(intent);
        checkHasSecurityQuestion(userId);
        /*M.log("Starting job Scheduler");
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        dispatcher.cancelAll();
        Bundle b = new Bundle();
        b.putString("userId", userId);
        Job job = dispatcher.newJobBuilder()
                .setReplaceCurrent(true)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setExtras(b)
                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setService(LocationUpdateService.class)
                .setTag(FIREBASE_JOB_ID)
                .setTrigger(Trigger.executionWindow(0, 60 * 2)) //0 to 2 minutes from now
                .build();
        dispatcher.mustSchedule(job);*/

        checkAndStartService();
        startLocationUpdates();
    }

    private void checkAndStartService() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        boolean found = false;
        for (ActivityManager.RunningServiceInfo runningService : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (runningService.getClass().getName().equals(BatteryStatusMonitorService.class.getName())) {
                found = true;
                break;
            }
        }

        if (!found)
            startBatteryMonitor();

    }

    public void startLocationUpdates() {
        M.log("Starting location updates");
        locationUpdateIntent = new Intent(this, LocationUpdateServiceNew.class);
        startService(locationUpdateIntent);
    }

    public void stopLocationUpdates() {
        M.log("Stopping location updates");
        if (locationUpdateIntent != null)
            stopService(locationUpdateIntent);
    }

    public RequestManager getGlide() {
        return mGlide;
    }


    @SuppressWarnings("unused")
    public boolean isAppIsInForeground() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = activityManager.getRunningAppProcesses();

        return runningApps.get(0).processName.equals(getPackageName());
    }


    public void startBatteryMonitor() {
        M.log("battery monitor service started");
        startService(new Intent(this, BatteryStatusMonitorService.class));
    }


    public void stopBatteryMonitor() {
        M.log("battery monitor service stopped");
        stopService(new Intent(this, BatteryStatusMonitorService.class));
    }

    public void checkHasSecurityQuestion(String userId) {
        boolean hasSecurityQuestion = PreferenceHelper.getBoolean(this, PreferenceHelper.KEY_ADDED_SECURITY, false);
        if (hasSecurityQuestion) return;

        String path = getString(R.string.selected_security_questions_path);

        HashMap<String, String> headers = new HashMap<>(2);
        headers.put("api_token", PreferenceHelper.getString(this, PreferenceHelper.KEY_ACCESS_TOKEN, ""));
        headers.put("userid", userId);

        HashMap<String, String> params = new HashMap<>(1);
        params.put("userId", userId);

        new SecurityQuestionsRepository().getSelected(path, headers, params, new RequestCallback<SelectedSecurityQuestionResponse>() {
            @Override
            public void onSuccess(SelectedSecurityQuestionResponse result) {
                boolean hasSecurity;
                if (result == null || result.securityAnswer == null || result.securityAnswer.equals("") || result.securityPin == 0) {
                    hasSecurity = false;
                } else {
                    hasSecurity = true;
                }
                PreferenceHelper.saveBoolean(TjssApplication.this, PreferenceHelper.KEY_ADDED_SECURITY, hasSecurity);
            }

            @Override
            public void onFailure(String reason) {
                PreferenceHelper.saveBoolean(TjssApplication.this, PreferenceHelper.KEY_ADDED_SECURITY, false);
            }
        });

    }


}
