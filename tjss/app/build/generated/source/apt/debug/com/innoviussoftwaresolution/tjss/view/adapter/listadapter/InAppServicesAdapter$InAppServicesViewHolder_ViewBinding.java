// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InAppServicesAdapter$InAppServicesViewHolder_ViewBinding implements Unbinder {
  private InAppServicesAdapter.InAppServicesViewHolder target;

  private View view2131755336;

  private View view2131755344;

  private View view2131755345;

  @UiThread
  public InAppServicesAdapter$InAppServicesViewHolder_ViewBinding(final InAppServicesAdapter.InAppServicesViewHolder target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.lblServiceHeader, "field 'mLblServiceHeader' and method 'onViewClicked'");
    target.mLblServiceHeader = Utils.castView(view, R.id.lblServiceHeader, "field 'mLblServiceHeader'", AppCompatTextView.class);
    view2131755336 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mLblServiceDescription = Utils.findRequiredViewAsType(source, R.id.lblServiceDescription, "field 'mLblServiceDescription'", AppCompatTextView.class);
    target.mLblServiceContactInfo = Utils.findRequiredViewAsType(source, R.id.lblServiceContactInfo, "field 'mLblServiceContactInfo'", AppCompatTextView.class);
    target.mLblServiceAddressLine1 = Utils.findRequiredViewAsType(source, R.id.lblServiceAddressLine1, "field 'mLblServiceAddressLine1'", AppCompatTextView.class);
    target.mLblServicePhone = Utils.findRequiredViewAsType(source, R.id.lblServicePhone, "field 'mLblServicePhone'", AppCompatTextView.class);
    target.mLblServicesCoinsRequired = Utils.findRequiredViewAsType(source, R.id.lblServicesCoinsRequired, "field 'mLblServicesCoinsRequired'", AppCompatTextView.class);
    target.mLblServicesPriceTag = Utils.findRequiredViewAsType(source, R.id.lblServicesPriceTag, "field 'mLblServicesPriceTag'", AppCompatTextView.class);
    target.mViewServiceSeparator = Utils.findRequiredView(source, R.id.viewServiceSeparator, "field 'mViewServiceSeparator'");
    view = Utils.findRequiredView(source, R.id.btnServiceCall, "field 'mBtnServiceCall' and method 'onViewClicked'");
    target.mBtnServiceCall = Utils.castView(view, R.id.btnServiceCall, "field 'mBtnServiceCall'", AppCompatButton.class);
    view2131755344 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnServicesPay, "field 'mBtnServicesPay' and method 'onViewClicked'");
    target.mBtnServicesPay = Utils.castView(view, R.id.btnServicesPay, "field 'mBtnServicesPay'", AppCompatButton.class);
    view2131755345 = view;
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
    InAppServicesAdapter.InAppServicesViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblServiceHeader = null;
    target.mLblServiceDescription = null;
    target.mLblServiceContactInfo = null;
    target.mLblServiceAddressLine1 = null;
    target.mLblServicePhone = null;
    target.mLblServicesCoinsRequired = null;
    target.mLblServicesPriceTag = null;
    target.mViewServiceSeparator = null;
    target.mBtnServiceCall = null;
    target.mBtnServicesPay = null;

    view2131755336.setOnClickListener(null);
    view2131755336 = null;
    view2131755344.setOnClickListener(null);
    view2131755344 = null;
    view2131755345.setOnClickListener(null);
    view2131755345 = null;
  }
}
