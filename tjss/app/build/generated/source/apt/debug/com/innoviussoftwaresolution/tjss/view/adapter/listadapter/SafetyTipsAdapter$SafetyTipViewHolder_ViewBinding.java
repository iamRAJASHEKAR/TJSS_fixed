// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SafetyTipsAdapter$SafetyTipViewHolder_ViewBinding implements Unbinder {
  private SafetyTipsAdapter.SafetyTipViewHolder target;

  @UiThread
  public SafetyTipsAdapter$SafetyTipViewHolder_ViewBinding(SafetyTipsAdapter.SafetyTipViewHolder target,
      View source) {
    this.target = target;

    target.mLblSafetyTipNumber = Utils.findRequiredViewAsType(source, R.id.lblSafetyTipNumber, "field 'mLblSafetyTipNumber'", AppCompatTextView.class);
    target.mLblSafetyTipTitle = Utils.findRequiredViewAsType(source, R.id.lblSafetyTipTitle, "field 'mLblSafetyTipTitle'", AppCompatTextView.class);
    target.mLblSafetyTipDescription = Utils.findRequiredViewAsType(source, R.id.lblSafetyTipDescription, "field 'mLblSafetyTipDescription'", AppCompatTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SafetyTipsAdapter.SafetyTipViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblSafetyTipNumber = null;
    target.mLblSafetyTipTitle = null;
    target.mLblSafetyTipDescription = null;
  }
}
