// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.alert;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentSafetyCircleAlertOptions_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentSafetyCircleAlertOptions target;

  @UiThread
  public FragmentSafetyCircleAlertOptions_ViewBinding(FragmentSafetyCircleAlertOptions target,
      View source) {
    super(target, source);

    this.target = target;

    target.mChkEmail = Utils.findRequiredViewAsType(source, R.id.chkEmail, "field 'mChkEmail'", AppCompatCheckBox.class);
    target.mChkNotifications = Utils.findRequiredViewAsType(source, R.id.chkNotifications, "field 'mChkNotifications'", AppCompatCheckBox.class);
    target.mChkSms = Utils.findRequiredViewAsType(source, R.id.chkSms, "field 'mChkSms'", AppCompatCheckBox.class);
  }

  @Override
  public void unbind() {
    FragmentSafetyCircleAlertOptions target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mChkEmail = null;
    target.mChkNotifications = null;
    target.mChkSms = null;

    super.unbind();
  }
}
