// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChatDetailAdapter$CheckInViewHolder_ViewBinding implements Unbinder {
  private ChatDetailAdapter.CheckInViewHolder target;

  @UiThread
  public ChatDetailAdapter$CheckInViewHolder_ViewBinding(ChatDetailAdapter.CheckInViewHolder target,
      View source) {
    this.target = target;

    target.mImgChatCheckInLocationImage = Utils.findRequiredViewAsType(source, R.id.imgChatCheckInLocationImage, "field 'mImgChatCheckInLocationImage'", AppCompatImageView.class);
    target.mLblChatCheckInLocation = Utils.findRequiredViewAsType(source, R.id.lblChatCheckInLocation, "field 'mLblChatCheckInLocation'", AppCompatTextView.class);
    target.mImgChatUserImage = Utils.findRequiredViewAsType(source, R.id.imgChatUserImage, "field 'mImgChatUserImage'", CircleImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChatDetailAdapter.CheckInViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImgChatCheckInLocationImage = null;
    target.mLblChatCheckInLocation = null;
    target.mImgChatUserImage = null;
  }
}
