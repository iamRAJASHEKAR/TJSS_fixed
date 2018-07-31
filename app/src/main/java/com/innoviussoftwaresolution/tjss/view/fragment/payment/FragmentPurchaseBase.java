package com.innoviussoftwaresolution.tjss.view.fragment.payment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.bugsnag.android.Bugsnag;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author Sony Raj on 12-10-17.
 */

public abstract class FragmentPurchaseBase extends BaseFragment {

    private static final int PAYPAL_ONE_TIME_REQUEST_CODE = 999;

    private static final int PAYPAL_SUBSCRIPTION_REQUEST_CODE = 154;
    protected ProgressDialog mProgressDialog;
    private BigDecimal mAmount;

    protected abstract void makeBankPayment();

    protected void showPaymentTypeSelector(final BigDecimal amount, final boolean isSubscription) {
        mAmount = amount;
        M.showAlert(getActivity(), "Payment Type", "Select payment type", "PAYPAL", "BANK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                makePaypalPayment(amount, isSubscription);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                makeBankPayment();
            }
        }, true);
    }

    protected void makePaypalPayment(BigDecimal amount, boolean isSubscription) {

        String paypalClient_ID = "AdkzbXY_qkA2Wu4k1mfSqUE-_936yYe5dZCq7YtaR8NqRX2eyoamT11glMl3zYI6la3XBVOcGYnNEA66";

        PayPalConfiguration configuration = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(paypalClient_ID);

        if (isSubscription) {
            makePaypalSubscription(configuration);
        } else {
            makePaypalOneTimePay(amount, configuration);
        }


    }

    private void makePaypalOneTimePay(BigDecimal amount, PayPalConfiguration configuration) {
        Intent services = new Intent(getActivity(), PayPalService.class);
        services.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        getActivity().startService(services);

        PayPalPayment payment = new PayPalPayment(amount, "USD", "Test payment with Paypal",
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getActivity(), com.paypal.android.sdk.payments.PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_ONE_TIME_REQUEST_CODE);
    }

    private void makePaypalSubscription(PayPalConfiguration configuration) {
        configuration.merchantName("TJSS")
                .merchantPrivacyPolicyUri(Uri.parse("https://www.twixttechnologies.com/privacy"))
                .merchantUserAgreementUri(Uri.parse("https://www.twixttechnologies.com/legal"));

        Intent services = new Intent(getActivity(), PayPalService.class);
        services.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        getActivity().startService(services);

        Intent intent = new Intent(getActivity(), com.paypal.android.sdk.payments.PayPalFuturePaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        startActivityForResult(intent, PAYPAL_SUBSCRIPTION_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            HashMap<String, String> params = new HashMap<>(4);
            params.put("userId", PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_USER_ID, ""));
            params.put("amount", mAmount.toString());
            params.put("status", "success");
            params.put("circleId", PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_PRIMARY_CIRCLE, ""));

            if (requestCode == PAYPAL_SUBSCRIPTION_REQUEST_CODE) {

                PayPalAuthorization auth = data
                        .getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        String authorizationCode = auth.getAuthorizationCode();
                        params.put("authToken", authorizationCode);
                    } catch (Exception e) {
                        Bugsnag.notify(new RuntimeException(e));
                        M.log(e.getMessage());
                    }
                }
            } else if (requestCode == PAYPAL_ONE_TIME_REQUEST_CODE) {
                PaymentConfirmation confirmation = data.getParcelableExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    String state = confirmation.getProofOfPayment().getState();
                    if (state.equals("approved")) {
                        params.put("reference", confirmation.getProofOfPayment().getPaymentId());
                    } else {
                        M.showToast(getActivity(), "error in the payment");
                    }
                } else {
                    M.showToast(getActivity(), "confirmation is null");
                }
            }
            updateStatus(params);
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("FuturePaymentExample", "The user canceled.");
        } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("FuturePaymentExample",
                    "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().stopService(new Intent(getActivity(), PayPalService.class));
        super.onDestroy();
    }

    public abstract void updateStatus(HashMap<String, String> data);

}
