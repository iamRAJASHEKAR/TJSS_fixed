// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.signup;

import android.support.annotation.UiThread;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignUpStepOnePhone_ViewBinding extends BaseFragment_ViewBinding {
  private SignUpStepOnePhone target;

  @UiThread
  public SignUpStepOnePhone_ViewBinding(SignUpStepOnePhone target, View source) {
    super(target, source);

    this.target = target;

    target.mCboRegistrationIsdCodes = Utils.findRequiredViewAsType(source, R.id.cboRegistrationIsdCodes, "field 'mCboRegistrationIsdCodes'", AppCompatSpinner.class);
    target.mTxtRegistrationPhone = Utils.findRequiredViewAsType(source, R.id.txtRegistrationPhone, "field 'mTxtRegistrationPhone'", TextInputLayout.class);
  }

  @Override
  public void unbind() {
    SignUpStepOnePhone target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mCboRegistrationIsdCodes = null;
    target.mTxtRegistrationPhone = null;

    super.unbind();
  }
}
