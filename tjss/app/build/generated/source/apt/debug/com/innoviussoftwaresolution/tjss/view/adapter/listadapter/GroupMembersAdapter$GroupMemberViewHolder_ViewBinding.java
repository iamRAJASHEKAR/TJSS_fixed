// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GroupMembersAdapter$GroupMemberViewHolder_ViewBinding implements Unbinder {
  private GroupMembersAdapter.GroupMemberViewHolder target;

  @UiThread
  public GroupMembersAdapter$GroupMemberViewHolder_ViewBinding(GroupMembersAdapter.GroupMemberViewHolder target,
      View source) {
    this.target = target;

    target.mImgGroupMemberImage = Utils.findRequiredViewAsType(source, R.id.imgGroupMemberImage, "field 'mImgGroupMemberImage'", CircleImageView.class);
    target.mLblGroupName = Utils.findRequiredViewAsType(source, R.id.lblGroupName, "field 'mLblGroupName'", AppCompatTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GroupMembersAdapter.GroupMemberViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImgGroupMemberImage = null;
    target.mLblGroupName = null;
  }
}
