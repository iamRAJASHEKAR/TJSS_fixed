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

public class IncidentsAdapter$IncidentViewHolder_ViewBinding implements Unbinder {
  private IncidentsAdapter.IncidentViewHolder target;

  private View view2131755284;

  private View viewSource;

  @UiThread
  public IncidentsAdapter$IncidentViewHolder_ViewBinding(final IncidentsAdapter.IncidentViewHolder target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.lblIncidentTitle, "field 'mLblIncidentTitle' and method 'onClick'");
    target.mLblIncidentTitle = Utils.castView(view, R.id.lblIncidentTitle, "field 'mLblIncidentTitle'", AppCompatTextView.class);
    view2131755284 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.mLblIncidentTime = Utils.findRequiredViewAsType(source, R.id.lblIncidentTime, "field 'mLblIncidentTime'", AppCompatTextView.class);
    target.mLblIncidentType = Utils.findRequiredViewAsType(source, R.id.lblIncidentType, "field 'mLblIncidentType'", AppCompatTextView.class);
    target.mLblIncidentDescription = Utils.findRequiredViewAsType(source, R.id.lblIncidentDescription, "field 'mLblIncidentDescription'", AppCompatTextView.class);
    viewSource = source;
    source.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onItemClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    IncidentsAdapter.IncidentViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblIncidentTitle = null;
    target.mLblIncidentTime = null;
    target.mLblIncidentType = null;
    target.mLblIncidentDescription = null;

    view2131755284.setOnClickListener(null);
    view2131755284 = null;
    viewSource.setOnClickListener(null);
    viewSource = null;
  }
}
