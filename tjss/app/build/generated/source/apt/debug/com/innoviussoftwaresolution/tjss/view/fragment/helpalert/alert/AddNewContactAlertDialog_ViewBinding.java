// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.helpalert.alert;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddNewContactAlertDialog_ViewBinding implements Unbinder {
  private AddNewContactAlertDialog target;

  private View view2131755366;

  private View view2131755367;

  @UiThread
  public AddNewContactAlertDialog_ViewBinding(final AddNewContactAlertDialog target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btnAddContactManually, "method 'onViewClicked'");
    view2131755366 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnAddContactFromContacts, "method 'onViewClicked'");
    view2131755367 = view;
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
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131755366.setOnClickListener(null);
    view2131755366 = null;
    view2131755367.setOnClickListener(null);
    view2131755367 = null;
  }
}
