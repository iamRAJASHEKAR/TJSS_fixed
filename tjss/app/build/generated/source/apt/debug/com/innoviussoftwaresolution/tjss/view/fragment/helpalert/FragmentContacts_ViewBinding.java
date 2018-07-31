// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.helpalert;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentContacts_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentContacts target;

  @UiThread
  public FragmentContacts_ViewBinding(FragmentContacts target, View source) {
    super(target, source);

    this.target = target;

    target.mLstContacts = Utils.findRequiredViewAsType(source, R.id.lstContacts, "field 'mLstContacts'", RecyclerView.class);
    target.mTxtSearchContacts = Utils.findRequiredViewAsType(source, R.id.txtSearchContacts, "field 'mTxtSearchContacts'", AppCompatEditText.class);
  }

  @Override
  public void unbind() {
    FragmentContacts target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstContacts = null;
    target.mTxtSearchContacts = null;

    super.unbind();
  }
}
