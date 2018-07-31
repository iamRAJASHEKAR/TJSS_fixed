// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.circle;

import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentEditSafetyCircle_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentEditSafetyCircle target;

  private View view2131755398;

  @UiThread
  public FragmentEditSafetyCircle_ViewBinding(final FragmentEditSafetyCircle target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mImgSafetyCircleDp = Utils.findRequiredViewAsType(source, R.id.imgSafetyCircleDp, "field 'mImgSafetyCircleDp'", CircleImageView.class);
    target.mTxtSafetyCircleName = Utils.findRequiredViewAsType(source, R.id.txtSafetyCircleName, "field 'mTxtSafetyCircleName'", TextInputEditText.class);
    target.mLstSafetyCircleMembers = Utils.findRequiredViewAsType(source, R.id.lstSafetyCircleMembers, "field 'mLstSafetyCircleMembers'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.btnChangeSafetyCircleImage, "method 'onViewClicked'");
    view2131755398 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentEditSafetyCircle target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImgSafetyCircleDp = null;
    target.mTxtSafetyCircleName = null;
    target.mLstSafetyCircleMembers = null;

    view2131755398.setOnClickListener(null);
    view2131755398 = null;

    super.unbind();
  }
}
