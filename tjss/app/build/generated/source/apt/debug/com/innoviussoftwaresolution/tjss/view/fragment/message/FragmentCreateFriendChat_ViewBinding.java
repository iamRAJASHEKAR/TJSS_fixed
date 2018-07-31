// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.message;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentCreateFriendChat_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentCreateFriendChat target;

  private View view2131755396;

  @UiThread
  public FragmentCreateFriendChat_ViewBinding(final FragmentCreateFriendChat target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mLstFriends = Utils.findRequiredViewAsType(source, R.id.lstFriends, "field 'mLstFriends'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.btnCreateSingleChat, "field 'mBtnCreateSingleChat' and method 'onViewClicked'");
    target.mBtnCreateSingleChat = Utils.castView(view, R.id.btnCreateSingleChat, "field 'mBtnCreateSingleChat'", AppCompatButton.class);
    view2131755396 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentCreateFriendChat target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstFriends = null;
    target.mBtnCreateSingleChat = null;

    view2131755396.setOnClickListener(null);
    view2131755396 = null;

    super.unbind();
  }
}
