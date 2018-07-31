package com.twixttechnologies.tjss.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import static android.content.Context.LOCATION_SERVICE;
import static com.twixttechnologies.tjss.utils.LocationUtil2.REQUEST_CHECK_SETTINGS;

/**
 * @author Sony Raj on 07-08-17.
 */
@SuppressWarnings("MissingPermission")
public class LocationUtil implements LocationListener {

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 500; // 500 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 2; // 2 minute
    private final Context context;
    private LocationUpdateCallback locationUpdateCallback;
    private LocationManager locationManager;
    private Location location;

    public LocationUtil(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

    }

    public void setLocationUpdateCallback(LocationUpdateCallback locationUpdateCallback) {
        this.locationUpdateCallback = locationUpdateCallback;
    }


    public Location getFineLocation() {

        // getting GPS status
        boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (isGPSEnabled) {
            if (location == null) {
                requestLocationUpdates();
                M.log("GPS Enabled", "GPS Enabled");
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
        }

        if (location == null) {
            getCoarseLocation();
        }

        return location;
    }

    public void requestLocationUpdates() {
        locationManager.requestLocationUpdates(
                locationManager.getBestProvider(new Criteria(), true),
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
    }

    public boolean isCoarseLocationEnabled(Context context) {
        return locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public boolean isFineLocationEnabled(Context context) {
        return locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public Location getCoarseLocation() {
        // getting network status
        boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isNetworkEnabled) {
            requestLocationUpdates();
            if (locationManager != null) {
                location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }

        return location;
    }

    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                        .addApi(LocationServices.API)
                        .build();
                googleApiClient.connect();
                LocationRequest locationRequest = LocationRequest.create();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setInterval(MIN_TIME_BW_UPDATES);
                locationRequest.setFastestInterval(MIN_TIME_BW_UPDATES / 2);
                locationRequest.setSmallestDisplacement(MIN_DISTANCE_CHANGE_FOR_UPDATES);

                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
                builder.setAlwaysShow(true);

                PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                    @Override
                    public void onResult(LocationSettingsResult result) {
                        final Status status = result.getStatus();
                        switch (status.getStatusCode()) {
                            case LocationSettingsStatusCodes.SUCCESS:
                                M.log("All location settings are satisfied.");
                                break;
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                M.log("Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                                try {
                                    status.startResolutionForResult(((Activity) context), REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException e) {
                                    M.log("PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                M.log("Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                                break;
                        }
                    }
                });
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        if (locationUpdateCallback != null)
            locationUpdateCallback.onUpdate(location);
        stopUsingGPS();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public interface LocationUpdateCallback {
        void onUpdate(Location location);
    }

}
