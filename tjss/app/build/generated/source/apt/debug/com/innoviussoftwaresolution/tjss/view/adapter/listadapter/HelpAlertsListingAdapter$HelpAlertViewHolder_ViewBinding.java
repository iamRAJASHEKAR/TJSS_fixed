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

public class HelpAlertsListingAdapter$HelpAlertViewHolder_ViewBinding implements Unbinder {
  private HelpAlertsListingAdapter.HelpAlertViewHolder target;

  private View viewSource;

  @UiThread
  public HelpAlertsListingAdapter$HelpAlertViewHolder_ViewBinding(final HelpAlertsListingAdapter.HelpAlertViewHolder target,
      View source) {
    this.target = target;

    target.mLblHelpAlertLocationName = Utils.findRequiredViewAsType(source, R.id.lblHelpAlertLocationName, "field 'mLblHelpAlertLocationName'", AppCompatTextView.class);
    target.mLblHelpAlertInLocationTime = Utils.findRequiredViewAsType(source, R.id.lblHelpAlertInLocationTime, "field 'mLblHelpAlertInLocationTime'", AppCompatTextView.class);
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
    HelpAlertsListingAdapter.HelpAlertViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblHelpAlertLocationName = null;
    target.mLblHelpAlertInLocationTime = null;

    viewSource.setOnClickListener(null);
    viewSource = null;
  }
}
