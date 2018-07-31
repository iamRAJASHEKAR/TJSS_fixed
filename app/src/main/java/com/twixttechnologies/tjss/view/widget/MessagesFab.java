package com.twixttechnologies.tjss.view.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.innoviussoftwaresolution.tjss.R;


/**
 * @author Sony Raj on 22-07-17.
 */

public class MessagesFab extends FrameLayout implements View.OnClickListener {

    FloatingActionButton groupChat;
    FloatingActionButton friendChat;
    private ObjectAnimator mExpandAnimatorX, mExpandAnimatorY;
    private ObjectAnimator mCollapseAnimatorX, mCollapseAnimatorY;
    private AppCompatImageView mArcImage;
    private FloatingActionButton btn;
    private boolean mIsExpanded = false;
    private AnimatorSet mExpandAnimatorSet, mCollapseAnimatorSet;

    public MessagesFab(@NonNull Context context) {
        this(context, null);
    }

    public MessagesFab(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessagesFab(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setOnGroupClickListener(OnClickListener onGroupClickListener) {
        this.groupChat.setOnClickListener(onGroupClickListener);
    }

    public void setOnFriendClickListener(OnClickListener onFriendClickListener) {
        this.friendChat.setOnClickListener(onFriendClickListener);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.extra_fab_menu, this, true);
        FabMenu menu = (FabMenu) view.findViewById(R.id.fabMenu);
        btn = menu.getMenuButton();
        if (btn != null)
            btn.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                        if (mIsExpanded)
                            collapse();
                        else
                            expand();
                    }
                    return false;

                }
            });
        mArcImage = (AppCompatImageView) view.findViewById(R.id.arcImage);

        mArcImage.setVisibility(INVISIBLE);

        groupChat = (FloatingActionButton) view.findViewById(R.id.fabGroupChat);
        friendChat = (FloatingActionButton) view.findViewById(R.id.fabFriendChat);


    }

    private void expand() {
        mArcImage.setVisibility(VISIBLE);
        mArcImage.setPivotX(mArcImage.getRight());
        mArcImage.setPivotY(mArcImage.getBottom());
        initializeAnimator();
        if (mExpandAnimatorX == null) {
            mExpandAnimatorX = ObjectAnimator.ofFloat(mArcImage, View.SCALE_X, 1);
        }
        if (mExpandAnimatorY == null) {
            mExpandAnimatorY = ObjectAnimator.ofFloat(mArcImage, View.SCALE_Y, 1);
        }

        mExpandAnimatorSet.playTogether(mExpandAnimatorX, mExpandAnimatorY);
        mExpandAnimatorSet.start();
        mIsExpanded = true;
    }


    public void open() {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis() + 100;
        float x = 0.0f;
        float y = 0.0f;
        int metaState = 0;
        MotionEvent motionEvent = MotionEvent.obtain(
                downTime,
                eventTime,
                MotionEvent.ACTION_UP,
                x,
                y,
                metaState
        );
        btn.dispatchTouchEvent(motionEvent);
    }


    private void initializeAnimator() {
        if (mExpandAnimatorSet != null)
            if (mExpandAnimatorSet.isStarted() || mExpandAnimatorSet.isRunning()) {
                mExpandAnimatorSet.cancel();
            }

        if (mCollapseAnimatorSet != null)
            if (mCollapseAnimatorSet.isStarted() || mCollapseAnimatorSet.isRunning()) {
                mCollapseAnimatorSet.cancel();
            }

        mExpandAnimatorSet = new AnimatorSet();

        mCollapseAnimatorSet = new AnimatorSet();

    }

    private void collapse() {
        mArcImage.setVisibility(VISIBLE);
        mArcImage.setPivotX(mArcImage.getRight());
        mArcImage.setPivotY(mArcImage.getBottom());
        initializeAnimator();
        if (mCollapseAnimatorX == null) {
            mCollapseAnimatorX = ObjectAnimator.ofFloat(mArcImage, View.SCALE_X, 0.01f);
        }
        if (mCollapseAnimatorY == null) {
            mCollapseAnimatorY = ObjectAnimator.ofFloat(mArcImage, View.SCALE_Y, 0.01f);
        }
        mCollapseAnimatorSet.playTogether(mCollapseAnimatorX, mCollapseAnimatorY);
        mCollapseAnimatorSet.start();
        mIsExpanded = false;
    }


    @Override
    public void onClick(View v) {
        if (mIsExpanded) {
            collapse();
        } else {
            expand();
        }
    }
}
