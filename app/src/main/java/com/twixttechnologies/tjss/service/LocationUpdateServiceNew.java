package com.twixttechnologies.tjss.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.innoviussoftwaresolution.tjss.R;
//import com.twixttechnologies.tjss.R;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.request.UpdateLocationRequest;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.LocationNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.utils.LocationUtil;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.utils.PreferenceHelper;
import com.twixttechnologies.tjss.view.viewutils.Permissions;

import org.joda.time.LocalTime;
import org.joda.time.Minutes;

/**
 * @author Sony Raj on 07-10-17.
 */

public class LocationUpdateServiceNew extends Service {

    private final Handler mHandler = new Handler();
    private final LocationUpdateRunnable locationUpdateRunnable = new LocationUpdateRunnable();
    Location lastLocation;
    LocalTime mLastUpdateTime = new LocalTime();
    LocationUtil mLocationUtil;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mLocationUtil == null) mLocationUtil = new LocationUtil(getApplication());
        mHandler.post(locationUpdateRunnable);
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacks(locationUpdateRunnable);
        super.onDestroy();
    }

    private class LocationUpdateRunnable implements Runnable {


        @Override
        public void run() {
            if (Permissions.havePermissionFor(getApplicationContext(), Permissions.FINE_LOCATION) == null) {
                M.log("have location permission");
                //boolean updateLocation = PreferenceHelper.getBoolean(getApplication(), PreferenceHelper.KEY_LOCATION_SHARED, false);
                //if (!updateLocation) return;

                final Location currentLocation = mLocationUtil.getFineLocation();
                Location previousLocation = lastLocation;

                if (currentLocation == null) return;
                final double latitude = currentLocation.getLatitude();
                final double longitude = currentLocation.getLongitude();


                String userId = PreferenceHelper.getString(getApplication(), PreferenceHelper.KEY_USER_ID, "");
                double previousLatitude = previousLocation == null ? 0.0 : previousLocation.getLatitude();
                double previousLongitude = previousLocation == null ? 0.0 : previousLocation.getLongitude();
                long lastUpdateTime = mLastUpdateTime.toDateTimeToday().getMillis();


                if (userId.equals("")) return;
                String authToken = PreferenceHelper.getString(getApplicationContext(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
                if (authToken.equals("")) return;

                String path = getString(R.string.update_user_location);

                LocalTime now = LocalTime.now();
                LocalTime previous = new LocalTime(lastUpdateTime);

                int diff = Minutes.minutesBetween(previous, now).getMinutes();

                int isIdle = (latitude == previousLatitude && longitude == previousLongitude) ? 0 : 1;

                if (((latitude != previousLatitude) || (longitude != previousLongitude))) {
                    M.log("updating Location");
                    new UpdateLocationRequest(LocationNetworkInterface.class, new RequestCallback<StatusMessage>() {
                        @Override
                        public void onSuccess(StatusMessage result) {
                            mLastUpdateTime = new LocalTime();
                            lastLocation = currentLocation;
                            M.log("Location Updated");
                        }

                        @Override
                        public void onFailure(String reason) {
                            M.log("Location", "Update Failed");
                            M.log("location", reason);
                        }
                    }).update(path, userId, authToken, latitude, longitude, isIdle);
                } else if ((diff >= 5)) {
                    M.log("updating Location");
                    new UpdateLocationRequest(LocationNetworkInterface.class, new RequestCallback<StatusMessage>() {
                        @Override
                        public void onSuccess(StatusMessage result) {
                            mLastUpdateTime = new LocalTime();
                            lastLocation = currentLocation;
                            M.log("Location Updated");
                        }

                        @Override
                        public void onFailure(String reason) {
                            M.log("Location", "Update Failed");
                            M.log("location", reason);
                        }
                    }).update(path, userId, authToken, latitude, longitude, isIdle);
                }
            } else {
                M.log("Don't have location permissions");
            }
            mHandler.postDelayed(this, 60 * 1000);
        }


    }


}
