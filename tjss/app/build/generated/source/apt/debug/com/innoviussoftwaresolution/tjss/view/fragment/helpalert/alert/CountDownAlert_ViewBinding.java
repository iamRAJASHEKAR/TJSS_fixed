// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.helpalert.alert;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextSwitcher;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CountDownAlert_ViewBinding implements Unbinder {
  private CountDownAlert target;

  private View view2131755394;

  @UiThread
  public CountDownAlert_ViewBinding(final CountDownAlert target, View source) {
    this.target = target;

    View view;
    target.mLblCounter = Utils.findRequiredViewAsType(source, R.id.lblCounter, "field 'mLblCounter'", TextSwitcher.class);
    view = Utils.findRequiredView(source, R.id.btnCancel, "field 'mBtnCancel' and method 'onViewClicked'");
    target.mBtnCancel = Utils.castView(view, R.id.btnCancel, "field 'mBtnCancel'", AppCompatButton.class);
    view2131755394 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    CountDownAlert target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblCounter = null;
    target.mBtnCancel = null;

    view2131755394.setOnClickListener(null);
    view2131755394 = null;
  }
}
