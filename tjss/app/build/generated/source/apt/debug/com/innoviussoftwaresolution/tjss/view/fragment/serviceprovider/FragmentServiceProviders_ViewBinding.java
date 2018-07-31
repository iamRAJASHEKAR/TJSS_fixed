// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.serviceprovider;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentServiceProviders_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentServiceProviders target;

  private View view2131755494;

  @UiThread
  public FragmentServiceProviders_ViewBinding(final FragmentServiceProviders target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mLstServiceProviders = Utils.findRequiredViewAsType(source, R.id.lstServiceProviders, "field 'mLstServiceProviders'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.btnServiceProvidersSendHelpAlert, "field 'mBthServiceProvidersSendHelpAlert' and method 'onViewClicked'");
    target.mBthServiceProvidersSendHelpAlert = Utils.castView(view, R.id.btnServiceProvidersSendHelpAlert, "field 'mBthServiceProvidersSendHelpAlert'", AppCompatButton.class);
    view2131755494 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.mLblTitle = Utils.findRequiredViewAsType(source, R.id.lblTitle, "field 'mLblTitle'", AppCompatTextView.class);
  }

  @Override
  public void unbind() {
    FragmentServiceProviders target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLstServiceProviders = null;
    target.mBthServiceProvidersSendHelpAlert = null;
    target.mLblTitle = null;

    view2131755494.setOnClickListener(null);
    view2131755494 = null;

    super.unbind();
  }
}
