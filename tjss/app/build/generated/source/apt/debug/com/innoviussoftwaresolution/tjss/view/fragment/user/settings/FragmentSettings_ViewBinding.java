// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.settings;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentSettings_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentSettings target;

  private View view2131755501;

  private View view2131755502;

  private View view2131755503;

  private View view2131755507;

  private View view2131755504;

  private View view2131755505;

  private View view2131755506;

  private View view2131755500;

  @UiThread
  public FragmentSettings_ViewBinding(final FragmentSettings target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mImgSettingsUserImage = Utils.findRequiredViewAsType(source, R.id.imgSettingsUserImage, "field 'mImgSettingsUserImage'", CircleImageView.class);
    target.mLblSettingsUserName = Utils.findRequiredViewAsType(source, R.id.lblSettingsUserName, "field 'mLblSettingsUserName'", AppCompatTextView.class);
    target.mLblSettingsEmail = Utils.findRequiredViewAsType(source, R.id.lblSettingsEmail, "field 'mLblSettingsEmail'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.btnSettingsSafetyCircle, "method 'onBtnSettingsSafetyCircleClicked'");
    view2131755501 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnSettingsSafetyCircleClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnSettingsMap, "method 'onBtnSettingsMapClicked'");
    view2131755502 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnSettingsMapClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnSettingsAlert, "method 'onBtnSettingsAlertClicked'");
    view2131755503 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnSettingsAlertClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnSettingsAccount, "method 'onBtnSettingsAccountClicked'");
    view2131755507 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnSettingsAccountClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnSettingsSafetyTips, "method 'onBtnSafetyTipsClicked'");
    view2131755504 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnSafetyTipsClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnSettingsMedicalRecords, "method 'onBtnMedicalRecordsClicked'");
    view2131755505 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnMedicalRecordsClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnSettingsServiceProviders, "method 'onServiceProvidersClicked'");
    view2131755506 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onServiceProvidersClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnLogout, "method 'onBtnLogoutClicked'");
    view2131755500 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBtnLogoutClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentSettings target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImgSettingsUserImage = null;
    target.mLblSettingsUserName = null;
    target.mLblSettingsEmail = null;

    view2131755501.setOnClickListener(null);
    view2131755501 = null;
    view2131755502.setOnClickListener(null);
    view2131755502 = null;
    view2131755503.setOnClickListener(null);
    view2131755503 = null;
    view2131755507.setOnClickListener(null);
    view2131755507 = null;
    view2131755504.setOnClickListener(null);
    view2131755504 = null;
    view2131755505.setOnClickListener(null);
    view2131755505 = null;
    view2131755506.setOnClickListener(null);
    view2131755506 = null;
    view2131755500.setOnClickListener(null);
    view2131755500 = null;

    super.unbind();
  }
}
