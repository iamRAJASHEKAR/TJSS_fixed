// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.signup;

import android.support.annotation.UiThread;
import android.view.View;
import butterknife.internal.Utils;
import com.chaos.view.PinView;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignUpStepFiveYourCircle_ViewBinding extends BaseFragment_ViewBinding {
  private SignUpStepFiveYourCircle target;

  @UiThread
  public SignUpStepFiveYourCircle_ViewBinding(SignUpStepFiveYourCircle target, View source) {
    super(target, source);

    this.target = target;

    target.mTxtSecurityCode = Utils.findRequiredViewAsType(source, R.id.txtSecurityCode, "field 'mTxtSecurityCode'", PinView.class);
  }

  @Override
  public void unbind() {
    SignUpStepFiveYourCircle target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTxtSecurityCode = null;

    super.unbind();
  }
}
