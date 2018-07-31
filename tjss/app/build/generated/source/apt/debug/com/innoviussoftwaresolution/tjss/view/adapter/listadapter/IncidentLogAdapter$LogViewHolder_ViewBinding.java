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

public class IncidentLogAdapter$LogViewHolder_ViewBinding implements Unbinder {
  private IncidentLogAdapter.LogViewHolder target;

  @UiThread
  public IncidentLogAdapter$LogViewHolder_ViewBinding(IncidentLogAdapter.LogViewHolder target,
      View source) {
    this.target = target;

    target.mLblLogTime = Utils.findRequiredViewAsType(source, R.id.lblLogTime, "field 'mLblLogTime'", AppCompatTextView.class);
    target.mLblLogDescription = Utils.findRequiredViewAsType(source, R.id.lblLogDescription, "field 'mLblLogDescription'", AppCompatTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    IncidentLogAdapter.LogViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblLogTime = null;
    target.mLblLogDescription = null;
  }
}
