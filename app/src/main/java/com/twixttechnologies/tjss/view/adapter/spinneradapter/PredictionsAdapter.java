package com.twixttechnologies.tjss.view.adapter.spinneradapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.twixttechnologies.tjss.model.internal.AddressPrediction;
import com.twixttechnologies.tjss.utils.M;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

/**
 * @author Sony Raj on 12-09-17.
 */

public class PredictionsAdapter extends ArrayAdapter<AddressPrediction> {

    private final Context mContext;
    private final int mLayout;
    private final int mTextViewId;
    private final List<AddressPrediction> mItems;

    public PredictionsAdapter(@NonNull Context context, @LayoutRes int layoutResource,
                              @IdRes int textViewResourceId,
                              @NonNull List<AddressPrediction> objects) {
        super(context, layoutResource, textViewResourceId, objects);

        mContext = context;
        mLayout = layoutResource;
        mTextViewId = textViewResourceId;
        mItems = objects;
    }

    @Override
    public void clear() {
        mItems.clear();
    }

    @Override
    public void addAll(@NonNull Collection<? extends AddressPrediction> collection) {
        mItems.addAll(collection);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Nullable
    @Override
    public AddressPrediction getItem(int position) {
        return mItems.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(mLayout, parent, false);
        }
        try {
            AppCompatTextView label = (AppCompatTextView) convertView.findViewById(mTextViewId);
            label.setText(MessageFormat.format("{0}, {1}", mItems.get(position).getPrimaryText(),
                    mItems.get(position).getSecondaryText()));

        } catch (Exception e) {
            M.log(e.getMessage());
        }
        return convertView;
    }
}
