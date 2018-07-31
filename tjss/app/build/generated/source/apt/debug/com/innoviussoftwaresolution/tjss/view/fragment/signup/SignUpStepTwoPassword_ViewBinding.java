// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.signup;

import android.support.annotation.UiThread;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignUpStepTwoPassword_ViewBinding extends BaseFragment_ViewBinding {
  private SignUpStepTwoPassword target;

  @UiThread
  public SignUpStepTwoPassword_ViewBinding(SignUpStepTwoPassword target, View source) {
    super(target, source);

    this.target = target;

    target.mTxtRegistrationPassword = Utils.findRequiredViewAsType(source, R.id.txtRegistrationPassword, "field 'mTxtRegistrationPassword'", TextInputLayout.class);
    target.txtConfromPassword = Utils.findRequiredViewAsType(source, R.id.txtRegistrationConformPassword, "field 'txtConfromPassword'", TextInputLayout.class);
  }

  @Override
  public void unbind() {
    SignUpStepTwoPassword target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTxtRegistrationPassword = null;
    target.txtConfromPassword = null;

    super.unbind();
  }
}
