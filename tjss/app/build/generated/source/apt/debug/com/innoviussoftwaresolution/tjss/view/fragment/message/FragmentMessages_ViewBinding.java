// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.message;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import com.innoviussoftwaresolution.tjss.view.widget.MessagesFab;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentMessages_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentMessages target;

  @UiThread
  public FragmentMessages_ViewBinding(FragmentMessages target, View source) {
    super(target, source);

    this.target = target;

    target.mLblNoMessage = Utils.findRequiredViewAsType(source, R.id.lblNoMessage, "field 'mLblNoMessage'", AppCompatTextView.class);
    target.mLstChats = Utils.findRequiredViewAsType(source, R.id.lstChats, "field 'mLstChats'", RecyclerView.class);
    target.mBtnMessage = Utils.findRequiredViewAsType(source, R.id.btnMessage, "field 'mBtnMessage'", MessagesFab.class);
  }

  @Override
  public void unbind() {
    FragmentMessages target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblNoMessage = null;
    target.mLstChats = null;
    target.mBtnMessage = null;

    super.unbind();
  }
}
