// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.home;

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

public class IncidentMarkerInfoWindowDialog_ViewBinding implements Unbinder {
  private IncidentMarkerInfoWindowDialog target;

  private View view2131755290;

  @UiThread
  public IncidentMarkerInfoWindowDialog_ViewBinding(final IncidentMarkerInfoWindowDialog target,
      View source) {
    this.target = target;

    View view;
    target.mLblIncidentDialogTitle = Utils.findRequiredViewAsType(source, R.id.lblIncidentDialogTitle, "field 'mLblIncidentDialogTitle'", AppCompatTextView.class);
    target.mLblIncidentDialogDescription = Utils.findRequiredViewAsType(source, R.id.lblIncidentDialogDescription, "field 'mLblIncidentDialogDescription'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.btnIncidentDialogViewMore, "method 'onViewClicked'");
    view2131755290 = view;
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
    IncidentMarkerInfoWindowDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblIncidentDialogTitle = null;
    target.mLblIncidentDialogDescription = null;

    view2131755290.setOnClickListener(null);
    view2131755290 = null;
  }
}
