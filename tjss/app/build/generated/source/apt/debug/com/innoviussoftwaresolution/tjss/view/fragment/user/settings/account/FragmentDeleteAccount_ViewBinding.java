// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.account;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentDeleteAccount_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentDeleteAccount target;

  private View view2131755397;

  @UiThread
  public FragmentDeleteAccount_ViewBinding(final FragmentDeleteAccount target, View source) {
    super(target, source);

    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnDeleteAccount, "field 'mBtnDeleteAccount' and method 'onViewClicked'");
    target.mBtnDeleteAccount = Utils.castView(view, R.id.btnDeleteAccount, "field 'mBtnDeleteAccount'", AppCompatButton.class);
    view2131755397 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentDeleteAccount target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mBtnDeleteAccount = null;

    view2131755397.setOnClickListener(null);
    view2131755397 = null;

    super.unbind();
  }
}
