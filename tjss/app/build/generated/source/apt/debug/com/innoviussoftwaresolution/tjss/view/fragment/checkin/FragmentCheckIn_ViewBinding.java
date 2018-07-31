// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.checkin;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentCheckIn_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentCheckIn target;

  @UiThread
  public FragmentCheckIn_ViewBinding(FragmentCheckIn target, View source) {
    super(target, source);

    this.target = target;

    target.mLstCheckInHistory = Utils.findRequiredViewAsType(source, R.id.lstCheckInHistory, "field 'mLstCheckInHistory'", RecyclerView.class);
  }

  @Override
  public void unbind() {
    FragmentCheckIn target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstCheckInHistory = null;

    super.unbind();
  }
}
