// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SafetyTipCategoriesAdapter$SafetyTipCategoriesViewHolder_ViewBinding implements Unbinder {
  private SafetyTipCategoriesAdapter.SafetyTipCategoriesViewHolder target;

  @UiThread
  public SafetyTipCategoriesAdapter$SafetyTipCategoriesViewHolder_ViewBinding(SafetyTipCategoriesAdapter.SafetyTipCategoriesViewHolder target,
      View source) {
    this.target = target;

    target.mChkSafetyTipCategory = Utils.findRequiredViewAsType(source, R.id.chkSafetyTipCategory, "field 'mChkSafetyTipCategory'", AppCompatCheckBox.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SafetyTipCategoriesAdapter.SafetyTipCategoriesViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mChkSafetyTipCategory = null;
  }
}
