// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.account;

import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentChangePassword_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentChangePassword target;

  private View view2131755382;

  @UiThread
  public FragmentChangePassword_ViewBinding(final FragmentChangePassword target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mTxtOldPassword = Utils.findRequiredViewAsType(source, R.id.txtOldPassword, "field 'mTxtOldPassword'", TextInputEditText.class);
    target.mTxtNewPassword = Utils.findRequiredViewAsType(source, R.id.txtNewPassword, "field 'mTxtNewPassword'", TextInputEditText.class);
    view = Utils.findRequiredView(source, R.id.btnSave, "field 'mBtnSave' and method 'onViewClicked'");
    target.mBtnSave = Utils.castView(view, R.id.btnSave, "field 'mBtnSave'", AppCompatButton.class);
    view2131755382 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentChangePassword target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTxtOldPassword = null;
    target.mTxtNewPassword = null;
    target.mBtnSave = null;

    view2131755382.setOnClickListener(null);
    view2131755382 = null;

    super.unbind();
  }
}
