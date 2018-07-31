// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.viewmodel;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SafetyCircleContactsAdapter$SafetyCircleContactViewHolder_ViewBinding implements Unbinder {
  private SafetyCircleContactsAdapter.SafetyCircleContactViewHolder target;

  private View view2131755232;

  private View view2131755231;

  @UiThread
  public SafetyCircleContactsAdapter$SafetyCircleContactViewHolder_ViewBinding(final SafetyCircleContactsAdapter.SafetyCircleContactViewHolder target,
      View source) {
    this.target = target;

    View view;
    target.mLblPhoneContactName = Utils.findRequiredViewAsType(source, R.id.lblPhoneContactName, "field 'mLblPhoneContactName'", AppCompatTextView.class);
    target.mLblPhoneContactNumber = Utils.findRequiredViewAsType(source, R.id.lblPhoneContactNumber, "field 'mLblPhoneContactNumber'", AppCompatTextView.class);
    target.mBtnAddContactToCircle = Utils.findRequiredViewAsType(source, R.id.btnAddContactToCircle, "field 'mBtnAddContactToCircle'", AppCompatImageButton.class);
    view = Utils.findRequiredView(source, R.id.btnEditHelpAlertContact, "field 'mBtnEdit' and method 'click'");
    target.mBtnEdit = Utils.castView(view, R.id.btnEditHelpAlertContact, "field 'mBtnEdit'", AppCompatImageButton.class);
    view2131755232 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.click(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDeleteHelpAlertContact, "field 'mBtnDelete' and method 'click'");
    target.mBtnDelete = Utils.castView(view, R.id.btnDeleteHelpAlertContact, "field 'mBtnDelete'", AppCompatImageButton.class);
    view2131755231 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.click(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SafetyCircleContactsAdapter.SafetyCircleContactViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblPhoneContactName = null;
    target.mLblPhoneContactNumber = null;
    target.mBtnAddContactToCircle = null;
    target.mBtnEdit = null;
    target.mBtnDelete = null;

    view2131755232.setOnClickListener(null);
    view2131755232 = null;
    view2131755231.setOnClickListener(null);
    view2131755231 = null;
  }
}
