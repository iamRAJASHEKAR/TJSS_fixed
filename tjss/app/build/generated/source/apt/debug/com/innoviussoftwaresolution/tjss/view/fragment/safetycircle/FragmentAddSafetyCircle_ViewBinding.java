// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.safetycircle;

import android.support.annotation.UiThread;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentAddSafetyCircle_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentAddSafetyCircle target;

  private View view2131755370;

  private View view2131755371;

  private View view2131755372;

  private View view2131755373;

  private View view2131755374;

  private View view2131755375;

  private View view2131755376;

  @UiThread
  public FragmentAddSafetyCircle_ViewBinding(final FragmentAddSafetyCircle target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mChkCreateSafetyCirclePrimaryOrNot = Utils.findRequiredViewAsType(source, R.id.chkCreateSafetyCirclePrimaryOrNot, "field 'mChkCreateSafetyCirclePrimaryOrNot'", AppCompatCheckBox.class);
    target.mTxtSafetyCircleName = Utils.findRequiredViewAsType(source, R.id.txtSafetyCircleName, "field 'mTxtSafetyCircleName'", TextInputLayout.class);
    view = Utils.findRequiredView(source, R.id.safetyCircleNameFriends, "field 'mSafetyCircleNameFriends' and method 'onViewClicked'");
    target.mSafetyCircleNameFriends = Utils.castView(view, R.id.safetyCircleNameFriends, "field 'mSafetyCircleNameFriends'", AppCompatButton.class);
    view2131755370 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.safetyCircleNameColleagues, "field 'mSafetyCircleNameColleagues' and method 'onViewClicked'");
    target.mSafetyCircleNameColleagues = Utils.castView(view, R.id.safetyCircleNameColleagues, "field 'mSafetyCircleNameColleagues'", AppCompatButton.class);
    view2131755371 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.safetyCircleNameGymBuddies, "field 'mSafetyCircleNameGymBuddies' and method 'onViewClicked'");
    target.mSafetyCircleNameGymBuddies = Utils.castView(view, R.id.safetyCircleNameGymBuddies, "field 'mSafetyCircleNameGymBuddies'", AppCompatButton.class);
    view2131755372 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.safetyCircleNameSiblings, "field 'mSafetyCircleNameSiblings' and method 'onViewClicked'");
    target.mSafetyCircleNameSiblings = Utils.castView(view, R.id.safetyCircleNameSiblings, "field 'mSafetyCircleNameSiblings'", AppCompatButton.class);
    view2131755373 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.safetyCircleNameSpecialPersons, "field 'mSafetyCircleNameSpecialPersons' and method 'onViewClicked'");
    target.mSafetyCircleNameSpecialPersons = Utils.castView(view, R.id.safetyCircleNameSpecialPersons, "field 'mSafetyCircleNameSpecialPersons'", AppCompatButton.class);
    view2131755374 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.safetyCircleNameFamily, "field 'mSafetyCircleNameFamily' and method 'onViewClicked'");
    target.mSafetyCircleNameFamily = Utils.castView(view, R.id.safetyCircleNameFamily, "field 'mSafetyCircleNameFamily'", AppCompatButton.class);
    view2131755375 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnDone, "field 'mBtnDone' and method 'onViewClicked'");
    target.mBtnDone = Utils.castView(view, R.id.btnDone, "field 'mBtnDone'", AppCompatButton.class);
    view2131755376 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  public void unbind() {
    FragmentAddSafetyCircle target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mChkCreateSafetyCirclePrimaryOrNot = null;
    target.mTxtSafetyCircleName = null;
    target.mSafetyCircleNameFriends = null;
    target.mSafetyCircleNameColleagues = null;
    target.mSafetyCircleNameGymBuddies = null;
    target.mSafetyCircleNameSiblings = null;
    target.mSafetyCircleNameSpecialPersons = null;
    target.mSafetyCircleNameFamily = null;
    target.mBtnDone = null;

    view2131755370.setOnClickListener(null);
    view2131755370 = null;
    view2131755371.setOnClickListener(null);
    view2131755371 = null;
    view2131755372.setOnClickListener(null);
    view2131755372 = null;
    view2131755373.setOnClickListener(null);
    view2131755373 = null;
    view2131755374.setOnClickListener(null);
    view2131755374 = null;
    view2131755375.setOnClickListener(null);
    view2131755375 = null;
    view2131755376.setOnClickListener(null);
    view2131755376 = null;

    super.unbind();
  }
}
