// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserTimlineAdapter$UserTravellingViewHolder_ViewBinding implements Unbinder {
  private UserTimlineAdapter.UserTravellingViewHolder target;

  private View viewSource;

  @UiThread
  public UserTimlineAdapter$UserTravellingViewHolder_ViewBinding(final UserTimlineAdapter.UserTravellingViewHolder target,
      View source) {
    this.target = target;

    target.mImgTimeLineCirclePin = Utils.findRequiredViewAsType(source, R.id.imgTimeLineCirclePin, "field 'mImgTimeLineCirclePin'", AppCompatImageView.class);
    target.mLblTimeLineCaption = Utils.findRequiredViewAsType(source, R.id.lblTimeLineCaption, "field 'mLblTimeLineCaption'", AppCompatTextView.class);
    target.mLblTimeLineDuration = Utils.findRequiredViewAsType(source, R.id.lblTimeLineDuration, "field 'mLblTimeLineDuration'", AppCompatTextView.class);
    target.mImgTimeLineRoute = Utils.findRequiredViewAsType(source, R.id.imgTimeLineRoute, "field 'mImgTimeLineRoute'", AppCompatImageView.class);
    viewSource = source;
    source.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    UserTimlineAdapter.UserTravellingViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImgTimeLineCirclePin = null;
    target.mLblTimeLineCaption = null;
    target.mLblTimeLineDuration = null;
    target.mImgTimeLineRoute = null;

    viewSource.setOnClickListener(null);
    viewSource = null;
  }
}
