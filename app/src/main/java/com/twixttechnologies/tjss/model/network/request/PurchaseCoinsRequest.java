package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.PaymentsNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.PaymentResponse;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 20-09-17.
 */

public class PurchaseCoinsRequest extends AbstractRequest<PaymentResponse, PaymentsNetworkInterface> {

    public PurchaseCoinsRequest(Class<PaymentsNetworkInterface> networkInterface, RequestCallback<PaymentResponse> callback) {
        super(networkInterface, callback);
    }

    public void purchase(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<PaymentResponse> call = mNetworkInterface.purchaseCoins(url, headerMap, params);
        call.enqueue(this);
    }

}
