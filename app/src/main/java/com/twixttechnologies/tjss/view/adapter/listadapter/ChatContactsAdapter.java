package com.twixttechnologies.tjss.view.adapter.listadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.twixttechnologies.tjss.model.internal.ChatListItem;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.activity.ChatDetailActivity;
import com.twixttechnologies.tjss.view.fragment.message.FragmentChatDetail;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Sony Raj on 04-10-17.
 */
public class ChatContactsAdapter extends RecyclerView.Adapter<ChatContactsAdapter.ChatContactsViewHolder> {
    private final LocalDateTime today;
    private final String IMAGE_BASE;
    private final Context mContext;
    private List<ChatListItem> items;
    private RequestManager mRequestManager;
    private RequestOptions mRequestOptions;

    public ChatContactsAdapter(List<ChatListItem> items, Context context) {
        this.items = items;
        mContext = context;
        JodaTimeAndroid.init(context.getApplicationContext());
        today = LocalDateTime.now();
        mRequestManager = Glide.with(context.getApplicationContext());
        mRequestOptions = new RequestOptions().override(48, 48);
        IMAGE_BASE = context.getResources().getString(R.string.image_base_url);
    }

    @Override
    public ChatContactsViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_chat_list_item, parent, false);
        return new ChatContactsViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ChatContactsViewHolder holder, int position) {
        ChatListItem item = items.get(position);
        try {

            if (item.messageType != null)
                switch (item.messageType) {
                    case "1":
                        holder.mLblChatMessage.setText(item.content);
                        break;
                    case "2":
                        holder.mLblChatMessage.setText("Check in");
                        break;
                    case "3":
                        holder.mLblChatMessage.setText("Image");
                        break;
                    case "4":
                        holder.mLblChatMessage.setText("Video");
                        break;
                    case "5":
                        holder.mLblChatMessage.setText("Message deleted");
                        break;
                    case "6":
                        holder.mLblChatMessage.setText("Audio");
                        break;
                }


            holder.mLblChatTitle.setText(item.profileName != null ? item.profileName : (item.groupName + " (G)"));
            //processing date
            DateTime dateTime = new DateTime(item.createdAt);

            LocalDateTime localDateTime = LocalDateTime.fromDateFields(dateTime.toDate());
            int days = Days.daysBetween(localDateTime, today).getDays();
            if (days <= 0) {
                //message send today
                holder.mLblChatMessageTime.setText(localDateTime.toString(DateTimeFormat.shortTime()));
            } else {
                //message is at-least one day old
                holder.mLblChatMessageTime.setText(localDateTime.toString(DateTimeFormat.shortDateTime()));
            }


            mRequestManager
                    .load(IMAGE_BASE + (item.singleUser == 0 ? item.groupImage : item.profileImage))
                    .apply(mRequestOptions)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            holder.mImgChatImage.setImageDrawable(resource);
                        }
                    });
            holder.mLblChatMessage.setSelected(true);

            if (item.unreadCount > 0) {
                holder.mLblUnreadMessage.setText(String.valueOf(item.unreadCount));
                holder.mLblUnreadMessage.setVisibility(View.VISIBLE);
            } else {
                holder.mLblUnreadMessage.setVisibility(View.GONE);
            }


        } catch (Exception e) {
            M.log(e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    class ChatContactsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgChatImage)
        CircleImageView mImgChatImage;
        @BindView(R.id.lblChatTitle)
        AppCompatTextView mLblChatTitle;
        @BindView(R.id.lblChatMessage)
        AppCompatTextView mLblChatMessage;
        @BindView(R.id.lblChatMessageTime)
        AppCompatTextView mLblChatMessageTime;
        @BindView(R.id.lblUnreadMessage)
        AppCompatTextView mLblUnreadMessage;

        ChatContactsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        @OnClick
        public void onClick() {
            int index = getAdapterPosition();
            if (index < 0 || index >= items.size()) return;
            ChatListItem item = items.get(index);
            if (item == null) return;
            item.unreadCount = 0;
            notifyItemChanged(index);
            Intent chatDetailIntent = new Intent(mContext, ChatDetailActivity.class);
            chatDetailIntent.putExtra(FragmentChatDetail.USERS, item.id);
            chatDetailIntent.putExtra(FragmentChatDetail.GROUP_NAME, item.groupName);
            chatDetailIntent.putExtra(FragmentChatDetail.NEW, "old");
            mContext.startActivity(chatDetailIntent);
        }

    }

}