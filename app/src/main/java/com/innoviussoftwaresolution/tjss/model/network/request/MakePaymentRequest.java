package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.PaymentsNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.PaymentResponse;

import retrofit2.Call;

/**
 * @author Sony Raj on 11-09-17.
 */

public class MakePaymentRequest extends AbstractRequest<PaymentResponse, PaymentsNetworkInterface> {

    public MakePaymentRequest(Class<PaymentsNetworkInterface> networkInterface, RequestCallback<PaymentResponse> callback) {
        super(networkInterface, callback);
    }

    public void makePayment(String url, String authToken, String userId, String planId,
                            String phoneNumber, String stripePlan, String stripeToken) {
        Call<PaymentResponse> call = mNetworkInterface.purchasePlan(url, authToken, userId, planId, phoneNumber,
                stripePlan, stripeToken, userId);
        call.enqueue(this);
    }

}
