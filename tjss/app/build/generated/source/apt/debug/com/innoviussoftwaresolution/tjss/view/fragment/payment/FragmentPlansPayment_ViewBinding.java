// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.payment;

import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment_ViewBinding;
import com.vicmikhailau.maskededittext.MaskedEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FragmentPlansPayment_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentPlansPayment target;

  private View view2131755477;

  @UiThread
  public FragmentPlansPayment_ViewBinding(final FragmentPlansPayment target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mTxtPaymentName = Utils.findRequiredViewAsType(source, R.id.txtPaymentName, "field 'mTxtPaymentName'", TextInputEditText.class);
    target.mTxtPaymentPhone = Utils.findRequiredViewAsType(source, R.id.txtPaymentPhone, "field 'mTxtPaymentPhone'", MaskedEditText.class);
    target.mTxtPaymentsCardNumber = Utils.findRequiredViewAsType(source, R.id.txtPaymentsCardNumber, "field 'mTxtPaymentsCardNumber'", MaskedEditText.class);
    target.mTxtPaymentsCardExpiry = Utils.findRequiredViewAsType(source, R.id.txtPaymentsCardExpiry, "field 'mTxtPaymentsCardExpiry'", MaskedEditText.class);
    target.mTxtPaymentsCardCvv = Utils.findRequiredViewAsType(source, R.id.txtPaymentsCardCvv, "field 'mTxtPaymentsCardCvv'", MaskedEditText.class);
    view = Utils.findRequiredView(source, R.id.btnSubmitPaymentDetails, "field 'mBtnSubmitPaymentDetails' and method 'onViewClicked'");
    target.mBtnSubmitPaymentDetails = Utils.castView(view, R.id.btnSubmitPaymentDetails, "field 'mBtnSubmitPaymentDetails'", AppCompatButton.class);
    view2131755477 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.mLblSelectedPlanName = Utils.findRequiredViewAsType(source, R.id.lblSelectedPlanName, "field 'mLblSelectedPlanName'", AppCompatTextView.class);
  }

  @Override
  public void unbind() {
    FragmentPlansPayment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTxtPaymentName = null;
    target.mTxtPaymentPhone = null;
    target.mTxtPaymentsCardNumber = null;
    target.mTxtPaymentsCardExpiry = null;
    target.mTxtPaymentsCardCvv = null;
    target.mBtnSubmitPaymentDetails = null;
    target.mLblSelectedPlanName = null;

    view2131755477.setOnClickListener(null);
    view2131755477 = null;

    super.unbind();
  }
}
