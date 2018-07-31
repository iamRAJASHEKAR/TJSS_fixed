package com.twixttechnologies.tjss.view.fragment.payment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.innoviussoftwaresolution.tjss.R;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.twixttechnologies.tjss.model.network.request.PayStackSaveData;
import com.twixttechnologies.tjss.model.network.response.PayStackTokenData;
import com.twixttechnologies.tjss.model.network.response.PaymentResponse;
import com.twixttechnologies.tjss.model.network.response.PaymentStatus;
import com.twixttechnologies.tjss.model.network.response.PlanAmount;
import com.twixttechnologies.tjss.utils.DependencyHandler;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.utils.PreferenceHelper;
import com.twixttechnologies.tjss.utils.TransactionCallback;
import com.twixttechnologies.tjss.view.activity.HomeActivity;
import com.twixttechnologies.tjss.view.activity.PaymentActivity;
import com.twixttechnologies.tjss.viewmodel.PaymentsViewModel;
import com.vicmikhailau.maskededittext.MaskedEditText;

import java.text.MessageFormat;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.exceptions.ExpiredAccessCodeException;
import co.paystack.android.model.Charge;

/**
 * @author Sony Raj on 24-07-17.
 */

public class FragmentPlansPayment extends FragmentPurchaseBase implements TransactionCallback<Token, Exception> {


    @BindView(R.id.txtPaymentName)
    TextInputEditText mTxtPaymentName;

    @BindView(R.id.txtPaymentPhone)
    MaskedEditText mTxtPaymentPhone;

    @BindView(R.id.txtPaymentsCardNumber)
    MaskedEditText mTxtPaymentsCardNumber;

    @BindView(R.id.txtPaymentsCardExpiry)
    MaskedEditText mTxtPaymentsCardExpiry;

    @BindView(R.id.txtPaymentsCardCvv)
    MaskedEditText mTxtPaymentsCardCvv;

    @BindView(R.id.btnSubmitPaymentDetails)
    AppCompatButton mBtnSubmitPaymentDetails;

    @BindView(R.id.lblSelectedPlanName)
    AppCompatTextView mLblSelectedPlanName;

    Unbinder unbinder;
    private boolean gotOtp = false;
    private co.paystack.android.model.Card mPayStackCard;
    private Transaction mPayStackTransaction;
    private DependencyHandler mDependencyHandler;
    private Bundle mData;
    private final Observer<PayStackSaveData> mSaveDataObserver
            = new Observer<PayStackSaveData>() {
        @Override
        public void onChanged(@Nullable PayStackSaveData payStackSaveData) {
            if (getActivity() == null) return;
            dismissDialog();
            if (payStackSaveData == null) {
                M.showAlert(getActivity(), getActivity().getString(R.string.app_name),
                        "Failed to make payment ,Please try again later", "OK", null, null, null, false);
            } else {
                if (mCallingFor == PaymentActivity.PAYMENT_TYPE_PLAN) {
                    String planId = mData.getString(PaymentActivity.PLAIN_ID, "");
                    PreferenceHelper.saveString(getActivity(), PreferenceHelper.KEY_SUBSCRIPTION_PLAN_ID, planId);
                }
                M.showAlert(getActivity(), getActivity().getString(R.string.app_name),
                        "Payment Success", "OK", null, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showHome();
                            }
                        }, null, false);
            }

        }
    };
    private int mCallingFor;
    private final Observer<PaymentStatus> mStatusMessageObserver
            = new Observer<PaymentStatus>() {
        @Override
        public void onChanged(@Nullable PaymentStatus statusMessage) {
            if (getActivity() == null) return;
            if (mProgressDialog != null && mProgressDialog.isShowing())
                mProgressDialog.dismiss();
            if (statusMessage == null || statusMessage.paymentStatus == null
                    || statusMessage.paymentStatus.equals("") || !statusMessage.paymentStatus.contains("success")) {
                M.showAlert(getActivity(), "Purchase coins", "An error occurred, please try again later", "OK", null, null, null, true);
                return;
            }
            if (statusMessage.paymentStatus.toLowerCase().contains("success")) {
                if (mCallingFor == PaymentActivity.PAYMENT_TYPE_PLAN) {
                    String planId = mData.getString(PaymentActivity.PLAIN_ID, "");
                    PreferenceHelper.saveString(getActivity(), PreferenceHelper.KEY_SUBSCRIPTION_PLAN_ID, planId);
                }
                M.showAlert(getActivity(), "Purchase coins", "Payment saved successfully", "OK", null, null, null, true);
            }
        }
    };
    private PaymentsViewModel mViewModel;
    private final Observer<PayStackTokenData> mPayStackTokenObserver
            = new Observer<PayStackTokenData>() {
        @Override
        public void onChanged(@Nullable PayStackTokenData payStackTokenData) {
            if (payStackTokenData == null || !payStackTokenData.status) {
                if (mViewModel != null) {
                    dismissDialog();
                    int retryAttempts = mViewModel.getRetryAttempts();
                    if (retryAttempts < 5) {
                        mViewModel.setRetryAttempts(++retryAttempts);
                        String email = PreferenceHelper.getString(mViewModel.getApplication(),
                                PreferenceHelper.KEY_EMAIL, "");
                        String planId = mData.getString(PaymentActivity.PLAIN_ID, "");
                        if (!email.equals("") && !planId.equals(""))
                            mViewModel.getPayStackToken(email, planId, String.valueOf(mData.getInt(PaymentActivity.NUMBER_OF_COINS, 0)));
                    } else {
                        dismissDialog();
                        if (getActivity() == null) return;
                        M.showAlert(getActivity(), "Payment", "An error occurred when contacting payment server, Please try again later.", "OK", null, null, null, false);
                    }
                }
            } else {
                try {
                    chargePayStackCard(payStackTokenData.data.accessCode);
                } catch (Exception e) {
                    M.log(e.getMessage());
                }
            }

        }
    };
    private Observer<PlanAmount> mPlanAmountObserver;
    private Observer<PaymentResponse> mPaymentObserver
            = new Observer<PaymentResponse>() {
        @Override
        public void onChanged(@Nullable PaymentResponse paymentResponse) {
            if (getActivity() == null) return;
            dismissDialog();
            if (paymentResponse != null && paymentResponse.paymentStatus.toLowerCase().equals("success")) {
                if (mCallingFor == PaymentActivity.PAYMENT_TYPE_PLAN){
                    PreferenceHelper.saveString(getActivity(),PreferenceHelper.KEY_SUBSCRIPTION_PLAN_ID,mData.getString(PaymentActivity.PLAIN_ID,""));
                }
                M.showAlert(getActivity(),
                        mCallingFor == PaymentActivity.PAYMENT_TYPE_PLAN ? "Select Plan" : "Purchase coins",
                        "Payment Successful", "OK", null, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showHome();
                            }
                        }, null, false);
            } else {
                M.showAlert(getActivity(),
                        mCallingFor == PaymentActivity.PAYMENT_TYPE_PLAN ? "Select Plan" : "Purchase coins",
                        "An error occurred, Please try again later", "OK", null, null, null, false);
            }

        }
    };

    public static FragmentPlansPayment newInstance(Bundle data) {
        FragmentPlansPayment fragment = new FragmentPlansPayment();
        fragment.setArguments(data);
        return fragment;
    }

    private void showHome() {
        dismissDialog();
        String planId = mData.getString(PaymentActivity.PLAIN_ID, "");
        PreferenceHelper.saveString(getActivity(), PreferenceHelper.KEY_SUBSCRIPTION_PLAN_ID, planId);
        Intent home = new Intent(getActivity(), HomeActivity.class);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home);
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        mData = getArguments();
        mCallingFor = mData.getInt(PaymentActivity.PAYMENT_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plans_payment, container, false);
        unbinder = ButterKnife.bind(this, view);
        mLblSelectedPlanName.setText(MessageFormat.format("You selected {0} plan", mData.getString(PaymentActivity.PLAN_NAME)));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDependencyHandler = new DependencyHandler(getActivity());
        mViewModel = ViewModelProviders.of(this,
                new PaymentsViewModel.PaymentsViewModelFactory(getActivity().getApplication()))
                .get(PaymentsViewModel.class);
        mViewModel.getPaymentsResponseData().observe(this, mPaymentObserver);
        mViewModel.getmPayStackTokenData().observe(this, mPayStackTokenObserver);
        mViewModel.getPayStackSaveData().observe(this, mSaveDataObserver);
        mViewModel.getPlanAmount(mData.getString(PaymentActivity.PLAIN_ID, ""));
        mViewModel.getStatusMessageData().observe(this, mStatusMessageObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccess(Token token) {
        if (mProgressDialog != null) mProgressDialog.dismiss();
        if (mCallingFor == PaymentActivity.PAYMENT_TYPE_PLAN) {
            String planId = mData.getString(PaymentActivity.PLAIN_ID, "");
            String stripePlan = mData.getString(PaymentActivity.STRIPE_PLAN, "");
            mViewModel.purchasePlan(planId, mTxtPaymentPhone.getText().toString(), stripePlan, token.getId());
        } else if (mCallingFor == PaymentActivity.PAYMENT_TYPE_COINS) {
            mViewModel.purchaseCoins(mData.getInt(PaymentActivity.NUMBER_OF_COINS), token.getId());
        }
    }

    @OnClick(R.id.btnSubmitPaymentDetails)
    public void onViewClicked() {
        M.showAlert(getActivity(), "Select Payment Method", "", "STRIPE", "PAYSTACK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String[] parts = mTxtPaymentsCardExpiry.getText().toString().split("/");
                Card card = new Card(mTxtPaymentsCardNumber.getUnMaskedText(), Integer.valueOf(parts[0]),
                        Integer.parseInt(parts[1]), mTxtPaymentsCardCvv.getUnMaskedText());

                if (!card.validateCard()) {
                    M.showAlert(getActivity(), "Payment", "Please review your input", "OK", null, null, null, false);
                    return;
                }
                initProgress();
                mDependencyHandler.attachAsyncTaskTokenController(card, FragmentPlansPayment.this);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initProgress();
                if (mCallingFor == PaymentActivity.PAYMENT_TYPE_PLAN && mViewModel.getPlanAmountData().getValue() == null) {
                    mPlanAmountObserver = new Observer<PlanAmount>() {
                        @Override
                        public void onChanged(@Nullable PlanAmount planAmount) {
                            if ((planAmount == null) || (planAmount.amount == null)) {
                                //Show error if necessary
                                M.log("Plan amount is null");
                                return;
                            }
                            showPaymentTypeSelector(planAmount.amount, true);
                        }
                    };
                    mViewModel.getPlanAmountData().observe(FragmentPlansPayment.this, mPlanAmountObserver);
                } else {
                    //showPaymentTypeSelector(mViewModel.getPlanAmountData().getValue().amount, true);
                    getPayStackToken();
                }
            }
        }, true);


    }

    //region PayStack

    @Override
    public void onFailure(Exception e) {
        if (mProgressDialog != null) mProgressDialog.dismiss();
        if (getActivity() == null) return;
        Toast.makeText(getActivity(), "Transaction Error", Toast.LENGTH_LONG).show();
        M.log(e.getMessage());
    }

    private void getPayStackToken() {
        validatePayStackCard();
        if (mPayStackCard == null || !mPayStackCard.isValid()) return;
        initProgress();
        String email = PreferenceHelper.getString(mViewModel.getApplication(),
                PreferenceHelper.KEY_EMAIL, "");
        String planId = mData.getString(PaymentActivity.PLAIN_ID, "");

        String coins = String.valueOf(mData.getInt(PaymentActivity.NUMBER_OF_COINS, 0));

        mViewModel.getPayStackToken(email, planId, coins);
    }

    private void validatePayStackCard() {

        String cardNumber = mTxtPaymentsCardNumber.getUnMaskedText().trim();
        if (TextUtils.isEmpty(cardNumber)) {
            M.showToast(getActivity(), "Please enter card number");
            mTxtPaymentsCardNumber.requestFocus();
            dismissDialog();
            return;
        }

        mPayStackCard = new co.paystack.android.model.Card.Builder(cardNumber, 0, 0, "").build();

        CharSequence monthAndYear = mTxtPaymentsCardExpiry.getText();

        if (TextUtils.isEmpty(monthAndYear)) {
            M.showToast(getActivity(), "Please enter card expiry month and year");
            mTxtPaymentsCardExpiry.requestFocus();
            dismissDialog();
            return;
        }
        int month = -1;
        try {
            month = Integer.parseInt(monthAndYear.toString().split("/")[0]);
        } catch (Exception e) {
            //ignore
        }

        if (month < 0) {
            M.showToast(getActivity(), "Please enter a valid month");
            mTxtPaymentsCardExpiry.requestFocus();
            dismissDialog();
            return;
        }

        mPayStackCard.setExpiryMonth(month);

        if (TextUtils.isEmpty(monthAndYear)) {
            M.showToast(getActivity(), "Please enter a valid year");
            mTxtPaymentsCardExpiry.requestFocus();
            dismissDialog();
            return;
        }

        int year = -1;
        try {
            year = Integer.parseInt(monthAndYear.toString().split("/")[1]);
        } catch (Exception e) {
            //ignore
        }

        if (year < 0) {
            M.showToast(getActivity(), "Please enter a valid year");
            mTxtPaymentsCardExpiry.requestFocus();
            dismissDialog();
            return;
        }

        mPayStackCard.setExpiryYear(year);

        boolean isValidExpiry = mPayStackCard.validExpiryDate();

        if (!isValidExpiry) {
            M.showToast(getActivity(), "Invalid expiry");
            mTxtPaymentsCardExpiry.requestFocus();
            dismissDialog();
        }

    }

    private void chargePayStackCard(String token) {
        Charge mPayStackCharge = new Charge();
        mPayStackCharge.setCard(mPayStackCard);
        mPayStackCharge.setAccessCode(token);
        PaystackSdk.chargeCard(getActivity(), mPayStackCharge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                //save and send data to server
                mPayStackTransaction = transaction;
                //verify at server
                verifyPayStackTransactionAtServer(mPayStackTransaction.getReference());
            }

            @Override
            public void beforeValidate(Transaction transaction) {
                mPayStackTransaction = transaction;
                //save and send to server
                gotOtp = true;
            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                //retry
                mPayStackTransaction = transaction;
                if (error instanceof ExpiredAccessCodeException) {
                    if (mViewModel != null && mViewModel.getRetryAttempts() < 5) {
                        mViewModel.setRetryAttempts(mViewModel.getRetryAttempts() + 1);
                        getPayStackToken();
                    }
                } else if (gotOtp) {
                    //verify at server
                    verifyPayStackTransactionAtServer(mPayStackTransaction.getReference());
                } else {
                    if (mViewModel != null && mViewModel.getRetryAttempts() < 5) {
                        mViewModel.setRetryAttempts(mViewModel.getRetryAttempts() + 1);
                        getPayStackToken();
                    }
                }
            }
        });
    }

    private void verifyPayStackTransactionAtServer(String transactionReference) {
        String planId = mData.getString(PaymentActivity.PLAIN_ID, "");
        if (mCallingFor == PaymentActivity.PAYMENT_TYPE_PLAN)
            mViewModel.savePayStackData(transactionReference, planId);
        else
            mViewModel.savePaystaackPayment(transactionReference, String.valueOf(mData.getInt(PaymentActivity.NUMBER_OF_COINS)));
        //showHome();
    }

    @Override
    public void makeBankPayment() {
        getPayStackToken();
    }

    @Override
    public void updateStatus(HashMap<String, String> data) {
        data.put("planId", mData.getString(PaymentActivity.PLAIN_ID, ""));
        mViewModel.savePaypalData(data);
    }

    //endregion

}
