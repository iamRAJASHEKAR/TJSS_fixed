// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.message;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentGroupMembers_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentGroupMembers target;

  @UiThread
  public FragmentGroupMembers_ViewBinding(FragmentGroupMembers target, View source) {
    super(target, source);

    this.target = target;

    target.mLstGroupMembers = Utils.findRequiredViewAsType(source, R.id.lstGroupMembers, "field 'mLstGroupMembers'", RecyclerView.class);
  }

  @Override
  public void unbind() {
    FragmentGroupMembers target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstGroupMembers = null;

    super.unbind();
  }
}
