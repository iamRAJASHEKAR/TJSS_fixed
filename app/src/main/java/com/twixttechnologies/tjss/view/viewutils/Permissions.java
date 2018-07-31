package com.twixttechnologies.tjss.view.viewutils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;

import com.twixttechnologies.tjss.view.fragment.generalalerts.PermissionsDetailListDialog;

import java.util.ArrayList;

/*
  @author Sony Raj on 05-08-17.
 */

/**
 * Manages checking and requesting permissions
 */
@SuppressWarnings("HardCodedStringLiteral")
public class Permissions {

    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final int READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 1;

    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final int LOCATION_REQUEST_CODE = 2;


    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final int CALL_PERMISSION_REQUEST_CODE = 3;

    public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final int CONTACTS_REQUEST_CODE = 4;


    public static final String CAMERA = Manifest.permission.CAMERA;
    public static final int CAMERA_REQUEST_CODE = 5;
    public static final int CAMERA_AND_STORAGE_REQUEST_CODE = 10;

    public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final int RECORD_AUDIO_REQUEST_CODE = 20;


    private Permissions() {
        //no instance
    }

    /**
     * Checks if we have permissions granted for the given list of permissions
     *
     * @param context     the context
     * @param permissions list of permissions to check
     * @return an array list of permissions that we need to ask permissions for, if we have all the
     * permissions granted, null will be returned
     */
    @Nullable
    public static ArrayList<String> havePermissionFor(Context context, String... permissions) {
        ArrayList<String> requiredPermissions = null;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) ==
                    PackageManager.PERMISSION_GRANTED)
                continue;
            if (requiredPermissions == null)
                requiredPermissions = new ArrayList<>();

            requiredPermissions.add(permission);
        }
        return requiredPermissions;
    }


    public static void requestPermissionFromFragment(Fragment fragment, int permissionsRequestCode,
                                                     String... permissionsRequired) {
        fragment.requestPermissions(permissionsRequired, permissionsRequestCode);
    }

    /**
     * Check if we should show a detail message about the permission required
     *
     * @param activity    the activity
     * @param permissions list of permissions
     * @return null if nothing else an array list of permissions
     */
    @Nullable
    public static ArrayList<String> shouldShowPermissionRequestRationale(Activity activity,
                                                                         String... permissions) {
        ArrayList<String> required = null;

        for (String permission : permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission))
                continue;

            if (required == null)
                required = new ArrayList<>();
            required.add(permission);
        }
        return required;

    }

    public static void showPermissionDetailsDialog(
            PermissionsDetailListDialog.PermissionsDialogCallback callback,
            FragmentManager fragmentManager) {
        String permissionDetails
                = "Request to access your location-TJSS will need to access your location in order " +
                "to work properly";

        ArrayList<String> permissionDetailsList = new ArrayList<>(1);
        permissionDetailsList.add(permissionDetails);

        PermissionsDetailListDialog dialog = PermissionsDetailListDialog.newInstance(permissionDetailsList);
        dialog.setCallback(callback);
        dialog.show(fragmentManager, PermissionsDetailListDialog.TAG);
    }


    /**
     * Displays an {@link android.support.v7.app.AlertDialog} with the details of permission required
     *
     * @param permissionDetails the list of message to display
     * @param okListener        {@link DialogInterface.OnClickListener} to handle positive
     *                          button click on the
     *                          dialog
     * @param fragmentManager   the fragment manager to show the dialog
     */
    public static void showPermissionDetails(ArrayList<String> permissionDetails,
                                             PermissionsDetailListDialog.PermissionsDialogCallback
                                                     okListener,
                                             FragmentManager fragmentManager) {
        PermissionsDetailListDialog dialog =
                PermissionsDetailListDialog.newInstance(permissionDetails);
        dialog.setCancelable(false);
        dialog.setCallback(okListener);
        dialog.show(fragmentManager, PermissionsDetailListDialog.TAG);
    }


}
