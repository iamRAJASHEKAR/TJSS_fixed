package com.twixttechnologies.tjss.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * @author Sony Raj on 07-08-17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class PreferenceHelper {

    public static final String LAST_UPDATED_LEVEL = "lastUpdatedLevel";
    public static final String KEY_FIRST_NAME = "fName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PROFILE_IMAGE = "profile";
    public static final String KEY_PRIMARY_CIRCLE = "primaryCircle";
    public static final String KEY_ACCESS_TOKEN = "accessToken";
    public static final String KEY_REFRESH_TOKEN = "refreshToken";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_SUBSCRIPTION_PLAN_ID = "subscriptionPlanId";
    public static final String KEY_USER_LOGGED_IN = "loggedIn";
    public static final String KEY_MAP_OPTIONS = "mapOptions";
    public static final String KEY_LOCATION_SHARED = "ShareLocation";
    public static final String KEY_UPDATE_INTERVAL = "locationUpdateInterval";
    public static final String KEY_ADDED_SECURITY = "addedSecurity";
    public static final String KEY_LAST_NAME = "lastName";
    private static final String KEY = "TJSS";
    public static final String KEY_SERVICE_PROVIDER = "10";
    public static final String LAST_LOCATION = "lastLocation";

    private PreferenceHelper() {
        //no instance
    }


    public static void saveString(Context context, String key, String value) {
        Log.d("Preference value: ",value + key);
        getEditor(context)
                .putString(key, value)
                .apply();
        //Log.d("Preference value: ",value + key);
    }

    public static void saveInt(Context context, String key, int value) {
        getEditor(context)
                .putInt(key, value)
                .apply();
    }

    public static void saveFloat(Context context, String key, float value) {
        getEditor(context)
                .putFloat(key, value)
                .apply();
    }

    public static void saveBoolean(Context context, String key, boolean value) {
        getEditor(context)
                .putBoolean(key, value)
                .apply();
    }


    public static String getString(Context context, String key, String defaultValueIfFailed) {
        return getSharedPreferences(context)
                .getString(key, defaultValueIfFailed);
    }

    public static int getInt(Context context, String key, int defaultValueIfFailed) {
        return getSharedPreferences(context)
                .getInt(key, defaultValueIfFailed);
    }

    public static float getFloat(Context context, String key, float defaultValueIfFailed) {
        return getSharedPreferences(context)
                .getFloat(key, defaultValueIfFailed);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValueIfFailed) {
        return getSharedPreferences(context)
                .getBoolean(key, defaultValueIfFailed);
    }


    private static SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    public static void reset(Context context) {
        saveString(context, KEY_FIRST_NAME, "");
        saveString(context, KEY_EMAIL, "");
        saveString(context, KEY_PROFILE_IMAGE, "");
        saveString(context, KEY_PRIMARY_CIRCLE, "");
        saveString(context, KEY_ACCESS_TOKEN, "");
        saveString(context, KEY_REFRESH_TOKEN, "");
        saveString(context, KEY_PHONE, "");
        saveString(context, KEY_USER_ID, "");
        saveString(context, KEY_SUBSCRIPTION_PLAN_ID, "");
        saveString(context, KEY_MAP_OPTIONS, "");
        saveBoolean(context, KEY_USER_LOGGED_IN, false);
    }


}
