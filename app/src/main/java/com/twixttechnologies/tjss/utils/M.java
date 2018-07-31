package com.twixttechnologies.tjss.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * @author Sony Raj on 04-08-17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class M {

    private static final String TAG = "TJSS";

    private M() {
        //no instance
    }

    public static void log(String message) {
        log(TAG, message);
    }

    public static void log(String tag, String message) {
        Log.e(tag, message);
    }

    public static void showAlert(Context context, String title, String message,
                                 String positiveButtonText, String negativeButtonText,
                                 DialogInterface.OnClickListener okListener,
                                 DialogInterface.OnClickListener cancelListener,
                                 boolean cancellable) {

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, okListener)
                .setNegativeButton(negativeButtonText, cancelListener)
                .setCancelable(cancellable)
                .show();
    }

    public static void showToast(Context context, String message) {
        Toast t = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP, 0, 200);
        t.show();
    }


    /**
     * Shows a Snack bar with a duration of {@link Snackbar#LENGTH_SHORT}
     *
     * @param view    the view for finding the parent for the snack bar
     * @param message the message to be displayed
     */
    public static void showSnackBar(View view, String message) {
        showSnackBar(view, message, null, null);

    }

    /**
     * Shows a Snack bar with a duration of {@link Snackbar#LENGTH_SHORT}
     *
     * @param view           the view for finding the parent for the snack bar
     * @param message        the message to be displayed
     * @param actionText     the text to display on the button if the value is null <B>OK<B/> will be
     *                       displayed on the button <p>The button will be displayed only if the
     *                       "actionListener is not null"</p>
     * @param actionListener the button click listener
     */
    public static void showSnackBar(View view, String message,
                                    @Nullable String actionText,
                                    @Nullable View.OnClickListener actionListener) {
        showSnackBar(view, message, actionText, actionListener, Snackbar.LENGTH_SHORT);

    }

    /**
     * Shows a Snack bar with the duration specified
     *
     * @param view           the view for finding the parent for the snack bar
     * @param message        the message to be displayed
     * @param actionText     the text to display on the button if the value is null <B>OK<B/> will be
     *                       displayed on the button <p>The button will be displayed only if the
     *                       "actionListener is not null"</p>
     * @param actionListener the button click listener
     * @param duration       The snack bar duration
     */
    public static void showSnackBar(View view, String message,
                                    @Nullable String actionText,
                                    @Nullable View.OnClickListener actionListener,
                                    int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        if (actionListener != null)
            snackbar.setAction(actionText == null ? "OK" : actionText, actionListener);

        snackbar.show();

    }


}
