package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bugsnag.android.Bugsnag;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.internal.ChatMessage;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Sony Raj on 03-10-17.
 */
public class ChatDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int LEFT = 776;
    private static final int RIGHT = 409;
    private static final int CHECK_IN = 692;
    private static final int CHAT_IMAGE_RIGHT = 790;
    private static final int CHAT_IMAGE_LEFT = 665;
    private static final int CHAT_VIDEO_RIGHT = 405;
    private static final int CHAT_VIDEO_LEFT = 982;
    private static final int LOADING = 96;
    private static final int DELETED_MESSAGE_LEFT = 93;
    private static final int DELETED_MESSAGE_RIGHT = 355;
    private static final int AUDIO_MESSAGE_LEFT = 965;
    private static final int AUDIO_MESSAGE_RIGHT = 843;

    private final LocalDateTime today;

    private final Context context;
    private final RequestManager mRequestManager;
    private List<ChatMessage> items = null;
    private boolean mLastIsLeft = true;
    private boolean mGotFirst = false;
    private int padding;
    private String mCurrentUser;
    private String mBaseUrl;
    private final RecyclerView mRecyclerView;

    private OnItemDeletedListener mDeleteListener;

    public ChatMessage getMessageAt(int index) {
        if (index < 0 || index >= items.size()) return null;
        return items.get(index);
    }

    public ChatDetailAdapter(Context context, RecyclerView recyclerView) {
        items=new ArrayList<ChatMessage>();
        this.context = context;
        mRecyclerView = recyclerView;
        today = LocalDateTime.now();
        mCurrentUser = PreferenceHelper.getString(context, PreferenceHelper.KEY_USER_ID, "");
        mBaseUrl = context.getResources().getString(R.string.image_base_url);
        mRequestManager = Glide.with(context);
        RequestOptions mRequestOptions = new RequestOptions().override(24, 24);
        mRequestOptions.apply(mRequestOptions);
        mRequestOptions.apply(RequestOptions.circleCropTransform());
        float scale = context.getResources().getDisplayMetrics().density;
        padding = (int) (32 * scale + 0.5f);
    }

    public void setDeleteListener(OnItemDeletedListener deleteListener) {
        mDeleteListener = deleteListener;
    }

    public void setMessages(List<ChatMessage> messages) {
        items = messages;
        notifyDataSetChanged();
    }

    public void addMessage(ChatMessage message) {
        items.add(message);
        notifyItemInserted(items.size() - 1);
        //notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOADING) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.extra_loading_view, parent, false);
            return new LoadingViewHolder(v);
        } else if (viewType == LEFT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.extra_chat_item_from_left, parent, false);
            return new TextChatViewHolder(v);
        } else if (viewType == RIGHT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.extra_chat_item_from_right, parent, false);
            return new TextChatViewHolder(v);
        } else if (viewType == CHECK_IN) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.extra_chat_check_in_item, parent, false);
            return new CheckInViewHolder(v);
        } else if (viewType == CHAT_IMAGE_RIGHT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.extra_chat_item_image_from_right, parent, false);
            return new ImageChatViewHolder(v);
        } else if (viewType == CHAT_IMAGE_LEFT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.extra_chat_item_image_from_left, parent, false);
            return new ImageChatViewHolder(v);
        } else if (viewType == CHAT_VIDEO_LEFT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.extra_chat_item_video_from_left, parent, false);
            return new VideoChatViewHolder(v);
        } else if (viewType == CHAT_VIDEO_RIGHT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.extra_chat_item_video_from_right, parent, false);
            return new VideoChatViewHolder(v);
        } else if (viewType == DELETED_MESSAGE_LEFT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.extra_chat_item_deleted_from_left, parent, false);
            return new DeletedMessageViewHolder(v);
        } else if (viewType == DELETED_MESSAGE_RIGHT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.extra_chat_item_deleted_from_right, parent, false);
            return new DeletedMessageViewHolder(v);
        } else if (viewType == AUDIO_MESSAGE_LEFT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.extra_audio_item_left, parent, false);
            return new AudioViewHolder(v);
        } else if (viewType == AUDIO_MESSAGE_RIGHT) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.extra_audio_item_right, parent, false);
            return new AudioViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (items == null) return;
        final ChatMessage item = items.get(position);
        String senderId = item.fromId;
        if (holder instanceof TextChatViewHolder) {
            final TextChatViewHolder viewHolder = (TextChatViewHolder) holder;
            if (senderId.equals(mCurrentUser)) {
                viewHolder.mLblChatMessage.setText(item.content);
            } else {
                viewHolder.mLblChatMessage.setText(item.content);
                viewHolder.mLblChatSenderName.setVisibility(View.VISIBLE);
                viewHolder.mLblChatTime.setVisibility(View.VISIBLE);
                viewHolder.mSeparator.setVisibility(View.VISIBLE);
                viewHolder.mLblChatSenderName.setText(item.senderName);

                DateTime dateTime = new DateTime(item.createdAt);

                LocalDateTime localDateTime = LocalDateTime.fromDateFields(dateTime.toDate());
                int days = Days.daysBetween(localDateTime, today).getDays();
                if (days <= 0) {
                    //message send today
                    viewHolder.mLblChatTime.setText(localDateTime.toString(DateTimeFormat.shortTime()));
                } else {
                    //message is at-least one day old
                    viewHolder.mLblChatTime.setText(localDateTime.toString(DateTimeFormat.shortDateTime()));
                }
            }
        } else if (holder instanceof CheckInViewHolder) {
            String[] parts = item.content.split(";");
            mRequestManager.load(parts[0])
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            ((CheckInViewHolder) holder).mImgChatCheckInLocationImage.setImageDrawable(resource);
                        }
                    });

            mRequestManager.load(mBaseUrl + item.senderImage)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                            ((CheckInViewHolder) holder).mImgChatUserImage.setImageDrawable(resource);
                        }
                    });
            if (parts.length == 2)
                ((CheckInViewHolder) holder).mLblChatCheckInLocation.setText(parts[1]);
        } else if (holder instanceof ImageChatViewHolder) {
            final ImageChatViewHolder viewHolder = (ImageChatViewHolder) holder;
            if (senderId.equals(mCurrentUser)) {
                viewHolder.mProgressBar.setProgress(item.progress);
                viewHolder.mProgressBar.setVisibility(item.progress < 100 ? View.VISIBLE : View.INVISIBLE);
                //if (item.fromId.equals(mCurrentUser))
                mRequestManager
                        .load(context.getString(R.string.base_url) + item.content)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                viewHolder.mImgChatImage.setImageDrawable(resource);
                                if (item.content.contains("chat"))
                                    item.progress = 100;
                                viewHolder.mProgressBar.setVisibility(View.GONE);
                                try {
                                    mRecyclerView.scrollToPosition(getItemCount());
                                } catch (Exception e) {
                                    Bugsnag.notify(new RuntimeException(e));
                                    M.log(e.getMessage());
                                }
                            }
                        });
            } else {
                viewHolder.mProgressBar.setProgress(item.progress);
                viewHolder.mProgressBar.setVisibility(item.progress < 100 ? View.VISIBLE : View.INVISIBLE);
                //if (item.fromId.equals(mCurrentUser))
                mRequestManager
                        .load(context.getString(R.string.base_url) + item.content)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                viewHolder.mImgChatImage.setImageDrawable(resource);
                                if (item.content.contains("chat"))
                                    item.progress = 100;
                                viewHolder.mProgressBar.setVisibility(View.GONE);
                                try {
                                    mRecyclerView.scrollToPosition(getItemCount());
                                } catch (Exception e) {
                                    Bugsnag.notify(new RuntimeException(e));
                                    M.log(e.getMessage());
                                }
                            }
                        });
            }
        } else if (holder instanceof VideoChatViewHolder) {
            final VideoChatViewHolder videoChatViewHolder = (VideoChatViewHolder) holder;
            if (senderId.equals(mCurrentUser)) {

                videoChatViewHolder.mPgProgress.setProgress(item.progress);
                videoChatViewHolder.mPgProgress.setVisibility(item.progress < 100 ? View.VISIBLE : View.GONE);
                videoChatViewHolder.mBtnPlay.setVisibility(View.VISIBLE);
                String path;
                if (item.id <= 0)
                    path = item.thumbNail;
                else
                    path = context.getString(R.string.base_url) + item.thumbNail;

                mRequestManager
                        .asBitmap()
                        .load(path)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                videoChatViewHolder.mImgChatImage.setImageBitmap(resource);
                                if (item.content.contains("chat"))
                                    item.progress = 100;
                                videoChatViewHolder.mPgProgress.setVisibility(View.GONE);
                                videoChatViewHolder.mBtnPlay.setVisibility(View.VISIBLE);
                                try {
                                    mRecyclerView.scrollToPosition(getItemCount());
                                } catch (Exception e) {
                                    Bugsnag.notify(new RuntimeException(e));
                                    M.log(e.getMessage());
                                }
                            }
                        });
            } else {
                videoChatViewHolder.mPgProgress.setProgress(item.progress);
                videoChatViewHolder.mPgProgress.setVisibility(item.progress < 100 ? View.VISIBLE : View.GONE);
                videoChatViewHolder.mBtnPlay.setVisibility(View.VISIBLE);
                String path;
                if (item.id <= 0)
                    path = item.thumbNail;
                else
                    path = context.getString(R.string.base_url) + item.thumbNail;

                mRequestManager
                        .asBitmap()
                        .load(path)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                videoChatViewHolder.mImgChatImage.setImageBitmap(resource);
                                if (item.content.contains("chat"))
                                    item.progress = 100;
                                videoChatViewHolder.mPgProgress.setVisibility(View.GONE);
                                videoChatViewHolder.mBtnPlay.setVisibility(View.VISIBLE);
                                try {
                                    mRecyclerView.scrollToPosition(getItemCount());
                                } catch (Exception e) {
                                    Bugsnag.notify(new RuntimeException(e));
                                    M.log(e.getMessage());
                                }
                            }
                        });
            }
        } else if (holder instanceof AudioViewHolder) {
            AudioViewHolder audioViewHolder = (AudioViewHolder) holder;
            audioViewHolder.mLblSenderName.setText(item.senderName);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items == null) {
            return LOADING;
        }

        ChatMessage chatMessage = items.get(position);
        if (chatMessage.fromId.equals(mCurrentUser)) {
            if (chatMessage.messageType.equals("1"))
                return RIGHT;
            else if (chatMessage.messageType.equals("2"))
                return CHECK_IN;
            else if (chatMessage.messageType.equals("3"))
                return CHAT_IMAGE_RIGHT;
            else if (chatMessage.messageType.equals("4"))
                return CHAT_VIDEO_RIGHT;
            else if (chatMessage.messageType.equals("5"))
                return DELETED_MESSAGE_RIGHT;
            else
                return AUDIO_MESSAGE_RIGHT;
        } else {
            if (chatMessage.messageType.equals("1"))
                return LEFT;
            else if (chatMessage.messageType.equals("2"))
                return CHECK_IN;
            else if (chatMessage.messageType.equals("3"))
                return CHAT_IMAGE_LEFT;
            else if (chatMessage.messageType.equals("4"))
                return CHAT_VIDEO_LEFT;
            else if (chatMessage.messageType.equals("5"))
                return DELETED_MESSAGE_LEFT;
            else
                return AUDIO_MESSAGE_LEFT;
        }
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 1;
        }
        return items.size();
    }

    public interface OnItemDeletedListener {
        void onItemDeleted(ChatMessage chatMessage);
    }

    //New ViewHolders for reducing calls to "onCreateViewHolder"
    //Should have done earlier :-P

    abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        //can be null if the message is not mine
        @Nullable
        @BindView(R.id.btnDeleteMessage)
        AppCompatImageButton mBtnDeleteMessage;

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        @OnClick
        public void onClick() {
            if (mBtnDeleteMessage == null) return;
            mBtnDeleteMessage.setVisibility(mBtnDeleteMessage.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        }

        @Optional
        @OnClick(R.id.btnDeleteMessage)
        public void onDeleteClicked() {
            int index = getAdapterPosition();
            if (index < 0) return;
            ChatMessage chatMessage = items.get(index);
            if (chatMessage == null) return;
            chatMessage.messageType = "5";
            if (mDeleteListener != null)
                mDeleteListener.onItemDeleted(chatMessage);
            notifyItemChanged(index);
        }

    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {

        LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class TextChatViewHolder extends BaseViewHolder {

        @BindView(R.id.lblChatMessage)
        AppCompatTextView mLblChatMessage;
        @Nullable
        @BindView(R.id.lblChatTime)
        AppCompatTextView mLblChatTime;
        @Nullable
        @BindView(R.id.lblChatSenderName)
        AppCompatTextView mLblChatSenderName;
        @Nullable
        @BindView(R.id.separator)
        View mSeparator;


        TextChatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (mBtnDeleteMessage != null)
                mBtnDeleteMessage.setVisibility(View.GONE);
        }

    }

    class CheckInViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.imgChatCheckInLocationImage)
        AppCompatImageView mImgChatCheckInLocationImage;
        @BindView(R.id.lblChatCheckInLocation)
        AppCompatTextView mLblChatCheckInLocation;
        @BindView(R.id.imgChatUserImage)
        CircleImageView mImgChatUserImage;

        CheckInViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ImageChatViewHolder extends BaseViewHolder {

        @BindView(R.id.imgChatImage)
        AppCompatImageView mImgChatImage;
        @BindView(R.id.pgProgress)
        ProgressBar mProgressBar;

        ImageChatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (mBtnDeleteMessage != null)
                mBtnDeleteMessage.setVisibility(View.GONE);
        }


    }

    class VideoChatViewHolder extends BaseViewHolder {

        @BindView(R.id.imgChatImage)
        AppCompatImageView mImgChatImage;
        @BindView(R.id.pgProgress)
        ProgressBar mPgProgress;
        @BindView(R.id.btnPlay)
        AppCompatImageButton mBtnPlay;


         VideoChatViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mBtnDeleteMessage.setVisibility(View.GONE);
        }

        @OnClick(R.id.btnPlay)
        public void onPlayClick() {
            int index = getAdapterPosition();
            if (index < 0) return;
            ChatMessage chatMessage = items.get(index);
            if (chatMessage == null) return;
            String url = context.getString(R.string.base_url) + chatMessage.content;
            Intent videoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            videoIntent.setDataAndType(Uri.parse(url), "video/*");
            context.startActivity(Intent.createChooser(videoIntent, "Play Video"));
        }


    }

    class AudioViewHolder extends BaseViewHolder {

        @BindView(R.id.btnPlay)
        AppCompatImageButton mBtnPlay;

        @BindView(R.id.lblChatSenderName)
        AppCompatTextView mLblSenderName;

         AudioViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
             if (mBtnDeleteMessage != null)
                 mBtnDeleteMessage.setVisibility(View.GONE);
        }

        @OnClick(R.id.btnPlay)
        public void onClick(View view) {
            int index = getAdapterPosition();
            if (index < 0) return;
            ChatMessage chatMessage = items.get(index);
            if (chatMessage == null) return;
            String url = context.getString(R.string.base_url) + chatMessage.content;
            Intent videoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            videoIntent.setDataAndType(Uri.parse(url), "audio/*");
            context.startActivity(Intent.createChooser(videoIntent, "Play Audio"));
        }

    }

    class DeletedMessageViewHolder extends BaseViewHolder {

        public DeletedMessageViewHolder(View itemView) {
            super(itemView);
        }
    }

}