package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

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
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyCircleMember;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Sony Raj on 23-08-17.
 */
public class SafetyCircleMembersAdapterForEdit extends RecyclerView.Adapter<SafetyCircleMembersAdapterForEdit.SafetyCircleViewHolderForEdit> {

    private final Context context;
    private final RequestManager mRequestManager;
    private final String IMAGE_BASE_URL;
    private final RequestOptions mRequestOptions;
    private List<SafetyCircleMember> items;

    private OnDeleteListener mDeleteListener;

    public SafetyCircleMembersAdapterForEdit(List<SafetyCircleMember> items, Context context,
                                             OnDeleteListener deleteListener) {
        this.items = items;
        this.context = context;
        mRequestManager = Glide.with(context);
        IMAGE_BASE_URL = context.getString(R.string.base_url);
        mRequestOptions = new RequestOptions()
                .override(100, 100)
                .fitCenter();
        mDeleteListener = deleteListener;
    }

    public void onDelete(int index, boolean success) {
        if (success) {
            items.remove(index);
            notifyItemRemoved(index);
        }
    }


    @Override
    public SafetyCircleViewHolderForEdit onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_safety_circle_members_item_for_edit, parent, false);
        return new SafetyCircleViewHolderForEdit(v);
    }

    @Override
    public void onBindViewHolder(final SafetyCircleViewHolderForEdit holder, int position) {
        SafetyCircleMember item = items.get(position);
        holder.mLblSafetyCircleName.setText(item.fname);
        mRequestManager
                .load(IMAGE_BASE_URL + "circleimages/" + item.image)
                .apply(mRequestOptions)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        holder.mLblSafetyCircleName
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


    public interface OnDeleteListener {
        void onDelete(SafetyCircleMember safetyCircleMember, int index);
    }

    class SafetyCircleViewHolderForEdit extends RecyclerView.ViewHolder {

        @BindView(R.id.lblSafetyCircleName)
        AppCompatTextView mLblSafetyCircleName;

        SafetyCircleViewHolderForEdit(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.btnDeleteUser)
        public void onViewClicked() {
            int index = getAdapterPosition();
            if (index < 0) return;
            SafetyCircleMember member = items.get(index);
            if (member == null) {
                M.log("Selected safety circle member is null");
                return;
            }
            String currentUserId = PreferenceHelper.getString(context, PreferenceHelper.KEY_USER_ID, "");
            if (currentUserId.equals(member.userId)) return;
            mDeleteListener.onDelete(member, index);
        }
    }


}