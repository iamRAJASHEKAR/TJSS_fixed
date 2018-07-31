// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.incident;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentIncidentListing_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentIncidentListing target;

  @UiThread
  public FragmentIncidentListing_ViewBinding(FragmentIncidentListing target, View source) {
    super(target, source);

    this.target = target;

    target.mLstIncidents = Utils.findRequiredViewAsType(source, R.id.lstIncidents, "field 'mLstIncidents'", RecyclerView.class);
  }

  @Override
  public void unbind() {
    FragmentIncidentListing target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstIncidents = null;

    super.unbind();
  }
}
