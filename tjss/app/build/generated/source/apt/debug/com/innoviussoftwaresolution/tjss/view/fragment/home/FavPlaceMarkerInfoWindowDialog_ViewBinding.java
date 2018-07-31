// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.home;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FavPlaceMarkerInfoWindowDialog_ViewBinding implements Unbinder {
  private FavPlaceMarkerInfoWindowDialog target;

  @UiThread
  public FavPlaceMarkerInfoWindowDialog_ViewBinding(FavPlaceMarkerInfoWindowDialog target,
      View source) {
    this.target = target;

    target.mImgMarkerDetailUserImage = Utils.findRequiredViewAsType(source, R.id.imgMarkerDetailUserImage, "field 'mImgMarkerDetailUserImage'", CircleImageView.class);
    target.mLblMarkerDetailsUserName = Utils.findRequiredViewAsType(source, R.id.lblMarkerDetailsName, "field 'mLblMarkerDetailsUserName'", AppCompatTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FavPlaceMarkerInfoWindowDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImgMarkerDetailUserImage = null;
    target.mLblMarkerDetailsUserName = null;
  }
}
