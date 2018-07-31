// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.safetytip;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentSafetyTipSettings_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentSafetyTipSettings target;

  private View view2131755487;

  @UiThread
  public FragmentSafetyTipSettings_ViewBinding(final FragmentSafetyTipSettings target,
      View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mLstSafetyTipsCategories = Utils.findRequiredViewAsType(source, R.id.lstSafetyTipsCategories, "field 'mLstSafetyTipsCategories'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.btnSafetyTipCategoriesUpdate, "field 'mBtnSafetyTipCategoriesUpdate' and method 'onViewClicked'");
    target.mBtnSafetyTipCategoriesUpdate = Utils.castView(view, R.id.btnSafetyTipCategoriesUpdate, "field 'mBtnSafetyTipCategoriesUpdate'", AppCompatButton.class);
    view2131755487 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentSafetyTipSettings target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstSafetyTipsCategories = null;
    target.mBtnSafetyTipCategoriesUpdate = null;

    view2131755487.setOnClickListener(null);
    view2131755487 = null;

    super.unbind();
  }
}
