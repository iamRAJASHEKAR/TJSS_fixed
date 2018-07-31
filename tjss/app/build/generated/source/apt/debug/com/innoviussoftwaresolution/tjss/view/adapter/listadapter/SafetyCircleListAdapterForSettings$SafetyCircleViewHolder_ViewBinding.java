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

public class SafetyCircleListAdapterForSettings$SafetyCircleViewHolder_ViewBinding implements Unbinder {
  private SafetyCircleListAdapterForSettings.SafetyCircleViewHolder target;

  private View viewSource;

  @UiThread
  public SafetyCircleListAdapterForSettings$SafetyCircleViewHolder_ViewBinding(final SafetyCircleListAdapterForSettings.SafetyCircleViewHolder target,
      View source) {
    this.target = target;

    target.mLblSafetyCircleName = Utils.findRequiredViewAsType(source, R.id.lblSafetyCircleName, "field 'mLblSafetyCircleName'", AppCompatTextView.class);
    viewSource = source;
    source.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SafetyCircleListAdapterForSettings.SafetyCircleViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblSafetyCircleName = null;

    viewSource.setOnClickListener(null);
    viewSource = null;
  }
}
