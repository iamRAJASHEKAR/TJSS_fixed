// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChatDetailAdapter$ImageChatViewHolder_ViewBinding extends ChatDetailAdapter$BaseViewHolder_ViewBinding {
  private ChatDetailAdapter.ImageChatViewHolder target;

  @UiThread
  public ChatDetailAdapter$ImageChatViewHolder_ViewBinding(ChatDetailAdapter.ImageChatViewHolder target,
      View source) {
    super(target, source);

    this.target = target;

    target.mImgChatImage = Utils.findRequiredViewAsType(source, R.id.imgChatImage, "field 'mImgChatImage'", AppCompatImageView.class);
    target.mProgressBar = Utils.findRequiredViewAsType(source, R.id.pgProgress, "field 'mProgressBar'", ProgressBar.class);
  }

  @Override
  public void unbind() {
    ChatDetailAdapter.ImageChatViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImgChatImage = null;
    target.mProgressBar = null;

    super.unbind();
  }
}
