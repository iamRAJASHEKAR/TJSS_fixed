// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

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

public class SafetyCircleMembersListingAdapter$SafetyCircleMemberViewHolder_ViewBinding implements Unbinder {
  private SafetyCircleMembersListingAdapter.SafetyCircleMemberViewHolder target;

  private View view2131755325;

  private View view2131755326;

  @UiThread
  public SafetyCircleMembersListingAdapter$SafetyCircleMemberViewHolder_ViewBinding(final SafetyCircleMembersListingAdapter.SafetyCircleMemberViewHolder target,
      View source) {
    this.target = target;

    View view;
    target.mImgSafetyCircleDp = Utils.findRequiredViewAsType(source, R.id.imgSafetyCircleDp, "field 'mImgSafetyCircleDp'", CircleImageView.class);
    target.mLblCircleMemberName = Utils.findRequiredViewAsType(source, R.id.lblCircleMemberName, "field 'mLblCircleMemberName'", AppCompatTextView.class);
    target.mLblSafetyBattery = Utils.findRequiredViewAsType(source, R.id.lblSafetyBattery, "field 'mLblSafetyBattery'", AppCompatTextView.class);
    target.mLblSafetyCircleLocation = Utils.findRequiredViewAsType(source, R.id.lblSafetyCircleLocation, "field 'mLblSafetyCircleLocation'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.btnSafetyCircleMsg, "method 'onViewClicked'");
    view2131755325 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnSafetyCirclePhone, "method 'onViewClicked'");
    view2131755326 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SafetyCircleMembersListingAdapter.SafetyCircleMemberViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImgSafetyCircleDp = null;
    target.mLblCircleMemberName = null;
    target.mLblSafetyBattery = null;
    target.mLblSafetyCircleLocation = null;

    view2131755325.setOnClickListener(null);
    view2131755325 = null;
    view2131755326.setOnClickListener(null);
    view2131755326 = null;
  }
}
