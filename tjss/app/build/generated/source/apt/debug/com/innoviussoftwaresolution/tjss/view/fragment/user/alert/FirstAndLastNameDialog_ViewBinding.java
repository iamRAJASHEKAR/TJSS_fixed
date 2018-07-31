// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.alert;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FirstAndLastNameDialog_ViewBinding implements Unbinder {
  private FirstAndLastNameDialog target;

  private View view2131755408;

  @UiThread
  public FirstAndLastNameDialog_ViewBinding(final FirstAndLastNameDialog target, View source) {
    this.target = target;

    View view;
    target.mTxtFirstName = Utils.findRequiredViewAsType(source, R.id.txtFirstName, "field 'mTxtFirstName'", TextInputLayout.class);
    target.mTxtLastName = Utils.findRequiredViewAsType(source, R.id.txtLastName, "field 'mTxtLastName'", TextInputLayout.class);
    view = Utils.findRequiredView(source, R.id.btnSubmit, "field 'mBtnSubmit' and method 'onViewClicked'");
    target.mBtnSubmit = Utils.castView(view, R.id.btnSubmit, "field 'mBtnSubmit'", AppCompatButton.class);
    view2131755408 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    FirstAndLastNameDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTxtFirstName = null;
    target.mTxtLastName = null;
    target.mBtnSubmit = null;

    view2131755408.setOnClickListener(null);
    view2131755408 = null;
  }
}
