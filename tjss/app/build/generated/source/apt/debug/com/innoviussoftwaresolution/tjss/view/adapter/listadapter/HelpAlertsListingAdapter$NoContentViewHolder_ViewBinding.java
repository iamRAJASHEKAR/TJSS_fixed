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

public class HelpAlertsListingAdapter$NoContentViewHolder_ViewBinding implements Unbinder {
  private HelpAlertsListingAdapter.NoContentViewHolder target;

  @UiThread
  public HelpAlertsListingAdapter$NoContentViewHolder_ViewBinding(HelpAlertsListingAdapter.NoContentViewHolder target,
      View source) {
    this.target = target;

    target.mLblError = Utils.findRequiredViewAsType(source, R.id.lblError, "field 'mLblError'", AppCompatTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HelpAlertsListingAdapter.NoContentViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblError = null;
  }
}
