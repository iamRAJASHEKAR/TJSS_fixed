// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChatDetailAdapter$AudioViewHolder_ViewBinding extends ChatDetailAdapter$BaseViewHolder_ViewBinding {
  private ChatDetailAdapter.AudioViewHolder target;

  private View view2131755213;

  @UiThread
  public ChatDetailAdapter$AudioViewHolder_ViewBinding(final ChatDetailAdapter.AudioViewHolder target,
      View source) {
    super(target, source);

    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnPlay, "field 'mBtnPlay' and method 'onClick'");
    target.mBtnPlay = Utils.castView(view, R.id.btnPlay, "field 'mBtnPlay'", AppCompatImageButton.class);
    view2131755213 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.mLblSenderName = Utils.findRequiredViewAsType(source, R.id.lblChatSenderName, "field 'mLblSenderName'", AppCompatTextView.class);
  }

  @Override
  public void unbind() {
    ChatDetailAdapter.AudioViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mBtnPlay = null;
    target.mLblSenderName = null;

    view2131755213.setOnClickListener(null);
    view2131755213 = null;

    super.unbind();
  }
}
