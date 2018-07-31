package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyCircleMember;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Sony Raj on 02-10-17.
 */
public class SafetyCircleMembersForGroupChatAdapter extends RecyclerView.Adapter<SafetyCircleMembersForGroupChatAdapter.SafetyCircleMemberViewHolder> {

    private List<SafetyCircleMember> items;
    private RequestManager mRequestManager;
    private RequestOptions mRequestOptions;
    private String imageBase;

    public SafetyCircleMembersForGroupChatAdapter(List<SafetyCircleMember> items, Context context) {
        this.items = items;
        mRequestManager = Glide.with(context);
        mRequestOptions = new RequestOptions().override(60, 60);
        imageBase = context.getString(R.string.image_base_url);
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

    public String getSelectedMembers() {

        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (int i = 0; i < items.size(); i++) {
            if (!items.get(i).isSelected) continue;
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(",");
            }
            sb.append(items.get(i).userId);
        }

        return sb.toString();
    }


    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public void replace(List<SafetyCircleMember> safetyCircleMembers) {
        items = safetyCircleMembers;
        notifyDataSetChanged();
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
                }
            });
        }

        @OnClick
        public void onClick() {
            mChkSafetyCircleMemberSelected.setChecked(!mChkSafetyCircleMemberSelected.isChecked());
        }

    }

}