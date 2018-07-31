// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.map;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentMapSettings_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentMapSettings target;

  private View view2131755456;

  @UiThread
  public FragmentMapSettings_ViewBinding(final FragmentMapSettings target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mChkHospitals = Utils.findRequiredViewAsType(source, R.id.chkHospitals, "field 'mChkHospitals'", AppCompatCheckBox.class);
    target.mChkFireStations = Utils.findRequiredViewAsType(source, R.id.chkFireStations, "field 'mChkFireStations'", AppCompatCheckBox.class);
    target.mChkPoliceStations = Utils.findRequiredViewAsType(source, R.id.chkPoliceStations, "field 'mChkPoliceStations'", AppCompatCheckBox.class);
    target.mLblSelectedCrimeUpdateDuration = Utils.findRequiredViewAsType(source, R.id.lblSelectedCrimeUpdateDuration, "field 'mLblSelectedCrimeUpdateDuration'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.btnCrimeUpdateDuration, "method 'onViewClicked'");
    view2131755456 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentMapSettings target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mChkHospitals = null;
    target.mChkFireStations = null;
    target.mChkPoliceStations = null;
    target.mLblSelectedCrimeUpdateDuration = null;

    view2131755456.setOnClickListener(null);
    view2131755456 = null;

    super.unbind();
  }
}
