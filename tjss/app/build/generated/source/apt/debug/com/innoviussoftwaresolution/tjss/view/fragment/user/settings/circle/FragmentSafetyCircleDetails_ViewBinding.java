// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.circle;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentSafetyCircleDetails_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentSafetyCircleDetails target;

  private View view2131755482;

  @UiThread
  public FragmentSafetyCircleDetails_ViewBinding(final FragmentSafetyCircleDetails target,
      View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mLblSafetyCircleName = Utils.findRequiredViewAsType(source, R.id.lblSafetyCircleName, "field 'mLblSafetyCircleName'", AppCompatTextView.class);
    target.mLstSafetyCircleMembers = Utils.findRequiredViewAsType(source, R.id.lstSafetyCircleMembers, "field 'mLstSafetyCircleMembers'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.btnLeaveSafetyCircle, "field 'mBtnLeaveSafetyCircle' and method 'onViewClicked'");
    target.mBtnLeaveSafetyCircle = Utils.castView(view, R.id.btnLeaveSafetyCircle, "field 'mBtnLeaveSafetyCircle'", AppCompatButton.class);
    view2131755482 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.mImgSafetyCircleDp = Utils.findRequiredViewAsType(source, R.id.imgSafetyCircleDp, "field 'mImgSafetyCircleDp'", CircleImageView.class);
  }

  @Override
  public void unbind() {
    FragmentSafetyCircleDetails target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblSafetyCircleName = null;
    target.mLstSafetyCircleMembers = null;
    target.mBtnLeaveSafetyCircle = null;
    target.mImgSafetyCircleDp = null;

    view2131755482.setOnClickListener(null);
    view2131755482 = null;

    super.unbind();
  }
}
