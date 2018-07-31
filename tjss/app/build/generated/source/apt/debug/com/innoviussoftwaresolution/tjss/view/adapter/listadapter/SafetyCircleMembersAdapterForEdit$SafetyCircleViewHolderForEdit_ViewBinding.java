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
import java.lang.IllegalStateException;
import java.lang.Override;

public class SafetyCircleMembersAdapterForEdit$SafetyCircleViewHolderForEdit_ViewBinding implements Unbinder {
  private SafetyCircleMembersAdapterForEdit.SafetyCircleViewHolderForEdit target;

  private View view2131755331;

  @UiThread
  public SafetyCircleMembersAdapterForEdit$SafetyCircleViewHolderForEdit_ViewBinding(final SafetyCircleMembersAdapterForEdit.SafetyCircleViewHolderForEdit target,
      View source) {
    this.target = target;

    View view;
    target.mLblSafetyCircleName = Utils.findRequiredViewAsType(source, R.id.lblSafetyCircleName, "field 'mLblSafetyCircleName'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.btnDeleteUser, "method 'onViewClicked'");
    view2131755331 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SafetyCircleMembersAdapterForEdit.SafetyCircleViewHolderForEdit target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblSafetyCircleName = null;

    view2131755331.setOnClickListener(null);
    view2131755331 = null;
  }
}
