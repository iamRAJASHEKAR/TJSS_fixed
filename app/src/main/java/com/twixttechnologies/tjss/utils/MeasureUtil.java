package com.twixttechnologies.tjss.utils;

import android.content.res.Resources;

/**
 * @author Sony Raj on 07-10-17.
 */

@SuppressWarnings("unused")
public class MeasureUtil {


    private MeasureUtil() {
        //no instance
    }

    public static int dpToPixel(int dp) {
        return (int) ((dp * Resources.getSystem().getDisplayMetrics().density) + 0.5);
    }

    public static int pixelToDp(int pixel) {
        return (int) ((pixel / Resources.getSystem().getDisplayMetrics().density) + 0.5);
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}
