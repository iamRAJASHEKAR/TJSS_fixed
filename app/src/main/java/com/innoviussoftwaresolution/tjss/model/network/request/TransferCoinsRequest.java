package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.PaymentsNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 06-11-17.
 */

public class TransferCoinsRequest extends AbstractRequest<StatusMessage, PaymentsNetworkInterface> {


    public TransferCoinsRequest(Class<PaymentsNetworkInterface> networkInterface, RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }


    public void transfer(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<StatusMessage> call = mNetworkInterface.transferCoins(url, headerMap, params);
        call.enqueue(this);
    }

}
