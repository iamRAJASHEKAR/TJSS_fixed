// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.payment;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentPurchaseService_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentPurchaseService target;

  @UiThread
  public FragmentPurchaseService_ViewBinding(FragmentPurchaseService target, View source) {
    super(target, source);

    this.target = target;

    target.mLstServices = Utils.findRequiredViewAsType(source, R.id.lstServices, "field 'mLstServices'", RecyclerView.class);
  }

  @Override
  public void unbind() {
    FragmentPurchaseService target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstServices = null;

    super.unbind();
  }
}
