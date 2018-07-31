// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.signin;

import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentSignInPhone_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentSignInPhone target;

  @UiThread
  public FragmentSignInPhone_ViewBinding(FragmentSignInPhone target, View source) {
    super(target, source);

    this.target = target;

    target.mCboSignInIsdCodes = Utils.findRequiredViewAsType(source, R.id.cboSignInIsdCodes, "field 'mCboSignInIsdCodes'", AppCompatSpinner.class);
    target.mTxtSignInPhone = Utils.findRequiredViewAsType(source, R.id.txtSignInPhone, "field 'mTxtSignInPhone'", TextInputEditText.class);
  }

  @Override
  public void unbind() {
    FragmentSignInPhone target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mCboSignInIsdCodes = null;
    target.mTxtSignInPhone = null;

    super.unbind();
  }
}
