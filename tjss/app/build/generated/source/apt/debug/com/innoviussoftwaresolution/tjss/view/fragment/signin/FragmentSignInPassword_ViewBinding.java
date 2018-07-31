// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.signin;

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

public class FragmentSignInPassword_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentSignInPassword target;

  private View view2131755618;

  @UiThread
  public FragmentSignInPassword_ViewBinding(final FragmentSignInPassword target, View source) {
    super(target, source);

    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnSignForgotPassword, "field 'mBtnSignForgotPassword' and method 'onViewClicked'");
    target.mBtnSignForgotPassword = Utils.castView(view, R.id.btnSignForgotPassword, "field 'mBtnSignForgotPassword'", AppCompatButton.class);
    view2131755618 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.mTxtLoginPassword = Utils.findRequiredViewAsType(source, R.id.txtLoginPassword, "field 'mTxtLoginPassword'", TextInputEditText.class);
  }

  @Override
  public void unbind() {
    FragmentSignInPassword target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mBtnSignForgotPassword = null;
    target.mTxtLoginPassword = null;

    view2131755618.setOnClickListener(null);
    view2131755618 = null;

    super.unbind();
  }
}
