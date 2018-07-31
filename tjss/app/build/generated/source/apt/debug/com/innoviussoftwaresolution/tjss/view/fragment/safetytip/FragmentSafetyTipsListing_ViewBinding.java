// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.safetytip;

import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentSafetyTipsListing_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentSafetyTipsListing target;

  @UiThread
  public FragmentSafetyTipsListing_ViewBinding(FragmentSafetyTipsListing target, View source) {
    super(target, source);

    this.target = target;

    target.mTabSafetyTips = Utils.findRequiredViewAsType(source, R.id.tabSafetyTips, "field 'mTabSafetyTips'", TabLayout.class);
    target.mLstSafetyTips = Utils.findRequiredViewAsType(source, R.id.lstSafetyTips, "field 'mLstSafetyTips'", RecyclerView.class);
  }

  @Override
  public void unbind() {
    FragmentSafetyTipsListing target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTabSafetyTips = null;
    target.mLstSafetyTips = null;

    super.unbind();
  }
}
