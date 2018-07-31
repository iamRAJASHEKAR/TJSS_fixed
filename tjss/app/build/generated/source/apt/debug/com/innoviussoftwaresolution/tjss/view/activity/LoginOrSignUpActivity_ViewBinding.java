// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginOrSignUpActivity_ViewBinding implements Unbinder {
  private LoginOrSignUpActivity target;

  private View view2131755637;

  private View view2131755638;

  @UiThread
  public LoginOrSignUpActivity_ViewBinding(LoginOrSignUpActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginOrSignUpActivity_ViewBinding(final LoginOrSignUpActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnHomeSignUp, "method 'onViewClicked'");
    view2131755637 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnHomeSignIn, "method 'onViewClicked'");
    view2131755638 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131755637.setOnClickListener(null);
    view2131755637 = null;
    view2131755638.setOnClickListener(null);
    view2131755638 = null;
  }
}
