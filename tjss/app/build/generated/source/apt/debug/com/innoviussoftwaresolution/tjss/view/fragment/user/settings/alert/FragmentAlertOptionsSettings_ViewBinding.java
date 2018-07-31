// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.alert;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentAlertOptionsSettings_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentAlertOptionsSettings target;

  private View view2131755378;

  private View view2131755379;

  @UiThread
  public FragmentAlertOptionsSettings_ViewBinding(final FragmentAlertOptionsSettings target,
      View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mLstSafetyCircles = Utils.findRequiredViewAsType(source, R.id.lstSafetyCircles, "field 'mLstSafetyCircles'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.chkCrimeInMyArea, "field 'mChkCrimeInMyArea' and method 'onCrimeInMyAreaChanged'");
    target.mChkCrimeInMyArea = Utils.castView(view, R.id.chkCrimeInMyArea, "field 'mChkCrimeInMyArea'", AppCompatCheckBox.class);
    view2131755378 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCrimeInMyAreaChanged();
      }
    });
    view = Utils.findRequiredView(source, R.id.chkAlertSound, "field 'mChkAlertSound' and method 'onAlertSoundChanged'");
    target.mChkAlertSound = Utils.castView(view, R.id.chkAlertSound, "field 'mChkAlertSound'", AppCompatCheckBox.class);
    view2131755379 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onAlertSoundChanged();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentAlertOptionsSettings target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstSafetyCircles = null;
    target.mChkCrimeInMyArea = null;
    target.mChkAlertSound = null;

    ((CompoundButton) view2131755378).setOnCheckedChangeListener(null);
    view2131755378 = null;
    ((CompoundButton) view2131755379).setOnCheckedChangeListener(null);
    view2131755379 = null;

    super.unbind();
  }
}
