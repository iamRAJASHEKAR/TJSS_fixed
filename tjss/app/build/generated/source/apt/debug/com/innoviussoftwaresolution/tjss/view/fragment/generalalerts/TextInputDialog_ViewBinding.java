// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.generalalerts;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TextInputDialog_ViewBinding implements Unbinder {
  private TextInputDialog target;

  private View view2131755521;

  private View view2131755522;

  @UiThread
  public TextInputDialog_ViewBinding(final TextInputDialog target, View source) {
    this.target = target;

    View view;
    target.mLblInputDialogTitle = Utils.findRequiredViewAsType(source, R.id.lblInputDialogTitle, "field 'mLblInputDialogTitle'", AppCompatTextView.class);
    target.mTxtInputDialog = Utils.findRequiredViewAsType(source, R.id.txtInputDialog, "field 'mTxtInputDialog'", AppCompatEditText.class);
    view = Utils.findRequiredView(source, R.id.btnInputDialogSet, "method 'onViewClicked'");
    view2131755521 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnInputDialogCancel, "method 'onViewClicked'");
    view2131755522 = view;
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
    TextInputDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblInputDialogTitle = null;
    target.mTxtInputDialog = null;

    view2131755521.setOnClickListener(null);
    view2131755521 = null;
    view2131755522.setOnClickListener(null);
    view2131755522 = null;
  }
}
