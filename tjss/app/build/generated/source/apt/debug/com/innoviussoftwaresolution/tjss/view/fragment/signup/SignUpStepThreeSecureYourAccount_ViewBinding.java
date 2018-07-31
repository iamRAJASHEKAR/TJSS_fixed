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

public class SignUpStepThreeSecureYourAccount_ViewBinding extends BaseFragment_ViewBinding {
  private SignUpStepThreeSecureYourAccount target;

  @UiThread
  public SignUpStepThreeSecureYourAccount_ViewBinding(SignUpStepThreeSecureYourAccount target,
      View source) {
    super(target, source);

    this.target = target;

    target.mTxtRegistrationEmail = Utils.findRequiredViewAsType(source, R.id.txtRegistrationEmail, "field 'mTxtRegistrationEmail'", TextInputLayout.class);
  }

  @Override
  public void unbind() {
    SignUpStepThreeSecureYourAccount target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTxtRegistrationEmail = null;

    super.unbind();
  }
}
