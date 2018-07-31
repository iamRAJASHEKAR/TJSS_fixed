// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChatDetailAdapter$TextChatViewHolder_ViewBinding extends ChatDetailAdapter$BaseViewHolder_ViewBinding {
  private ChatDetailAdapter.TextChatViewHolder target;

  @UiThread
  public ChatDetailAdapter$TextChatViewHolder_ViewBinding(ChatDetailAdapter.TextChatViewHolder target,
      View source) {
    super(target, source);

    this.target = target;

    target.mLblChatMessage = Utils.findRequiredViewAsType(source, R.id.lblChatMessage, "field 'mLblChatMessage'", AppCompatTextView.class);
    target.mLblChatTime = Utils.findOptionalViewAsType(source, R.id.lblChatTime, "field 'mLblChatTime'", AppCompatTextView.class);
    target.mLblChatSenderName = Utils.findOptionalViewAsType(source, R.id.lblChatSenderName, "field 'mLblChatSenderName'", AppCompatTextView.class);
    target.mSeparator = source.findViewById(R.id.separator);
  }

  @Override
  public void unbind() {
    ChatDetailAdapter.TextChatViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblChatMessage = null;
    target.mLblChatTime = null;
    target.mLblChatSenderName = null;
    target.mSeparator = null;

    super.unbind();
  }
}
