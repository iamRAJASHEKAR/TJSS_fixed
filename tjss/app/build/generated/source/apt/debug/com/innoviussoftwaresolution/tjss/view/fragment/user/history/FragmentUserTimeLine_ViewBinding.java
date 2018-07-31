// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user.history;

import android.support.annotation.UiThread;
import android.support.v4.widget.Space;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentUserTimeLine_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentUserTimeLine target;

  @UiThread
  public FragmentUserTimeLine_ViewBinding(FragmentUserTimeLine target, View source) {
    super(target, source);

    this.target = target;

    target.mViewTransparent = Utils.findRequiredViewAsType(source, R.id.viewTransparent, "field 'mViewTransparent'", Space.class);
    target.mImgTimeLineUserImage = Utils.findRequiredViewAsType(source, R.id.imgTimeLineUserImage, "field 'mImgTimeLineUserImage'", CircleImageView.class);
    target.mLblTimeLineUserName = Utils.findRequiredViewAsType(source, R.id.lblTimeLineUserName, "field 'mLblTimeLineUserName'", AppCompatTextView.class);
    target.mLblTimeLineLastUpdateTime = Utils.findRequiredViewAsType(source, R.id.lblTimeLineLastUpdateTime, "field 'mLblTimeLineLastUpdateTime'", AppCompatTextView.class);
    target.mUserTimeLineCard = Utils.findRequiredViewAsType(source, R.id.userTimeLineCard, "field 'mUserTimeLineCard'", CardView.class);
    target.mLstTimeline = Utils.findRequiredViewAsType(source, R.id.lstTimeline, "field 'mLstTimeline'", RecyclerView.class);
  }

  @Override
  public void unbind() {
    FragmentUserTimeLine target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mViewTransparent = null;
    target.mImgTimeLineUserImage = null;
    target.mLblTimeLineUserName = null;
    target.mLblTimeLineLastUpdateTime = null;
    target.mUserTimeLineCard = null;
    target.mLstTimeline = null;

    super.unbind();
  }
}
