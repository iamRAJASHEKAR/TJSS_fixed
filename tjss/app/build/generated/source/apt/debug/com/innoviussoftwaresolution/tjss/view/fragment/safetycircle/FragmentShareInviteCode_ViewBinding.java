// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.safetycircle;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentShareInviteCode_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentShareInviteCode target;

  private View view2131755509;

  @UiThread
  public FragmentShareInviteCode_ViewBinding(final FragmentShareInviteCode target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mLblInviteCode = Utils.findRequiredViewAsType(source, R.id.lblInviteCode, "field 'mLblInviteCode'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.btnSendCode, "method 'onViewClicked'");
    view2131755509 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentShareInviteCode target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblInviteCode = null;

    view2131755509.setOnClickListener(null);
    view2131755509 = null;

    super.unbind();
  }
}
