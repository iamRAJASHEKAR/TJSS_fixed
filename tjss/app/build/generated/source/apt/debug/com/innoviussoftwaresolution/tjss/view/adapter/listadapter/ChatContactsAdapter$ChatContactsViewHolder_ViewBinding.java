// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChatContactsAdapter$ChatContactsViewHolder_ViewBinding implements Unbinder {
  private ChatContactsAdapter.ChatContactsViewHolder target;

  private View viewSource;

  @UiThread
  public ChatContactsAdapter$ChatContactsViewHolder_ViewBinding(final ChatContactsAdapter.ChatContactsViewHolder target,
      View source) {
    this.target = target;

    target.mImgChatImage = Utils.findRequiredViewAsType(source, R.id.imgChatImage, "field 'mImgChatImage'", CircleImageView.class);
    target.mLblChatTitle = Utils.findRequiredViewAsType(source, R.id.lblChatTitle, "field 'mLblChatTitle'", AppCompatTextView.class);
    target.mLblChatMessage = Utils.findRequiredViewAsType(source, R.id.lblChatMessage, "field 'mLblChatMessage'", AppCompatTextView.class);
    target.mLblChatMessageTime = Utils.findRequiredViewAsType(source, R.id.lblChatMessageTime, "field 'mLblChatMessageTime'", AppCompatTextView.class);
    target.mLblUnreadMessage = Utils.findRequiredViewAsType(source, R.id.lblUnreadMessage, "field 'mLblUnreadMessage'", AppCompatTextView.class);
    viewSource = source;
    source.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ChatContactsAdapter.ChatContactsViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImgChatImage = null;
    target.mLblChatTitle = null;
    target.mLblChatMessage = null;
    target.mLblChatMessageTime = null;
    target.mLblUnreadMessage = null;

    viewSource.setOnClickListener(null);
    viewSource = null;
  }
}
