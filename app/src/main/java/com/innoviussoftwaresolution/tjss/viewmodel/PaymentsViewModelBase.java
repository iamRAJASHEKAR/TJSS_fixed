package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.PaymentStatus;
import com.innoviussoftwaresolution.tjss.model.repository.PaymentsRepository;

import java.util.HashMap;

/**
 * @author Sony Raj on 12-10-17.
 */

abstract class PaymentsViewModelBase extends ViewModelBase {

    private final MutableLiveData<PaymentStatus> mStatusMessageData
            = new MutableLiveData<>();
    private final RequestCallback<PaymentStatus> mStatusMessageCallback
            = new RequestCallback<PaymentStatus>() {
        @Override
        public void onSuccess(PaymentStatus result) {
            mStatusMessageData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };
    PaymentsRepository mRepository;


    PaymentsViewModelBase(Application application) {
        super(application);
        mRepository = new PaymentsRepository();
    }

    public MutableLiveData<PaymentStatus> getStatusMessageData() {
        return mStatusMessageData;
    }

    public void savePaypalData(HashMap<String, String> params) {
        String path = getApplication().getString(R.string.save_pay_pal_subscription_path);
        mRepository.savePaypalData(path, getHeaders(), params, mStatusMessageCallback);
    }

    public void savePaypalPayment(HashMap<String, String> params) {
        String path = getApplication().getString(R.string.save_paypal_purchase_path);
        mRepository.savePaypalData(path, getHeaders(), params, mStatusMessageCallback);
    }

}
