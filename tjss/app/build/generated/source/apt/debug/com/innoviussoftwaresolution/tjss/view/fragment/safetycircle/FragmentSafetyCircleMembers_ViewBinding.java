// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.safetycircle;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentSafetyCircleMembers_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentSafetyCircleMembers target;

  private View view2131755483;

  @UiThread
  public FragmentSafetyCircleMembers_ViewBinding(final FragmentSafetyCircleMembers target,
      View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mLstSafetyCircleMembers = Utils.findRequiredViewAsType(source, R.id.lstSafetyCircleMembers, "field 'mLstSafetyCircleMembers'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.btnAddNewMember, "method 'onViewClicked'");
    view2131755483 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentSafetyCircleMembers target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstSafetyCircleMembers = null;

    view2131755483.setOnClickListener(null);
    view2131755483 = null;

    super.unbind();
  }
}
