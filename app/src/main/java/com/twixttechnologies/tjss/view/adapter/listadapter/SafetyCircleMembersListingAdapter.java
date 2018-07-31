package com.twixttechnologies.tjss.view.adapter.listadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.innoviussoftwaresolution.tjss.R;
//import com.twixttechnologies.tjss.R;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/*
 * @author Sony Raj on 23-08-17.
 */

/**
 * This adapter class is for listing members
 */

public class SafetyCircleMembersListingAdapter extends RecyclerView.Adapter<SafetyCircleMembersListingAdapter.SafetyCircleMemberViewHolder> {

    private final RequestManager mRequestManager;
    private final String IMAGE_BASE_URL;
    private final RequestOptions mRequestOptions;
    private List<SafetyCircleMember> items;

    private OnMemberSelectedCallback mMemberSelectedCallback;

    public SafetyCircleMembersListingAdapter(List<SafetyCircleMember> items, Context context,
                                             OnMemberSelectedCallback callback) {
        this.items = items;
        mRequestManager = Glide.with(context);
        IMAGE_BASE_URL = context.getString(R.string.image_base_url);
        mRequestOptions = new RequestOptions()
                .fitCenter()
                .override(100, 100);
        mMemberSelectedCallback = callback;
    }

    @Override
    public SafetyCircleMemberViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_safety_circle_member, parent, false);
        return new SafetyCircleMemberViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SafetyCircleMemberViewHolder holder, int position) {
        SafetyCircleMember item = items.get(position);
        holder.mLblCircleMemberName.setText(item.fname);
        holder.mLblSafetyBattery.setText(MessageFormat.format("{0} %", item.battery));

        if (!TextUtils.isEmpty(item.locationName))
            holder.mLblSafetyCircleLocation.setText(item.locationName);
        else
            holder.mLblSafetyCircleLocation.setVisibility(View.GONE);
        mRequestManager
                .load(IMAGE_BASE_URL + item.image)
                .apply(mRequestOptions)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        holder.mImgSafetyCircleDp.setImageDrawable(resource);
                    }
                });
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public interface OnMemberSelectedCallback {
        void onSelected(SafetyCircleMember member, boolean call);
    }

    class SafetyCircleMemberViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.imgSafetyCircleDp)
        CircleImageView mImgSafetyCircleDp;
        @BindView(R.id.lblCircleMemberName)
        AppCompatTextView mLblCircleMemberName;
        @BindView(R.id.lblSafetyBattery)
        AppCompatTextView mLblSafetyBattery;
        @BindView(R.id.lblSafetyCircleLocation)
        AppCompatTextView mLblSafetyCircleLocation;

        SafetyCircleMemberViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.btnSafetyCircleMsg, R.id.btnSafetyCirclePhone})
        public void onViewClicked(View view) {
            int index = getAdapterPosition();
            if (index < 0 || index >= items.size()) return;
            SafetyCircleMember member = items.get(index);
            if (member == null) return;
            switch (view.getId()) {
                case R.id.btnSafetyCircleMsg:
                    if (mMemberSelectedCallback != null)
                        mMemberSelectedCallback.onSelected(member, false);
                    break;
                case R.id.btnSafetyCirclePhone:
                    mMemberSelectedCallback.onSelected(member, true);
                    break;
            }
        }


    }

}