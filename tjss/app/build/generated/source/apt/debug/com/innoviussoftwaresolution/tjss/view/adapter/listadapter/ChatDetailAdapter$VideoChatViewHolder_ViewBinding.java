// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChatDetailAdapter$VideoChatViewHolder_ViewBinding extends ChatDetailAdapter$BaseViewHolder_ViewBinding {
  private ChatDetailAdapter.VideoChatViewHolder target;

  private View view2131755213;

  @UiThread
  public ChatDetailAdapter$VideoChatViewHolder_ViewBinding(final ChatDetailAdapter.VideoChatViewHolder target,
      View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mImgChatImage = Utils.findRequiredViewAsType(source, R.id.imgChatImage, "field 'mImgChatImage'", AppCompatImageView.class);
    target.mPgProgress = Utils.findRequiredViewAsType(source, R.id.pgProgress, "field 'mPgProgress'", ProgressBar.class);
    view = Utils.findRequiredView(source, R.id.btnPlay, "field 'mBtnPlay' and method 'onPlayClick'");
    target.mBtnPlay = Utils.castView(view, R.id.btnPlay, "field 'mBtnPlay'", AppCompatImageButton.class);
    view2131755213 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPlayClick();
      }
    });
  }

  @Override
  public void unbind() {
    ChatDetailAdapter.VideoChatViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImgChatImage = null;
    target.mPgProgress = null;
    target.mBtnPlay = null;

    view2131755213.setOnClickListener(null);
    view2131755213 = null;

    super.unbind();
  }
}
