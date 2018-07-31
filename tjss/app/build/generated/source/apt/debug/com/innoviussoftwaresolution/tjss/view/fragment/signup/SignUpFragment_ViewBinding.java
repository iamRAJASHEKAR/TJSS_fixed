// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.signup;

import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignUpFragment_ViewBinding extends BaseFragment_ViewBinding {
  private SignUpFragment target;

  private View view2131755510;

  private View view2131755513;

  private View view2131755015;

  private View view2131755518;

  @UiThread
  public SignUpFragment_ViewBinding(final SignUpFragment target, View source) {
    super(target, source);

    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.lblSignUpSignIn, "field 'mLblSignUpSignIn' and method 'onViewClicked'");
    target.mLblSignUpSignIn = Utils.castView(view, R.id.lblSignUpSignIn, "field 'mLblSignUpSignIn'", AppCompatTextView.class);
    view2131755510 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mLblSignUpSignUp = Utils.findRequiredViewAsType(source, R.id.lblSignUpSignUp, "field 'mLblSignUpSignUp'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.btnSignUpBack, "field 'mBtnSignUpBack' and method 'onViewClicked'");
    target.mBtnSignUpBack = Utils.castView(view, R.id.btnSignUpBack, "field 'mBtnSignUpBack'", AppCompatButton.class);
    view2131755513 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mLblSignUpProgress = Utils.findRequiredViewAsType(source, R.id.lblSignUpProgress, "field 'mLblSignUpProgress'", TextSwitcher.class);
    target.mLblSignUpStepText = Utils.findRequiredViewAsType(source, R.id.lblSignUpStepText, "field 'mLblSignUpStepText'", TextSwitcher.class);
    target.mPgrsSignUpProgress = Utils.findRequiredViewAsType(source, R.id.pgrsSignUpProgress, "field 'mPgrsSignUpProgress'", ProgressBar.class);
    target.mVpSignUp = Utils.findRequiredViewAsType(source, R.id.vpSignUp, "field 'mVpSignUp'", ViewPager.class);
    view = Utils.findRequiredView(source, R.id.btnNext, "field 'mBtnNext' and method 'onViewClicked'");
    target.mBtnNext = Utils.castView(view, R.id.btnNext, "field 'mBtnNext'", AppCompatButton.class);
    view2131755015 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lblSignUpCreateCircle, "field 'mLblSignUpCreateCircle' and method 'onViewClicked'");
    target.mLblSignUpCreateCircle = Utils.castView(view, R.id.lblSignUpCreateCircle, "field 'mLblSignUpCreateCircle'", AppCompatTextView.class);
    view2131755518 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  public void unbind() {
    SignUpFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblSignUpSignIn = null;
    target.mLblSignUpSignUp = null;
    target.mBtnSignUpBack = null;
    target.mLblSignUpProgress = null;
    target.mLblSignUpStepText = null;
    target.mPgrsSignUpProgress = null;
    target.mVpSignUp = null;
    target.mBtnNext = null;
    target.mLblSignUpCreateCircle = null;

    view2131755510.setOnClickListener(null);
    view2131755510 = null;
    view2131755513.setOnClickListener(null);
    view2131755513 = null;
    view2131755015.setOnClickListener(null);
    view2131755015 = null;
    view2131755518.setOnClickListener(null);
    view2131755518 = null;

    super.unbind();
  }
}
