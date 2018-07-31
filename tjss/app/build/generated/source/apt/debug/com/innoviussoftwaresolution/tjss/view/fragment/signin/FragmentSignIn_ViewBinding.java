// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.signin;

import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentSignIn_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentSignIn target;

  private View view2131755511;

  private View view2131755015;

  @UiThread
  public FragmentSignIn_ViewBinding(final FragmentSignIn target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mLblSignUpSignIn = Utils.findRequiredViewAsType(source, R.id.lblSignUpSignIn, "field 'mLblSignUpSignIn'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.lblSignUpSignUp, "field 'mLblSignUpSignUp' and method 'onMLblSignUpSignUpClicked'");
    target.mLblSignUpSignUp = Utils.castView(view, R.id.lblSignUpSignUp, "field 'mLblSignUpSignUp'", AppCompatTextView.class);
    view2131755511 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onMLblSignUpSignUpClicked();
      }
    });
    target.mVpSignIn = Utils.findRequiredViewAsType(source, R.id.vpSignIn, "field 'mVpSignIn'", ViewPager.class);
    view = Utils.findRequiredView(source, R.id.btnNext, "field 'mBtnNext' and method 'onMBtnNextClicked'");
    target.mBtnNext = Utils.castView(view, R.id.btnNext, "field 'mBtnNext'", AppCompatButton.class);
    view2131755015 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onMBtnNextClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentSignIn target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblSignUpSignIn = null;
    target.mLblSignUpSignUp = null;
    target.mVpSignIn = null;
    target.mBtnNext = null;

    view2131755511.setOnClickListener(null);
    view2131755511 = null;
    view2131755015.setOnClickListener(null);
    view2131755015 = null;

    super.unbind();
  }
}
