// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.user;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentUserProfile_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentUserProfile target;

  private View view2131755568;

  private View view2131755569;

  private View view2131755566;

  private View view2131755567;

  private View view2131755563;

  @UiThread
  public FragmentUserProfile_ViewBinding(final FragmentUserProfile target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mImgUserProfileImage = Utils.findRequiredViewAsType(source, R.id.imgUserProfileImage, "field 'mImgUserProfileImage'", CircleImageView.class);
    target.mLblUserProfileName = Utils.findRequiredViewAsType(source, R.id.lblUserProfileName, "field 'mLblUserProfileName'", AppCompatTextView.class);
    target.mLblUserProfileEmail = Utils.findRequiredViewAsType(source, R.id.lblUserProfileEmail, "field 'mLblUserProfileEmail'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.lblUserProfileFirstName, "field 'mLblUserProfileFirstName' and method 'onViewClicked'");
    target.mLblUserProfileFirstName = Utils.castView(view, R.id.lblUserProfileFirstName, "field 'mLblUserProfileFirstName'", AppCompatTextView.class);
    view2131755568 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.lblUserProfileLastName, "field 'mLblUserProfileLastName' and method 'onViewClicked'");
    target.mLblUserProfileLastName = Utils.castView(view, R.id.lblUserProfileLastName, "field 'mLblUserProfileLastName'", AppCompatTextView.class);
    view2131755569 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.mLblUserProfileEmail2 = Utils.findRequiredViewAsType(source, R.id.lblUserProfileEmail2, "field 'mLblUserProfileEmail2'", AppCompatTextView.class);
    target.mCboUserProfileIsdCodes = Utils.findRequiredViewAsType(source, R.id.cboUserProfileIsdCodes, "field 'mCboUserProfileIsdCodes'", AppCompatSpinner.class);
    target.mLblUserProfilePhone = Utils.findRequiredViewAsType(source, R.id.lblUserProfilePhone, "field 'mLblUserProfilePhone'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.btnUserProfileMyAccount, "method 'onViewClicked'");
    view2131755566 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnUserProfileDeleteAccount, "method 'onViewClicked'");
    view2131755567 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnUserProfileChangeImage, "method 'onViewClicked'");
    view2131755563 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  public void unbind() {
    FragmentUserProfile target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImgUserProfileImage = null;
    target.mLblUserProfileName = null;
    target.mLblUserProfileEmail = null;
    target.mLblUserProfileFirstName = null;
    target.mLblUserProfileLastName = null;
    target.mLblUserProfileEmail2 = null;
    target.mCboUserProfileIsdCodes = null;
    target.mLblUserProfilePhone = null;

    view2131755568.setOnClickListener(null);
    view2131755568 = null;
    view2131755569.setOnClickListener(null);
    view2131755569 = null;
    view2131755566.setOnClickListener(null);
    view2131755566 = null;
    view2131755567.setOnClickListener(null);
    view2131755567 = null;
    view2131755563.setOnClickListener(null);
    view2131755563 = null;

    super.unbind();
  }
}
