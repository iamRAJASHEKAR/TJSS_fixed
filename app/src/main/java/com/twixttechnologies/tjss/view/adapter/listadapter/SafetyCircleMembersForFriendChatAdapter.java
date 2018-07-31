package com.twixttechnologies.tjss.view.adapter.listadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Sony Raj on 02-10-17.
 */
public class SafetyCircleMembersForFriendChatAdapter extends RecyclerView.Adapter<SafetyCircleMembersForFriendChatAdapter.SafetyCircleMemberViewHolder> {

    private List<SafetyCircleMember> items;
    private RequestManager mRequestManager;
    private RequestOptions mRequestOptions;
    private String imageBase;

    private int mLastSelectedIndex = -1;

    private Handler mHandler = new Handler();
    private UpdateRunnable mUpdateRunnable;

    public SafetyCircleMembersForFriendChatAdapter(List<SafetyCircleMember> items, Context context) {
        this.items = items;
        mRequestManager = Glide.with(context);
        mRequestOptions = new RequestOptions().override(60, 60);
        imageBase = context.getString(R.string.image_base_url);
    }

    public void replace(List<SafetyCircleMember> safetyCircleMembers) {
        items = safetyCircleMembers;
        notifyDataSetChanged();
    }

    @Override
    public SafetyCircleMemberViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_safety_circle_member_for_group_chat, parent, false);
        return new SafetyCircleMemberViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SafetyCircleMemberViewHolder holder, int position) {
        SafetyCircleMember item = items.get(position);
        mRequestManager.load(imageBase + item.image)
                .apply(mRequestOptions)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        holder.mImgSafetyCircleMemberDp.setImageDrawable(resource);
                    }
                });
        holder.mLblCircleMemberName.setText(item.fname);
        holder.mLblSafetyCircleMemberEmail.setText(item.phone);
        holder.mChkSafetyCircleMemberSelected.setChecked(item.isSelected);
    }

    @Nullable
    public SafetyCircleMember getSelectedMember() {
        if (mLastSelectedIndex < 0) return null;
        return items.get(mLastSelectedIndex);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    private class UpdateRunnable implements Runnable {

        private int index = -1;

        public void setIndex(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            if (index < 0) return;
            notifyItemChanged(index);
        }
    }

    class SafetyCircleMemberViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.imgSafetyCircleMemberDp)
        CircleImageView mImgSafetyCircleMemberDp;
        @BindView(R.id.lblCircleMemberName)
        AppCompatTextView mLblCircleMemberName;
        @BindView(R.id.lblSafetyCircleMemberEmail)
        AppCompatTextView mLblSafetyCircleMemberEmail;
        @BindView(R.id.chkSafetyCircleMemberSelected)
        AppCompatCheckBox mChkSafetyCircleMemberSelected;

        SafetyCircleMemberViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mChkSafetyCircleMemberSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int index = getAdapterPosition();
                    if (index < 0 || index >= items.size()) return;
                    items.get(index).isSelected = isChecked;

                    if (mLastSelectedIndex >= 0) {
                        items.get(mLastSelectedIndex).isSelected = false;
                        if (mUpdateRunnable == null) mUpdateRunnable = new UpdateRunnable();
                        mUpdateRunnable.setIndex(mLastSelectedIndex);
                        mHandler.postDelayed(mUpdateRunnable, 300);
                    }

                    mLastSelectedIndex = index;

                }
            });
        }

        @OnClick
        public void onClick() {
            mChkSafetyCircleMemberSelected.setChecked(!mChkSafetyCircleMemberSelected.isChecked());
        }


    }

}