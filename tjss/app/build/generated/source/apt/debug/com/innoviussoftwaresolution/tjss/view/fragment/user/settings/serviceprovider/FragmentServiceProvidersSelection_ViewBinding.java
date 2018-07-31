// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.serviceprovider;

import android.support.annotation.UiThread;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import com.innoviussoftwaresolution.tjss.view.widget.TouchDisabledViewPager;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentServiceProvidersSelection_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentServiceProvidersSelection target;

  @UiThread
  public FragmentServiceProvidersSelection_ViewBinding(FragmentServiceProvidersSelection target,
      View source) {
    super(target, source);

    this.target = target;

    target.mVpServiceProviderSelection = Utils.findRequiredViewAsType(source, R.id.vpServiceProviderSelection, "field 'mVpServiceProviderSelection'", TouchDisabledViewPager.class);
  }

  @Override
  public void unbind() {
    FragmentServiceProvidersSelection target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mVpServiceProviderSelection = null;

    super.unbind();
  }
}
