// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HelpAlertsListingAdapter_ViewBinding implements Unbinder {
  private HelpAlertsListingAdapter target;

  private View view2131755225;

  @UiThread
  public HelpAlertsListingAdapter_ViewBinding(final HelpAlertsListingAdapter target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.appCompatImageView, "method 'onViewClicked'");
    view2131755225 = view;
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
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131755225.setOnClickListener(null);
    view2131755225 = null;
  }
}
