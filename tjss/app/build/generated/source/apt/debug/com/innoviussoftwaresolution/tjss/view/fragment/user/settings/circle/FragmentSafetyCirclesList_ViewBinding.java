// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.circle;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentSafetyCirclesList_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentSafetyCirclesList target;

  @UiThread
  public FragmentSafetyCirclesList_ViewBinding(FragmentSafetyCirclesList target, View source) {
    super(target, source);

    this.target = target;

    target.mLstSafetyCircles = Utils.findRequiredViewAsType(source, R.id.lstSafetyCircles, "field 'mLstSafetyCircles'", RecyclerView.class);
  }

  @Override
  public void unbind() {
    FragmentSafetyCirclesList target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstSafetyCircles = null;

    super.unbind();
  }
}
