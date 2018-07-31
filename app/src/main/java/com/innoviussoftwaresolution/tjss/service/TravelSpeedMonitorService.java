package com.innoviussoftwaresolution.tjss.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.innoviussoftwaresolution.tjss.utils.LocationUtil;
import com.innoviussoftwaresolution.tjss.utils.M;


/**
 * @author Sony Raj on 26-09-17.
 */

public class TravelSpeedMonitorService extends Service {


    private static final int ONE_MINUTE = 1000;
    LocationUtil locationUtil;
    Location location = null;
    private Handler handler = new Handler();
    private Runnable startTrackingRunnable = new Runnable() {
        @Override
        public void run() {
            locationUtil.requestLocationUpdates();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new Handler();
        M.log("speed", "Starting speed detector");
        locationUtil = new LocationUtil(getApplication());
        location = locationUtil.getFineLocation();
        locationUtil.requestLocationUpdates();
        locationUtil.setLocationUpdateCallback(new LocationUtil.LocationUpdateCallback() {
            @Override
            public void onUpdate(Location newLocation) {
                M.log("speed", "Starting speed detector");
                if (location != null) {
                    M.log("speed " + "location", location.getLatitude() + " = " + location.getLongitude());
                }
                if (newLocation == null) {
                    M.log("new location is null");
                    return;
                }

                M.log("speed " + "new Location", newLocation.getLatitude() + " = " + newLocation.getLongitude());

                if (location == null) {
                    location = newLocation;
                    return;
                }

                boolean isBetterLocation = isBetterLocation(location, newLocation);
                Double distance = getDistance(location.getLatitude(), location.getLongitude(),
                        newLocation.getLatitude(), newLocation.getLongitude());
                location = newLocation;
                locationUtil.stopUsingGPS();
                handler.postDelayed(startTrackingRunnable, 30000);

            }
        });
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationUtil.stopUsingGPS();
        stopSelf();
    }

    boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > ONE_MINUTE;
        boolean isSignificantlyOlder = timeDelta < -ONE_MINUTE;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether two providers are the same
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    private double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371000; // for haversine use R = 6372.8 km instead of 6371 km
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return 2 * R * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    private class SpeedTrackerRunnable implements Runnable {

        @Override
        public void run() {
            M.log("speed", "Starting speed detector");
            if (location != null) {
                M.log("speed " + "location", location.getLatitude() + " = " + location.getLongitude());
            }
            Location newLocation = locationUtil.getFineLocation();
            if (newLocation == null) {
                M.log("new location is null");
                return;
            }

            M.log("speed " + "new Location", newLocation.getLatitude() + " = " + newLocation.getLongitude());

            if (location == null) {
                location = newLocation;
                return;
            }

            boolean isBetterLocation = isBetterLocation(location, newLocation);
            Double distance = getDistance(location.getLatitude(), location.getLongitude(),
                    newLocation.getLatitude(), newLocation.getLongitude());
            location = newLocation;

            //Toast.makeText(TravelSpeedMonitorService.this, "Your speed is : " + distance / 1000 + " Kms", Toast.LENGTH_SHORT).show();

            handler.postDelayed(this, 60000);
        }


    }

}
