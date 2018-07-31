// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.helpalert.alert;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.chaos.view.PinView;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InitHelpAlertDialog_ViewBinding implements Unbinder {
  private InitHelpAlertDialog target;

  private View view2131755440;

  @UiThread
  public InitHelpAlertDialog_ViewBinding(final InitHelpAlertDialog target, View source) {
    this.target = target;

    View view;
    target.mTxtSecurityAnswer = Utils.findRequiredViewAsType(source, R.id.txtSecurityAnswer, "field 'mTxtSecurityAnswer'", TextInputEditText.class);
    target.mPinSecurityQuestion = Utils.findRequiredViewAsType(source, R.id.pinSecurityQuestion, "field 'mPinSecurityQuestion'", PinView.class);
    target.mLblSecurityQuestion = Utils.findRequiredViewAsType(source, R.id.lblSecurityQuestion, "field 'mLblSecurityQuestion'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.btnSubmitSecurity, "method 'onViewClicked'");
    view2131755440 = view;
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
    InitHelpAlertDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTxtSecurityAnswer = null;
    target.mPinSecurityQuestion = null;
    target.mLblSecurityQuestion = null;

    view2131755440.setOnClickListener(null);
    view2131755440 = null;
  }
}
