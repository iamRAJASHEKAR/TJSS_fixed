// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.serviceprovider.alert;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ServiceProviderInfoWindow_ViewBinding implements Unbinder {
  private ServiceProviderInfoWindow target;

  private View view2131755489;

  private View view2131755490;

  @UiThread
  public ServiceProviderInfoWindow_ViewBinding(final ServiceProviderInfoWindow target,
      View source) {
    this.target = target;

    View view;
    target.mLblServiceProviderTitle = Utils.findRequiredViewAsType(source, R.id.lblServiceProviderTitle, "field 'mLblServiceProviderTitle'", AppCompatTextView.class);
    target.mLblServiceProviderCategoryAndSubCategory = Utils.findRequiredViewAsType(source, R.id.lblServiceProviderCategoryAndSubCategory, "field 'mLblServiceProviderCategoryAndSubCategory'", AppCompatTextView.class);
    target.mSeparator = Utils.findRequiredView(source, R.id.separator, "field 'mSeparator'");
    target.mLblServiceProviderDescription = Utils.findRequiredViewAsType(source, R.id.lblServiceProviderDescription, "field 'mLblServiceProviderDescription'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.btnShare, "method 'onShareClicked'");
    view2131755489 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onShareClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnCall, "method 'onCallClicked'");
    view2131755490 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onCallClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ServiceProviderInfoWindow target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblServiceProviderTitle = null;
    target.mLblServiceProviderCategoryAndSubCategory = null;
    target.mSeparator = null;
    target.mLblServiceProviderDescription = null;

    view2131755489.setOnClickListener(null);
    view2131755489 = null;
    view2131755490.setOnClickListener(null);
    view2131755490 = null;
  }
}
