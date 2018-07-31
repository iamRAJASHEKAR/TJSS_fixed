package com.twixttechnologies.tjss.view.adapter.listadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;
import com.twixttechnologies.tjss.view.activity.UserTimeLineActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * @author Sony Raj on 23-08-17.
 */

/**
 * This adapter class is for listing members
 */

public class SafetyCircleMembersAdapter extends RecyclerView.Adapter<SafetyCircleMembersAdapter.SafetyCircleMemberViewHolder> {

    private final RequestManager mRequestManager;
    private final String IMAGE_BASE_URL;
    private final RequestOptions mRequestOptions;
    private final Context mContext;
    private List<SafetyCircleMember> items;


    private OnAdminStatusChangedListener mAdminStatusListener;

    public SafetyCircleMembersAdapter(List<SafetyCircleMember> items, Context context,
                                      OnAdminStatusChangedListener adminStatusListener) {
        this.items = items;
        mContext = context;
        mRequestManager = Glide.with(context);
        IMAGE_BASE_URL = context.getString(R.string.base_url);
        mRequestOptions = new RequestOptions()
                .fitCenter()
                .override(100, 100);
        mAdminStatusListener = adminStatusListener;
    }

    public int numberOfAdmins() {
        int count = 0;
        for (SafetyCircleMember s :
                items) {
            if (s.admin == 1) {
                ++count;
            }
        }
        return count;
    }

    @Override
    public SafetyCircleMemberViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_safety_circle_member_item, parent, false);
        return new SafetyCircleMemberViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SafetyCircleMemberViewHolder holder, int position) {
        SafetyCircleMember item = items.get(position);
        holder.mChkCircleMemberIsAdmin.setCompoundDrawablesWithIntrinsicBounds(
                item.admin == 1 ? R.drawable.check_image : R.drawable.un_check_image, 0, 0, 0);
        holder.mLblCircleMemberName.setText(item.fname);
        mRequestManager
                .load(IMAGE_BASE_URL + "circleimages/" + item.image)
                .apply(mRequestOptions)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        holder.mLblCircleMemberName
                                .setCompoundDrawablesWithIntrinsicBounds(resource, null, null, null);
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

    public void update(int selectedIndex, boolean success) {
        SafetyCircleMember member = items.get(selectedIndex);
        int isAdmin = member.admin;
        if (success) {
            member.admin = (isAdmin == 1 ? 0 : 1);
        } else {
            member.admin = (isAdmin == 1 ? 1 : 0);
        }
        notifyItemChanged(selectedIndex);
    }

    public interface OnAdminStatusChangedListener {
        void onStatusChanged(SafetyCircleMember safetyCircleMember, int index);
    }

    class SafetyCircleMemberViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblCircleMemberName)
        AppCompatTextView mLblCircleMemberName;
        @BindView(R.id.chkCircleMemberIsAdmin)
        AppCompatCheckBox mChkCircleMemberIsAdmin;

        SafetyCircleMemberViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        public void onClick() {
            int index = getAdapterPosition();
            if (index < 0) return;
            SafetyCircleMember safetyCircleMember = items.get(index);
            if (safetyCircleMember == null) return;
            Intent timeLineIntent = new Intent(mContext, UserTimeLineActivity.class);
            timeLineIntent.putExtra(UserTimeLineActivity.SAFETY_CIRCLE_MEMBER, safetyCircleMember);
            mContext.startActivity(timeLineIntent);
        }

        @OnClick(R.id.chkCircleMemberIsAdmin)
        void onClicked() {
            int index = getAdapterPosition();
            if (index < 0) return;
            SafetyCircleMember member = items.get(index);
            if (member == null) return;
            mAdminStatusListener.onStatusChanged(member, index);
        }
    }

}