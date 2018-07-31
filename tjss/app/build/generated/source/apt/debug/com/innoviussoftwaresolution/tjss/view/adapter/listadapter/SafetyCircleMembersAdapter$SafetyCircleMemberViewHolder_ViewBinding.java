// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SafetyCircleMembersAdapter$SafetyCircleMemberViewHolder_ViewBinding implements Unbinder {
  private SafetyCircleMembersAdapter.SafetyCircleMemberViewHolder target;

  private View view2131755330;

  private View viewSource;

  @UiThread
  public SafetyCircleMembersAdapter$SafetyCircleMemberViewHolder_ViewBinding(final SafetyCircleMembersAdapter.SafetyCircleMemberViewHolder target,
      View source) {
    this.target = target;

    View view;
    target.mLblCircleMemberName = Utils.findRequiredViewAsType(source, R.id.lblCircleMemberName, "field 'mLblCircleMemberName'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.chkCircleMemberIsAdmin, "field 'mChkCircleMemberIsAdmin' and method 'onClicked'");
    target.mChkCircleMemberIsAdmin = Utils.castView(view, R.id.chkCircleMemberIsAdmin, "field 'mChkCircleMemberIsAdmin'", AppCompatCheckBox.class);
    view2131755330 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClicked();
      }
    });
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
    SafetyCircleMembersAdapter.SafetyCircleMemberViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblCircleMemberName = null;
    target.mChkCircleMemberIsAdmin = null;

    view2131755330.setOnClickListener(null);
    view2131755330 = null;
    viewSource.setOnClickListener(null);
    viewSource = null;
  }
}
