// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.location;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentLocationSharing_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentLocationSharing target;

  private View view2131755446;

  @UiThread
  public FragmentLocationSharing_ViewBinding(final FragmentLocationSharing target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mLblLocationSharingHeading = Utils.findRequiredViewAsType(source, R.id.lblLocationSharingHeading, "field 'mLblLocationSharingHeading'", AppCompatTextView.class);
    target.mLblLocationSharingInfo = Utils.findRequiredViewAsType(source, R.id.lblLocationSharingInfo, "field 'mLblLocationSharingInfo'", AppCompatTextView.class);
    target.mImgLocationSharingUserImage = Utils.findRequiredViewAsType(source, R.id.imgLocationSharingUserImage, "field 'mImgLocationSharingUserImage'", CircleImageView.class);
    target.mLblLocationSharingUserName = Utils.findRequiredViewAsType(source, R.id.lblLocationSharingUserName, "field 'mLblLocationSharingUserName'", AppCompatTextView.class);
    target.mLblLocationSharingEmail = Utils.findRequiredViewAsType(source, R.id.lblLocationSharingEmail, "field 'mLblLocationSharingEmail'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.tglLocationSharingShareMyLocation, "field 'mTglLocationSharingShareMyLocation' and method 'onCheckedChanged'");
    target.mTglLocationSharingShareMyLocation = Utils.castView(view, R.id.tglLocationSharingShareMyLocation, "field 'mTglLocationSharingShareMyLocation'", SwitchCompat.class);
    view2131755446 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckedChanged(p1);
      }
    });
    target.mLblLocationSharingLocationOfPeopleInCircle = Utils.findRequiredViewAsType(source, R.id.lblLocationSharingLocationOfPeopleInCircle, "field 'mLblLocationSharingLocationOfPeopleInCircle'", AppCompatTextView.class);
    target.mLstLocationSharingLocationOfPeopleInCircle = Utils.findRequiredViewAsType(source, R.id.lstLocationSharingLocationOfPeopleInCircle, "field 'mLstLocationSharingLocationOfPeopleInCircle'", RecyclerView.class);
  }

  @Override
  public void unbind() {
    FragmentLocationSharing target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblLocationSharingHeading = null;
    target.mLblLocationSharingInfo = null;
    target.mImgLocationSharingUserImage = null;
    target.mLblLocationSharingUserName = null;
    target.mLblLocationSharingEmail = null;
    target.mTglLocationSharingShareMyLocation = null;
    target.mLblLocationSharingLocationOfPeopleInCircle = null;
    target.mLstLocationSharingLocationOfPeopleInCircle = null;

    ((CompoundButton) view2131755446).setOnCheckedChangeListener(null);
    view2131755446 = null;

    super.unbind();
  }
}
