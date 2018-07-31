package com.twixttechnologies.tjss.utils.extensions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.innoviussoftwaresolution.tjss.R;
//import com.twixttechnologies.tjss.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Sony Raj on 11-09-17.
 */

public class BitmapToPinExtension extends com.twixttechnologies.tjss.utils.extensions.BitmapTransformation {

    private String name;

    public BitmapToPinExtension(String name) {
        this.name = name;
    }

    @Override
    public String key() {
        return "pin for " + name;
    }

    @Override
    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
                               @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater == null) return null;

        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.extra_pin, null, false);
        CircleImageView imageView = (CircleImageView) view.findViewById(R.id.img);
        imageView.setImageBitmap(toTransform);
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache(true);
        return view.getDrawingCache();
    }

}
