// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.history;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentTimeLineHistoryDetail_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentTimeLineHistoryDetail target;

  @UiThread
  public FragmentTimeLineHistoryDetail_ViewBinding(FragmentTimeLineHistoryDetail target,
      View source) {
    super(target, source);

    this.target = target;

    target.mLblTimeLineDetailSource = Utils.findRequiredViewAsType(source, R.id.lblTimeLineDetailSource, "field 'mLblTimeLineDetailSource'", AppCompatTextView.class);
    target.mLblTimeLineDetailDestination = Utils.findRequiredViewAsType(source, R.id.lblTimeLineDetailDestination, "field 'mLblTimeLineDetailDestination'", AppCompatTextView.class);
    target.mLblTimeLineDetailSourceTime = Utils.findRequiredViewAsType(source, R.id.lblTimeLineDetailSourceTime, "field 'mLblTimeLineDetailSourceTime'", AppCompatTextView.class);
    target.mLblTimeLineDetailDestinationTime = Utils.findRequiredViewAsType(source, R.id.lblTimeLineDetailDestinationTime, "field 'mLblTimeLineDetailDestinationTime'", AppCompatTextView.class);
  }

  @Override
  public void unbind() {
    FragmentTimeLineHistoryDetail target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblTimeLineDetailSource = null;
    target.mLblTimeLineDetailDestination = null;
    target.mLblTimeLineDetailSourceTime = null;
    target.mLblTimeLineDetailDestinationTime = null;

    super.unbind();
  }
}
