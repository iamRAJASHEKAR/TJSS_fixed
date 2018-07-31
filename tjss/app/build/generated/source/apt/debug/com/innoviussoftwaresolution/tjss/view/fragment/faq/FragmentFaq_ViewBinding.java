// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.faq;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentFaq_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentFaq target;

  @UiThread
  public FragmentFaq_ViewBinding(FragmentFaq target, View source) {
    super(target, source);

    this.target = target;

    target.mLstFaq = Utils.findRequiredViewAsType(source, R.id.lstFaq, "field 'mLstFaq'", RecyclerView.class);
  }

  @Override
  public void unbind() {
    FragmentFaq target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstFaq = null;

    super.unbind();
  }
}
