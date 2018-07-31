package com.innoviussoftwaresolution.tjss.view.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author Sony Raj on 03-11-17.
 */

public class TouchDisabledViewPager extends ViewPager {

    public TouchDisabledViewPager(Context context) {
        this(context, null);
    }

    public TouchDisabledViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
