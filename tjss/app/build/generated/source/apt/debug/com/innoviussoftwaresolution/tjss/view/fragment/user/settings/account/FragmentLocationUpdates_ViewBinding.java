// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.account;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentLocationUpdates_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentLocationUpdates target;

  private View view2131755452;

  @UiThread
  public FragmentLocationUpdates_ViewBinding(final FragmentLocationUpdates target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mSkLocationUpdateDuration = Utils.findRequiredViewAsType(source, R.id.skLocationUpdateDuration, "field 'mSkLocationUpdateDuration'", AppCompatSeekBar.class);
    target.mLblLocationUpdateDuration = Utils.findRequiredViewAsType(source, R.id.lblLocationUpdateDuration, "field 'mLblLocationUpdateDuration'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.btnUpdateLocationDuration, "field 'mBtnUpdateLocationDuration' and method 'onViewClicked'");
    target.mBtnUpdateLocationDuration = Utils.castView(view, R.id.btnUpdateLocationDuration, "field 'mBtnUpdateLocationDuration'", AppCompatButton.class);
    view2131755452 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentLocationUpdates target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mSkLocationUpdateDuration = null;
    target.mLblLocationUpdateDuration = null;
    target.mBtnUpdateLocationDuration = null;

    view2131755452.setOnClickListener(null);
    view2131755452 = null;

    super.unbind();
  }
}
