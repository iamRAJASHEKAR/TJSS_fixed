// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.adapter.listadapter;

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

public class ContactsListAdapter$ContactsViewHolder_ViewBinding implements Unbinder {
  private ContactsListAdapter.ContactsViewHolder target;

  private View view2131755230;

  @UiThread
  public ContactsListAdapter$ContactsViewHolder_ViewBinding(final ContactsListAdapter.ContactsViewHolder target,
      View source) {
    this.target = target;

    View view;
    target.mLblPhoneContactName = Utils.findRequiredViewAsType(source, R.id.lblPhoneContactName, "field 'mLblPhoneContactName'", AppCompatTextView.class);
    target.mLblPhoneContactNumber = Utils.findRequiredViewAsType(source, R.id.lblPhoneContactNumber, "field 'mLblPhoneContactNumber'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.btnAddContactToCircle, "field 'mBtnAddContactToCircle' and method 'onClick'");
    target.mBtnAddContactToCircle = Utils.castView(view, R.id.btnAddContactToCircle, "field 'mBtnAddContactToCircle'", AppCompatImageButton.class);
    view2131755230 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ContactsListAdapter.ContactsViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblPhoneContactName = null;
    target.mLblPhoneContactNumber = null;
    target.mBtnAddContactToCircle = null;

    view2131755230.setOnClickListener(null);
    view2131755230 = null;
  }
}
