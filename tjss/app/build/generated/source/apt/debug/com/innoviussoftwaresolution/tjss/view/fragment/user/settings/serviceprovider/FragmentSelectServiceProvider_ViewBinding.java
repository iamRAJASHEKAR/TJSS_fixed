// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.serviceprovider;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentSelectServiceProvider_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentSelectServiceProvider target;

  private View view2131755496;

  @UiThread
  public FragmentSelectServiceProvider_ViewBinding(final FragmentSelectServiceProvider target,
      View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mLblTitle = Utils.findRequiredViewAsType(source, R.id.lblTitle, "field 'mLblTitle'", AppCompatTextView.class);
    target.mLstData = Utils.findRequiredViewAsType(source, R.id.lstData, "field 'mLstData'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.btnServiceProvidersSettingsProceed, "method 'onViewClicked'");
    view2131755496 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentSelectServiceProvider target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblTitle = null;
    target.mLstData = null;

    view2131755496.setOnClickListener(null);
    view2131755496 = null;

    super.unbind();
  }
}
