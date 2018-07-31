// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.helpalert;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentHelpAlert_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentHelpAlert target;

  private View view2131755412;

  private View view2131755414;

  @UiThread
  public FragmentHelpAlert_ViewBinding(final FragmentHelpAlert target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mLstHelpAlertContacts = Utils.findRequiredViewAsType(source, R.id.lstHelpAlertContacts, "field 'mLstHelpAlertContacts'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.btnAddNewContact, "method 'onAddNewContactClicked'");
    view2131755412 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onAddNewContactClicked();
      }
    });
    view = Utils.findRequiredView(source, R.id.btnSendHelpAlert, "method 'onSendHelpAlertClicked'");
    view2131755414 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSendHelpAlertClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentHelpAlert target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstHelpAlertContacts = null;

    view2131755412.setOnClickListener(null);
    view2131755412 = null;
    view2131755414.setOnClickListener(null);
    view2131755414 = null;

    super.unbind();
  }
}
