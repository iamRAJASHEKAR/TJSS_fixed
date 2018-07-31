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

public class CheckInHistoryAdapter$CheckInViewHolder_ViewBinding implements Unbinder {
  private CheckInHistoryAdapter.CheckInViewHolder target;

  @UiThread
  public CheckInHistoryAdapter$CheckInViewHolder_ViewBinding(CheckInHistoryAdapter.CheckInViewHolder target,
      View source) {
    this.target = target;

    target.mLblCheckInLocationName = Utils.findRequiredViewAsType(source, R.id.lblCheckInLocationName, "field 'mLblCheckInLocationName'", AppCompatTextView.class);
    target.mLblCheckInLocationTime = Utils.findRequiredViewAsType(source, R.id.lblCheckInLocationTime, "field 'mLblCheckInLocationTime'", AppCompatTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CheckInHistoryAdapter.CheckInViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblCheckInLocationName = null;
    target.mLblCheckInLocationTime = null;
  }
}
