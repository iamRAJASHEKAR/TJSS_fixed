package com.twixttechnologies.tjss.view.fragment.payment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.CoinBalance;
import com.twixttechnologies.tjss.model.network.response.PaymentStatus;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.utils.PreferenceHelper;
import com.twixttechnologies.tjss.view.activity.PaymentActivity;
import com.twixttechnologies.tjss.viewmodel.CoinPurchaseViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Sony Raj on 07-10-17.
 */

public class FragmentCoinPurchase extends FragmentPurchaseBase {

    public static final String TAG = "coins";
    private final Observer<PaymentStatus> mStatusMessageObserver
            = new Observer<PaymentStatus>() {
        @Override
        public void onChanged(@Nullable PaymentStatus statusMessage) {
            if (mProgressDialog != null && mProgressDialog.isShowing())
                mProgressDialog.dismiss();
            if (statusMessage == null || statusMessage.paymentStatus == null
                    || statusMessage.paymentStatus.equals("") || !statusMessage.paymentStatus.contains("success")) {
                M.showAlert(getActivity(), "Purchase coins", "An error occurred, please try again later", "OK", null, null, null, true);
                return;
            }
            if (statusMessage.paymentStatus.toLowerCase().contains("success")) {
                M.showAlert(getActivity(), "Purchase coins", "Payment Success!", "OK", null, null, null, true);
            }
        }
    };
    @BindView(R.id.imgWalletDp)
    CircleImageView mImgWalletDp;
    @BindView(R.id.lblWalletName)
    AppCompatTextView mLblWalletName;
    @BindView(R.id.lblCoinsInYourWallet)
    AppCompatTextView mLblCoinsInYourWallet;
    private final Observer<CoinBalance> mCoinBalanceObserver
            = new Observer<CoinBalance>() {
        @Override
        public void onChanged(@Nullable CoinBalance coinBalance) {
            if (coinBalance == null) return;
            if (coinBalance.coinBalance == null || coinBalance.coinBalance.equals("")) {
                mLblCoinsInYourWallet.setText("0");
            } else {
                mLblCoinsInYourWallet.setText(coinBalance.coinBalance);
            }
        }
    };
    @BindView(R.id.cboSelectCoinsToPurchase)
    AppCompatSpinner mCboSelectCoinsToPurchase;
    @BindView(R.id.btnPurchaseCoins)
    AppCompatButton mBtnPurchaseCoins;
    Unbinder unbinder;
    private int mSelectedCoinsCount = 0;
    private CoinPurchaseViewModel mViewModel;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coin_purchase, container, false);
        unbinder = ButterKnife.bind(this, view);
        setCoins();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new CoinPurchaseViewModel.CoinPurchaseViewModelFactory(getActivity().getApplication()))
                .get(CoinPurchaseViewModel.class);

        mViewModel.getCoinBalanceData().observe(this, mCoinBalanceObserver);
        mViewModel.getStatusMessageData().observe(this, mStatusMessageObserver);

        Glide.with(this)
                .load(getString(R.string.image_base_url) + PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_PROFILE_IMAGE, ""))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        mImgWalletDp.setImageDrawable(resource);
                    }
                });

        mLblWalletName.setText(PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_FIRST_NAME, ""));

    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getCoinBalance();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnPurchaseCoins)
    public void onViewClicked() {
        if (mSelectedCoinsCount == 0) {
            M.showAlert(getActivity(), "Purchse Coins", "Pease select the number of coins to purchase",
                    "OK", null, null, null, false);
            return;
        }

        showPaymentTypeSelector(new BigDecimal(mSelectedCoinsCount), false);

    }

    @Override
    public void makeBankPayment() {
        Intent purchaseIntent = new Intent(getActivity(), PaymentActivity.class);
        purchaseIntent.putExtra(PaymentActivity.PAYMENT_TYPE, PaymentActivity.PAYMENT_TYPE_COINS);
        purchaseIntent.putExtra(PaymentActivity.NUMBER_OF_COINS, mSelectedCoinsCount);
        startActivity(purchaseIntent);
    }

    @Override
    public void updateStatus(HashMap<String, String> data) {
        mViewModel.savePaypalPayment(data);
    }


    private void setCoins() {
        final ArrayList<String> coins = new ArrayList<>(7);
        coins.add("Select Coins...");
        coins.add("100 Coins");
        coins.add("200 Coins");
        coins.add("300 Coins");
        coins.add("400 Coins");
        coins.add("500 Coins");
        coins.add("1000 Coins");
        ArrayAdapter<String> coinsAdapter =
                new ArrayAdapter<>(getActivity(), R.layout.extra_simple_spinner_item,
                        R.id.lblSpinnerLabel, coins);

        mCboSelectCoinsToPurchase.setAdapter(coinsAdapter);

        mCboSelectCoinsToPurchase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position <= 0) return; //0th position is select coins, we don't want that
                mSelectedCoinsCount = Integer.parseInt(coins.get(position).split(" ")[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

}
