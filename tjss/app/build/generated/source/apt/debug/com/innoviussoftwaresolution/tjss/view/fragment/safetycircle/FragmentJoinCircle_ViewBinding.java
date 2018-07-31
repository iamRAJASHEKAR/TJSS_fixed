// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.safetycircle;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.chaos.view.PinView;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentJoinCircle_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentJoinCircle target;

  private View view2131755441;

  @UiThread
  public FragmentJoinCircle_ViewBinding(final FragmentJoinCircle target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mTxtSecurityCode = Utils.findRequiredViewAsType(source, R.id.txtSecurityCode, "field 'mTxtSecurityCode'", PinView.class);
    view = Utils.findRequiredView(source, R.id.btnSubmitCircleInviteCode, "field 'mBtnSubmitCircleInviteCode' and method 'onViewClicked'");
    target.mBtnSubmitCircleInviteCode = Utils.castView(view, R.id.btnSubmitCircleInviteCode, "field 'mBtnSubmitCircleInviteCode'", AppCompatButton.class);
    view2131755441 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentJoinCircle target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTxtSecurityCode = null;
    target.mBtnSubmitCircleInviteCode = null;

    view2131755441.setOnClickListener(null);
    view2131755441 = null;

    super.unbind();
  }
}
