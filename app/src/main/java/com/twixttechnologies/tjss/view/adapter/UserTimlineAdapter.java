package com.twixttechnologies.tjss.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.UserTimeLineItem;
import com.twixttechnologies.tjss.view.activity.TimeLineHistoryDetailActivity;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sony Raj on 07-10-17.
 */
public class UserTimlineAdapter extends RecyclerView.Adapter<UserTimlineAdapter.UserTravellingViewHolder> {
    private final Context context;
    private List<UserTimeLineItem> items;

    public UserTimlineAdapter(List<UserTimeLineItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public UserTravellingViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_user_time_line_item, parent, false);
        return new UserTravellingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final UserTravellingViewHolder holder, int position) {
        UserTimeLineItem item = items.get(position);
        holder.mLblTimeLineCaption.setText(item.placeName);
        holder.mLblTimeLineDuration.setText(MessageFormat.format("{0} mins", item.time));
        if (item.type.equals("1")) {
            Glide.with(context)
                    .load("http://twixttechnologies.com/tjss/public/images/" + item.route)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            holder.mImgTimeLineRoute.setImageDrawable(resource);
                        }
                    });
        } else {
            holder.mImgTimeLineRoute.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class UserTravellingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgTimeLineCirclePin)
        AppCompatImageView mImgTimeLineCirclePin;
        @BindView(R.id.lblTimeLineCaption)
        AppCompatTextView mLblTimeLineCaption;
        @BindView(R.id.lblTimeLineDuration)
        AppCompatTextView mLblTimeLineDuration;
        @BindView(R.id.imgTimeLineRoute)
        AppCompatImageView mImgTimeLineRoute;

        UserTravellingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        public void onClick() {
            int index = getAdapterPosition();
            if (index < 0 || index >= items.size()) return;
            UserTimeLineItem item = items.get(index);
            if (item == null) return;
            Intent detailIntent = new Intent(context, TimeLineHistoryDetailActivity.class);
            detailIntent.putExtra("id", item.id);
            detailIntent.putExtra("userId", item.userId);
            context.startActivity(detailIntent);
        }
    }

}