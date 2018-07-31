// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.signup;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignUpStepSixSelectPlan_ViewBinding extends BaseFragment_ViewBinding {
  private SignUpStepSixSelectPlan target;

  @UiThread
  public SignUpStepSixSelectPlan_ViewBinding(SignUpStepSixSelectPlan target, View source) {
    super(target, source);

    this.target = target;

    target.mLblSelectedPlanType = Utils.findRequiredViewAsType(source, R.id.lblSelectedPlanType, "field 'mLblSelectedPlanType'", AppCompatTextView.class);
    target.mLblSelectedPlanExtra = Utils.findRequiredViewAsType(source, R.id.lblSelectedPlanExtra, "field 'mLblSelectedPlanExtra'", AppCompatTextView.class);
    target.mLblSelectedPlanFee = Utils.findRequiredViewAsType(source, R.id.lblSelectedPlanFee, "field 'mLblSelectedPlanFee'", AppCompatTextView.class);
    target.mLblSelectedPlanDescription = Utils.findRequiredViewAsType(source, R.id.lblSelectedPlanDescription, "field 'mLblSelectedPlanDescription'", AppCompatTextView.class);
    target.mBtnSelectedPlanDetails = Utils.findRequiredViewAsType(source, R.id.btnSelectedPlanDetails, "field 'mBtnSelectedPlanDetails'", AppCompatButton.class);
    target.mLblSelectedPLan = Utils.findRequiredViewAsType(source, R.id.lblSelectedPLan, "field 'mLblSelectedPLan'", AppCompatTextView.class);
    target.mLtSelectedPlanCard = Utils.findRequiredViewAsType(source, R.id.ltSelectedPlanCard, "field 'mLtSelectedPlanCard'", CardView.class);
    target.mLtPlans = Utils.findRequiredViewAsType(source, R.id.lrPlans, "field 'mLtPlans'", LinearLayout.class);
  }

  @Override
  public void unbind() {
    SignUpStepSixSelectPlan target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLblSelectedPlanType = null;
    target.mLblSelectedPlanExtra = null;
    target.mLblSelectedPlanFee = null;
    target.mLblSelectedPlanDescription = null;
    target.mBtnSelectedPlanDetails = null;
    target.mLblSelectedPLan = null;
    target.mLtSelectedPlanCard = null;
    target.mLtPlans = null;

    super.unbind();
  }
}
