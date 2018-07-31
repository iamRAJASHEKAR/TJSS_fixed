package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.PayStackSaveData;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.PayStackTokenData;
import com.innoviussoftwaresolution.tjss.model.network.response.PaymentResponse;
import com.innoviussoftwaresolution.tjss.model.network.response.PlanAmount;
import com.innoviussoftwaresolution.tjss.model.repository.PaymentsRepository;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;

import java.util.HashMap;

import co.paystack.android.PaystackSdk;


/**
 * @author Sony Raj on 20-09-17.
 */
public class PaymentsViewModel extends PaymentsViewModelBase {

    private final PaymentsRepository mRepository;
    private final MutableLiveData<PaymentResponse> mPaymentsResponseData
            = new MutableLiveData<>();
    private final MutableLiveData<PayStackTokenData> mPayStackTokenData
            = new MutableLiveData<>();
    private final MutableLiveData<PayStackSaveData> mPayStackSaveData
            = new MutableLiveData<>();
    private final MutableLiveData<PlanAmount> mPlanAmountData
            = new MutableLiveData<>();
    private final RequestCallback<PayStackTokenData> mPayStackTokenDataRequestCallback
            = new RequestCallback<PayStackTokenData>() {
        @Override
        public void onSuccess(PayStackTokenData result) {
            mPayStackTokenData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };
    private final RequestCallback<PayStackSaveData> mPayStackSaveDataCallback
            = new RequestCallback<PayStackSaveData>() {
        @Override
        public void onSuccess(PayStackSaveData result) {
            mPayStackSaveData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };
    private final RequestCallback<PlanAmount> mPlanAmountRequestCallback
            = new RequestCallback<PlanAmount>() {
        @Override
        public void onSuccess(PlanAmount result) {
            mPlanAmountData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };
    private int mRetryAttempts = 0;
    private RequestCallback<PaymentResponse> mResponseCallback
            = new RequestCallback<PaymentResponse>() {
        @Override
        public void onSuccess(PaymentResponse result) {
            mPaymentsResponseData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };


    private PaymentsViewModel(Application application) {
        super(application);
        mRepository = new PaymentsRepository();
        PaystackSdk.initialize(application.getApplicationContext());
        String payStackPublicKey = "pk_test_9520cd2c0f9f01af4f67a00e4e880f80f48f3081";
        PaystackSdk.setPublicKey(payStackPublicKey);
    }

    public MutableLiveData<PaymentResponse> getPaymentsResponseData() {
        return mPaymentsResponseData;
    }

    public MutableLiveData<PayStackTokenData> getmPayStackTokenData() {
        return mPayStackTokenData;
    }

    public MutableLiveData<PayStackSaveData> getPayStackSaveData() {
        return mPayStackSaveData;
    }

    public MutableLiveData<PlanAmount> getPlanAmountData() {
        return mPlanAmountData;
    }


    public int getRetryAttempts() {
        return mRetryAttempts;
    }

    public void setRetryAttempts(int mRetryAttempts) {
        this.mRetryAttempts = mRetryAttempts;
    }

    public void purchasePlan(String planId, String phoneNumber, String stripePlan, String stripeToken) {
        String url = getApplication().getString(R.string.send_token_path_for_plan);
        String userId = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_USER_ID, "");
        String authToken = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
        mRepository.purchasePlan(url, authToken, userId, planId, phoneNumber, stripePlan, stripeToken, mResponseCallback);
    }

    public void purchaseCoins(int amount, String token) {
        String url = getApplication().getString(R.string.coin_purchase_path);
        HashMap<String, String> params = new HashMap<>(2);
        params.put("userId", getUserId());
        params.put("amount", String.valueOf(amount));
        params.put("token", token);
        mRepository.purchaseCoin(url, getHeaders(), params, mResponseCallback);
    }

    public void getPayStackToken(String email, String plainId, String amount) {
        String url = getApplication().getString(R.string.pay_stack_token_path);
        HashMap<String, String> params = new HashMap<>();
        params.put("planId", plainId);
        params.put("email", email);
        params.put("amount", amount);

        mRepository.getPayStackToken(url, getHeaders(), params, mPayStackTokenDataRequestCallback);
    }

    public void savePayStackData(String reference, String planId) {
        String path = getApplication().getString(R.string.pay_stack_save_path);
        String circleId = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_PRIMARY_CIRCLE, "");
        HashMap<String, String> params = new HashMap<>(4);
        params.put("reference", reference);
        params.put("userId", getUserId());
        params.put("planId", planId);
        params.put("circleId", circleId);
        mRepository.savePayStackData(path, getHeaders(), params, mPayStackSaveDataCallback);
    }

    public void savePaystaackPayment(String reference, String amount) {
        String path = getApplication().getString(R.string.save_paystack_coin);
        HashMap<String, String> params = new HashMap<>(4);
        params.put("reference", reference);
        params.put("userId", getUserId());
        params.put("amount", amount);
        mRepository.savePayStackData(path, getHeaders(), params, mPayStackSaveDataCallback);
    }


    public void getPlanAmount(String planId) {
        String path = getApplication().getString(R.string.plan_amount_path);
        mRepository.getPlanAmount(path, getHeaders(), planId, mPlanAmountRequestCallback);
    }

    @SuppressWarnings("unchecked")
    public static class PaymentsViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public PaymentsViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new PaymentsViewModel(mApplication);
        }
    }


}