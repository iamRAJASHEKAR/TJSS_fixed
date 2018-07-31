package com.innoviussoftwaresolution.tjss.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.NotificationConstants;
import com.innoviussoftwaresolution.tjss.view.activity.HelpAlertDisplayActivity;
import com.innoviussoftwaresolution.tjss.view.activity.HomeActivity;
import com.innoviussoftwaresolution.tjss.view.activity.SafetyCircleDetailsActivity;
import com.innoviussoftwaresolution.tjss.view.activity.SafetyCircleSettingsActivity;
import com.innoviussoftwaresolution.tjss.view.activity.SettingsActivity;
import com.innoviussoftwaresolution.tjss.view.fragment.helpalert.FragmentHelpAlertDisplay;
import com.innoviussoftwaresolution.tjss.view.fragment.user.settings.circle.FragmentSafetyCircleDetails;

import java.util.Map;


public class TjssFireBaseMessagingService extends FirebaseMessagingService implements NotificationConstants {

    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String TYPE_ID = "type_id";
    private static final String USER_ID = "userid";
    private static final String REDIRECT = "redirect";
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String IMAGE = "image";
    private static final String TITLE = "title";
    private static final String LOCATION = "location";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        M.log("From: " + remoteMessage.getFrom());

        Map<String, String> map = remoteMessage.getData();

        if (map.size() > 0) {
            if (map.containsKey(REDIRECT)) {
                String redirectTo = map.get(REDIRECT);
                if (redirectTo == null || redirectTo.equals("")) return;

                if (redirectTo.toLowerCase().equals(RedirectConstants.HOME)) {
                    /*String lastLocation = PreferenceHelper.getString(this, PreferenceHelper.LAST_LOCATION,"");
                    String currentLocation = map.get(LOCATION);
                    PreferenceHelper.saveString(this,PreferenceHelper.LAST_LOCATION,currentLocation);

                    if (lastLocation.equals(currentLocation)) return;*/

                    Intent homeIntent = new Intent(this, HomeActivity.class);
                    TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
                    taskStackBuilder.addParentStack(HomeActivity.class);
                    taskStackBuilder.addNextIntent(homeIntent);

                    PendingIntent notificationIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    Notification notification = new NotificationCompat.Builder(this, "1")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(getString(R.string.app_name))
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_MAX)
                            .setContentText(map.get(TITLE))
                            .setContentIntent(notificationIntent)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .build();

                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1, notification);

                } else if (redirectTo.toLowerCase().equals(RedirectConstants.MAP)) {
                    Intent homeIntent = new Intent(this, HomeActivity.class);
                    TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
                    taskStackBuilder.addParentStack(HomeActivity.class);
                    taskStackBuilder.addNextIntent(homeIntent);

                    Intent displayIntent = new Intent(this, HelpAlertDisplayActivity.class);
                    displayIntent.putExtra(FragmentHelpAlertDisplay.LAT, map.get(LATITUDE));
                    displayIntent.putExtra(FragmentHelpAlertDisplay.LNG, map.get(LONGITUDE));
                    taskStackBuilder.addNextIntent(displayIntent);

                    PendingIntent notificationIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    Notification notification = new NotificationCompat.Builder(this, "1")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(getString(R.string.app_name))
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_MAX)
                            .setContentText(map.get(TITLE))
                            .setContentIntent(notificationIntent)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .build();

                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1, notification);

                } else if (redirectTo.equals(RedirectConstants.CIRCLE_LISTING)) {
                    //intent flow
                    //home => settings => safety circle options => safety circle members listing
                    //create a activity BackStack
                    //start with the home
                    Intent homeIntent = new Intent(this, HomeActivity.class);
                    TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
                    taskStackBuilder.addParentStack(HomeActivity.class);
                    taskStackBuilder.addNextIntent(homeIntent);

                    //next is the settings activity
                    Intent settingsIntent = new Intent(this, SettingsActivity.class);
                    taskStackBuilder.addNextIntent(settingsIntent);

                    //next is the safety circle options activity
                    Intent safetyCircleOptionsIntent = new Intent(this, SafetyCircleSettingsActivity.class);
                    taskStackBuilder.addNextIntent(safetyCircleOptionsIntent);

                    //now the members listing class
                    //pass the required args to the members listing activity
                    Intent safetyCircleMembersListingIntent = new Intent(this, SafetyCircleDetailsActivity.class);
                    safetyCircleMembersListingIntent.putExtra(FragmentSafetyCircleDetails.SAFETY_CIRCLE_ID, map.get(TYPE_ID));
                    safetyCircleMembersListingIntent.putExtra(FragmentSafetyCircleDetails.SAFETY_CIRCLE_NAME, map.get(NAME));
                    safetyCircleMembersListingIntent.putExtra(FragmentSafetyCircleDetails.SAFETY_CIRCLE_IMAGE_LINK, "");
                    safetyCircleMembersListingIntent.putExtra(FragmentSafetyCircleDetails.IS_ADMIN, false);
                    taskStackBuilder.addNextIntent(safetyCircleMembersListingIntent);

                    //now create the pending intent to launch when the notification is clicked
                    PendingIntent notificationIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    Notification notification = new NotificationCompat.Builder(this, "1")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(getString(R.string.app_name))
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_MAX)
                            .setContentText(map.get(TITLE))
                            .setContentIntent(notificationIntent)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .build();

                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1, notification);

                }
            }
        }

        if (remoteMessage.getNotification() != null) {
            M.log("Message Notification Body: " + remoteMessage.getNotification().getBody());
        }


    }

}
