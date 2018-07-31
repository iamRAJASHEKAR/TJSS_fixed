// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.home;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SafetyCircleMarkerInfoWindowDialog_ViewBinding implements Unbinder {
  private SafetyCircleMarkerInfoWindowDialog target;

  private View view2131755319;

  private View view2131755320;

  @UiThread
  public SafetyCircleMarkerInfoWindowDialog_ViewBinding(final SafetyCircleMarkerInfoWindowDialog target,
      View source) {
    this.target = target;

    View view;
    target.mImgMarkerDetailUserImage = Utils.findRequiredViewAsType(source, R.id.imgMarkerDetailUserImage, "field 'mImgMarkerDetailUserImage'", CircleImageView.class);
    target.mLblMarkerDetailsUserName = Utils.findRequiredViewAsType(source, R.id.lblMarkerDetailsName, "field 'mLblMarkerDetailsUserName'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.btnMarkerCall, "method 'onMBtnMarkerCallClicked'");
    view2131755319 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onMBtnMarkerCallClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnMarkerMail, "method 'onMBtnMarkerMailClicked'");
    view2131755320 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onMBtnMarkerMailClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SafetyCircleMarkerInfoWindowDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImgMarkerDetailUserImage = null;
    target.mLblMarkerDetailsUserName = null;

    view2131755319.setOnClickListener(null);
    view2131755319 = null;
    view2131755320.setOnClickListener(null);
    view2131755320 = null;
  }
}
