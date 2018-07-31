// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.account;

import android.support.annotation.UiThread;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentAccountSettings_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentAccountSettings target;

  private View view2131755355;

  private View view2131755356;

  private View view2131755357;

  @UiThread
  public FragmentAccountSettings_ViewBinding(final FragmentAccountSettings target, View source) {
    super(target, source);

    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnAccountSettingsChangePassword, "method 'onMBtnAccountSettingsChangePasswordClicked'");
    view2131755355 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onMBtnAccountSettingsChangePasswordClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnAccountSettingsDeleteAccount, "method 'onMBtnAccountSettingsDeleteAccountClicked'");
    view2131755356 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onMBtnAccountSettingsDeleteAccountClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnAccountSettingsLocationUpdates, "method 'onMBtnAccountSettingsLocationUpdatesClicked'");
    view2131755357 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onMBtnAccountSettingsLocationUpdatesClicked();
      }
    });
  }

  @Override
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131755355.setOnClickListener(null);
    view2131755355 = null;
    view2131755356.setOnClickListener(null);
    view2131755356 = null;
    view2131755357.setOnClickListener(null);
    view2131755357 = null;

    super.unbind();
  }
}
