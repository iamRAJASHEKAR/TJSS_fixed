// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.message;

import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentCreateGroupChat_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentCreateGroupChat target;

  private View view2131755410;

  @UiThread
  public FragmentCreateGroupChat_ViewBinding(final FragmentCreateGroupChat target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mTxtFavPlaceName = Utils.findRequiredViewAsType(source, R.id.txtFavPlaceName, "field 'mTxtFavPlaceName'", TextInputEditText.class);
    target.mLstSafetyCircleMembers = Utils.findRequiredViewAsType(source, R.id.lstSafetyCircleMembers, "field 'mLstSafetyCircleMembers'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.btnCreateGroupChat, "field 'mBtnCreateGroupChat' and method 'onViewClicked'");
    target.mBtnCreateGroupChat = Utils.castView(view, R.id.btnCreateGroupChat, "field 'mBtnCreateGroupChat'", AppCompatButton.class);
    view2131755410 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentCreateGroupChat target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTxtFavPlaceName = null;
    target.mLstSafetyCircleMembers = null;
    target.mBtnCreateGroupChat = null;

    view2131755410.setOnClickListener(null);
    view2131755410 = null;

    super.unbind();
  }
}
