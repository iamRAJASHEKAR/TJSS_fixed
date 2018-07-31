package com.twixttechnologies.tjss.view.adapter.listadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.twixttechnologies.tjss.model.network.request.GroupMember;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Sony Raj on 28-10-17.
 */
public class GroupMembersAdapter extends RecyclerView.Adapter<GroupMembersAdapter.GroupMemberViewHolder> {

    private final Context context;
    private List<GroupMember> items;

    private RequestManager mRequestManager;

    public GroupMembersAdapter(List<GroupMember> items, Context context) {
        this.items = items;
        this.context = context;
        mRequestManager = Glide.with(context);
        RequestOptions requestOptions = new RequestOptions().override(45, 45);
        mRequestManager.applyDefaultRequestOptions(requestOptions);
    }

    @Override
    public GroupMemberViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_group_member, parent, false);
        return new GroupMemberViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final GroupMemberViewHolder holder, int position) {
        GroupMember item = items.get(position);
        mRequestManager
                .load(context.getString(R.string.image_base_url) + item.profileImage)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        holder.mImgGroupMemberImage.setImageDrawable(resource);
                    }
                });

        holder.mLblGroupName.setText(item.fname);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class GroupMemberViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgGroupMemberImage)
        CircleImageView mImgGroupMemberImage;
        @BindView(R.id.lblGroupName)
        AppCompatTextView mLblGroupName;

        GroupMemberViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}