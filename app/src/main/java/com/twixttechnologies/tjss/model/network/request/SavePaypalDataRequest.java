package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.PaymentsNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.PaymentStatus;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 12-10-17.
 */

public class SavePaypalDataRequest extends AbstractRequest<PaymentStatus, PaymentsNetworkInterface> {

    public SavePaypalDataRequest(Class<PaymentsNetworkInterface> networkInterface, RequestCallback<PaymentStatus> callback) {
        super(networkInterface, callback);
    }

    public void save(String path, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<PaymentStatus> call = mNetworkInterface.updatePaypalPayment(path, headerMap, params);
        call.enqueue(this);
    }

}
