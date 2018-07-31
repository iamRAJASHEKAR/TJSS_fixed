package com.innoviussoftwaresolution.tjss.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.innoviussoftwaresolution.tjss.broadcastreceiver.NewLocationAvailableReceiver;
import com.innoviussoftwaresolution.tjss.view.viewutils.Permissions;

import java.lang.ref.WeakReference;


/**
 * @author Sony Raj on 21/11/17.
 */

public class LocationUtil2 extends LocationCallback {

    @Override
    public void onLocationResult(LocationResult locationResult) {
        super.onLocationResult(locationResult);
        Location location = locationResult.getLastLocation();
        mLocationProviderClient.removeLocationUpdates(this);
        mMediumAccuracyLocationRequest.setFastestInterval(MIN_TIME_BW_UPDATES);
        mMediumAccuracyLocationRequest.setInterval(MIN_TIME_BW_UPDATES * 5);
        mMediumAccuracyLocationRequest.setSmallestDisplacement(MIN_DISTANCE_CHANGE_FOR_UPDATES);
        if (location == null) return;
        sendLocationChangeOrLocationAvailableBroadCast(location);
    }

    private final FusedLocationProviderClient mLocationProviderClient;

    public static final int REQUEST_CHECK_SETTINGS = 100;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 500; // 500 meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 2; // 2 minute
    private final Context mContext;

    private LocationRequest mMediumAccuracyLocationRequest;
    private LocationRequest mHighAccuracyLocationRequest;
    private OnCompleteListener<LocationSettingsResponse> mCompleteListener;

    public LocationUtil2(Context context) {
        this.mContext = context;
        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public void checkForSufficientSettings(Activity activity) {
        final WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        if (mMediumAccuracyLocationRequest == null) {
            mMediumAccuracyLocationRequest = new LocationRequest();
            mMediumAccuracyLocationRequest.setNumUpdates(1);
            mMediumAccuracyLocationRequest.setSmallestDisplacement(MIN_DISTANCE_CHANGE_FOR_UPDATES);
            mMediumAccuracyLocationRequest.setFastestInterval(MIN_TIME_BW_UPDATES);
            mMediumAccuracyLocationRequest.setInterval(MIN_TIME_BW_UPDATES * 5);
            mMediumAccuracyLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        }

        if (mHighAccuracyLocationRequest == null) {
            mHighAccuracyLocationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setFastestInterval(MIN_TIME_BW_UPDATES / 2)
                    .setInterval(MIN_TIME_BW_UPDATES * 10)
                    .setNumUpdates(1);
        }
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mMediumAccuracyLocationRequest)
                .addLocationRequest(mHighAccuracyLocationRequest)
                .setAlwaysShow(false);

        Task<LocationSettingsResponse> result =
                LocationServices.getSettingsClient(mContext).checkLocationSettings(builder.build());

        if (mCompleteListener == null)
            mCompleteListener = new OnCompleteListener<LocationSettingsResponse>() {
                @SuppressLint("MissingPermission")
                @Override
                public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                    try {
                        LocationSettingsResponse response = task.getResult(ApiException.class);
                        M.log("Gps ", String.valueOf(response.getLocationSettingsStates().isGpsPresent()));

                        if (Permissions.havePermissionFor(mContext, Permissions.FINE_LOCATION) != null)
                            return;
                        if (activityWeakReference.get() != null)
                            getLocation(activityWeakReference.get());

                    } catch (ApiException exception) {
                        switch (exception.getStatusCode()) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                try {
                                    ResolvableApiException resolvable = (ResolvableApiException) exception;
                                    resolvable.startResolutionForResult(
                                            activityWeakReference.get(),
                                            REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException e) {
                                    // Ignore .
                                } catch (ClassCastException e) {
                                    // Ignore.
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                M.log("No way to fix");
                                break;
                        }
                    }
                }
            };

        result.addOnCompleteListener(mCompleteListener);
    }

    public void getLocation(Activity activity) {
        try {
            mLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location == null) {
                                mHighAccuracyLocationRequest.setInterval(0);
                                mHighAccuracyLocationRequest.setFastestInterval(0);
                                mHighAccuracyLocationRequest.setSmallestDisplacement(0);
                                try {
                                    mLocationProviderClient.requestLocationUpdates(mHighAccuracyLocationRequest, LocationUtil2.this, null);
                                } catch (SecurityException e) {
                                    M.log(e.getMessage());
                                }

                                return;
                            }
                            sendLocationChangeOrLocationAvailableBroadCast(location);
                        }
                    });
        } catch (SecurityException e) {
            M.log(e.getMessage());
        }
    }

    private void sendLocationChangeOrLocationAvailableBroadCast(Location location) {
        Intent intent = new Intent();
        intent.setAction(NewLocationAvailableReceiver.LOCATION_AVAILABLE_ACTION);
        intent.putExtra(NewLocationAvailableReceiver.LAT, location.getLatitude());
        intent.putExtra(NewLocationAvailableReceiver.LNG, location.getLongitude());
        LocalBroadcastManager.getInstance(mContext)
                .sendBroadcast(intent);
    }

}
