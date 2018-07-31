// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.helpalert;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentHelpAlertsListing_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentHelpAlertsListing target;

  @UiThread
  public FragmentHelpAlertsListing_ViewBinding(FragmentHelpAlertsListing target, View source) {
    super(target, source);

    this.target = target;

    target.mLstHelpAlertHistory = Utils.findRequiredViewAsType(source, R.id.lstCheckInHistory, "field 'mLstHelpAlertHistory'", RecyclerView.class);
  }

  @Override
  public void unbind() {
    FragmentHelpAlertsListing target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstHelpAlertHistory = null;

    super.unbind();
  }
}
