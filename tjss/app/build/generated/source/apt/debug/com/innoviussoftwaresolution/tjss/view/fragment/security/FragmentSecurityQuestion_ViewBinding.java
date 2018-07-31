// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.security;

import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.chaos.view.PinView;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentSecurityQuestion_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentSecurityQuestion target;

  private View view2131755440;

  @UiThread
  public FragmentSecurityQuestion_ViewBinding(final FragmentSecurityQuestion target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mCboSecurityQuestions = Utils.findRequiredViewAsType(source, R.id.cboSecurityQuestions, "field 'mCboSecurityQuestions'", AppCompatSpinner.class);
    target.mTxtSecurityAnswer = Utils.findRequiredViewAsType(source, R.id.txtSecurityAnswer, "field 'mTxtSecurityAnswer'", TextInputEditText.class);
    target.mPinSecurityQuestion = Utils.findRequiredViewAsType(source, R.id.pinSecurityQuestion, "field 'mPinSecurityQuestion'", PinView.class);
    view = Utils.findRequiredView(source, R.id.btnSubmitSecurity, "field 'mBtnSubmitSecurity' and method 'onViewClicked'");
    target.mBtnSubmitSecurity = Utils.castView(view, R.id.btnSubmitSecurity, "field 'mBtnSubmitSecurity'", AppCompatButton.class);
    view2131755440 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentSecurityQuestion target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mCboSecurityQuestions = null;
    target.mTxtSecurityAnswer = null;
    target.mPinSecurityQuestion = null;
    target.mBtnSubmitSecurity = null;

    view2131755440.setOnClickListener(null);
    view2131755440 = null;

    super.unbind();
  }
}
