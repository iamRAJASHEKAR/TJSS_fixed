package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.PaymentsNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.PayStackTokenData;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 10-10-17.
 */

public class GetPayStackToken extends AbstractRequest<PayStackTokenData, PaymentsNetworkInterface> {

    public GetPayStackToken(Class<PaymentsNetworkInterface> networkInterface, RequestCallback<PayStackTokenData> callback) {
        super(networkInterface, callback);
    }

    public void get(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<PayStackTokenData> call = mNetworkInterface.getPayStackToken(url, headerMap, params);
        call.enqueue(this);
    }

}
