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

public class CheckInHistoryAdapter$NoContentViewHolder_ViewBinding implements Unbinder {
  private CheckInHistoryAdapter.NoContentViewHolder target;

  @UiThread
  public CheckInHistoryAdapter$NoContentViewHolder_ViewBinding(CheckInHistoryAdapter.NoContentViewHolder target,
      View source) {
    this.target = target;

    target.mLblError = Utils.findRequiredViewAsType(source, R.id.lblError, "field 'mLblError'", AppCompatTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CheckInHistoryAdapter.NoContentViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblError = null;
  }
}
