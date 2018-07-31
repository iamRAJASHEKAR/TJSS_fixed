package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 03-11-17.
 */

public class ForgotPasswordRequest extends AbstractRequest<StatusMessage, TjssNetworkInterface> {


    public ForgotPasswordRequest(Class<TjssNetworkInterface> networkInterface, RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }

    public void send(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<StatusMessage> call = mNetworkInterface.forgotPassword(url, headerMap, params);
        call.enqueue(this);
    }

}
