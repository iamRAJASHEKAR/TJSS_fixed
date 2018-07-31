// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.signup;

import android.support.annotation.UiThread;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignUpStepFourYourProfile_ViewBinding extends BaseFragment_ViewBinding {
  private SignUpStepFourYourProfile target;

  private View view2131755622;

  @UiThread
  public SignUpStepFourYourProfile_ViewBinding(final SignUpStepFourYourProfile target,
      View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mImgSignUpUserImage = Utils.findRequiredViewAsType(source, R.id.imgSignUpUserImage, "field 'mImgSignUpUserImage'", CircleImageView.class);
    target.mTxtRegistrationName = Utils.findRequiredViewAsType(source, R.id.txtRegistrationName, "field 'mTxtRegistrationName'", TextInputLayout.class);
    view = Utils.findRequiredView(source, R.id.btnRegistrationSelectImage, "method 'onViewClicked'");
    view2131755622 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    SignUpStepFourYourProfile target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImgSignUpUserImage = null;
    target.mTxtRegistrationName = null;

    view2131755622.setOnClickListener(null);
    view2131755622 = null;

    super.unbind();
  }
}
