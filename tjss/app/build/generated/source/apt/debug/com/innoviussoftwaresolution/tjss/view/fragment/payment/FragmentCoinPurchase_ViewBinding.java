// Generated code from Butter Knife. Do not modify!
package com.innoviussoftwaresolution.tjss.view.fragment.payment;

import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatButton;
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

public class FragmentCoinPurchase_ViewBinding extends BaseFragment_ViewBinding {
  private FragmentCoinPurchase target;

  private View view2131755392;

  @UiThread
  public FragmentCoinPurchase_ViewBinding(final FragmentCoinPurchase target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.mImgWalletDp = Utils.findRequiredViewAsType(source, R.id.imgWalletDp, "field 'mImgWalletDp'", CircleImageView.class);
    target.mLblWalletName = Utils.findRequiredViewAsType(source, R.id.lblWalletName, "field 'mLblWalletName'", AppCompatTextView.class);
    target.mLblCoinsInYourWallet = Utils.findRequiredViewAsType(source, R.id.lblCoinsInYourWallet, "field 'mLblCoinsInYourWallet'", AppCompatTextView.class);
    target.mCboSelectCoinsToPurchase = Utils.findRequiredViewAsType(source, R.id.cboSelectCoinsToPurchase, "field 'mCboSelectCoinsToPurchase'", AppCompatSpinner.class);
    view = Utils.findRequiredView(source, R.id.btnPurchaseCoins, "field 'mBtnPurchaseCoins' and method 'onViewClicked'");
    target.mBtnPurchaseCoins = Utils.castView(view, R.id.btnPurchaseCoins, "field 'mBtnPurchaseCoins'", AppCompatButton.class);
    view2131755392 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  public void unbind() {
    FragmentCoinPurchase target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImgWalletDp = null;
    target.mLblWalletName = null;
    target.mLblCoinsInYourWallet = null;
    target.mCboSelectCoinsToPurchase = null;
    target.mBtnPurchaseCoins = null;

    view2131755392.setOnClickListener(null);
    view2131755392 = null;

    super.unbind();
  }
}
