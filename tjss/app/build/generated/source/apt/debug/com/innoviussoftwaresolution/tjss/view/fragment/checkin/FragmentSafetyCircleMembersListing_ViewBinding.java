// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.checkin;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentSafetyCircleMembersListing_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentSafetyCircleMembersListing target;

  @UiThread
  public FragmentSafetyCircleMembersListing_ViewBinding(FragmentSafetyCircleMembersListing target,
      View source) {
    super(target, source);

    this.target = target;

    target.mLstSafetyCircleMembers = Utils.findRequiredViewAsType(source, R.id.lstSafetyCircleMembers, "field 'mLstSafetyCircleMembers'", RecyclerView.class);
    target.mBtnAddNewMember = Utils.findRequiredViewAsType(source, R.id.btnAddNewMember, "field 'mBtnAddNewMember'", AppCompatTextView.class);
  }

  @Override
  public void unbind() {
    FragmentSafetyCircleMembersListing target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstSafetyCircleMembers = null;
    target.mBtnAddNewMember = null;

    super.unbind();
  }
}
