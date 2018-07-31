// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.helpalert;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentHelpAlertDetail_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentHelpAlertDetail target;

  @UiThread
  public FragmentHelpAlertDetail_ViewBinding(FragmentHelpAlertDetail target, View source) {
    super(target, source);

    this.target = target;

    target.mBtnIncidentType = Utils.findRequiredViewAsType(source, R.id.btnIncidentType, "field 'mBtnIncidentType'", AppCompatButton.class);
    target.mLblIncidentHeading = Utils.findRequiredViewAsType(source, R.id.lblIncidentHeading, "field 'mLblIncidentHeading'", AppCompatTextView.class);
    target.mLblIncidentTimeAndDate = Utils.findRequiredViewAsType(source, R.id.lblIncidentTimeAndDate, "field 'mLblIncidentTimeAndDate'", AppCompatTextView.class);
    target.mLblIncidentDescription = Utils.findRequiredViewAsType(source, R.id.lblIncidentDescription, "field 'mLblIncidentDescription'", AppCompatTextView.class);
    target.mLstIncidentLog = Utils.findRequiredViewAsType(source, R.id.lstIncidentLog, "field 'mLstIncidentLog'", RecyclerView.class);
  }

  @Override
  public void unbind() {
    FragmentHelpAlertDetail target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mBtnIncidentType = null;
    target.mLblIncidentHeading = null;
    target.mLblIncidentTimeAndDate = null;
    target.mLblIncidentDescription = null;
    target.mLstIncidentLog = null;

    super.unbind();
  }
}
