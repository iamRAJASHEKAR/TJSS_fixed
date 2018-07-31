package com.innoviussoftwaresolution.tjss.service;

import android.location.Location;
import android.os.Bundle;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.TjssApplication;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.request.UpdateLocationRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.LocationNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;
import com.innoviussoftwaresolution.tjss.utils.LocationUtil;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;
import com.innoviussoftwaresolution.tjss.view.viewutils.Permissions;

import org.joda.time.LocalTime;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

public class LocationUpdateService extends JobService {

    LocationUtil locationUtil;

    @Override
    public boolean onStartJob(final JobParameters job) {
        M.log("Updating location");

        boolean isLoggedIn = !PreferenceHelper.getString(getApplication(), PreferenceHelper.KEY_USER_ID, "").equals("");
        if (!isLoggedIn) return false;
        locationUtil = new LocationUtil(getApplicationContext());
        if (Permissions.havePermissionFor(getApplicationContext(), Permissions.FINE_LOCATION) != null)
            return false;
        final Location location = locationUtil.getFineLocation();
        Location previousLocation = ((TjssApplication) getApplication()).getPreviousLocation();

        if (location == null) return false;
        final double latitude = location.getLatitude();
        final double longitude = location.getLongitude();

        String userId = "";
        final Bundle b = job.getExtras();
        double previousLatitude = previousLocation == null ? 0.0 : previousLocation.getLatitude();
        double previousLongitude = previousLocation == null ? 0.0 : previousLocation.getLongitude();
        long lastUpdateTime = ((TjssApplication) getApplication()).getLastUpdateTime();

        //noinspection ConstantConditions
        userId = b.getString("userId", "");

        if (userId == null || userId.equals("")) return false;
        String authToken = PreferenceHelper.getString(getApplicationContext(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
        if (authToken.equals("")) return false;

        String path = getString(R.string.update_user_location);

        LocalTime now = LocalTime.now();
        LocalTime previous = new LocalTime(lastUpdateTime);

        int diff = Minutes.minutesBetween(previous, now).getMinutes();

        int isIdle = (latitude == previousLatitude && longitude == previousLongitude) ? 1 : 0;

        if (diff >= 5 || (latitude != previousLatitude && longitude != previousLongitude)) {
            new UpdateLocationRequest(LocationNetworkInterface.class, new RequestCallback<StatusMessage>() {
                @Override
                public void onSuccess(StatusMessage result) {
                    jobFinished(job, true);
                    ((TjssApplication) getApplication()).setLastUpdateTime(new Date().getTime());
                    ((TjssApplication) getApplication()).setPreviousLocation(location);
                }

                @Override
                public void onFailure(String reason) {
                    M.log("Location", "Update Failed");
                    M.log("location", reason);
                }
            }).update(path, userId, authToken, latitude, longitude, isIdle);
        } else {
            return false;
        }
        M.log("location update", "last updateTime = " + lastUpdateTime + " now =" + new Date().getTime());
        M.log("location update", "last latitude = " + previousLatitude + " last longitude = " + previousLongitude);
        M.log("location update", "new latitude = " + latitude + " new longitude =" + longitude);
        M.log("location update", "local time = " + new LocalTime(new Date()).toString(DateTimeFormat.shortTime()));
        M.log("Calling update finished");

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        locationUtil.stopUsingGPS();
        return true;
    }
}
