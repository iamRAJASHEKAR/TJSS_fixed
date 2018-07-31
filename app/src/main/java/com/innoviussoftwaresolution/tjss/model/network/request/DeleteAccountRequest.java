package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.DeleteAccountResponse;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 30-10-17.
 */

public class DeleteAccountRequest extends AbstractRequest<DeleteAccountResponse, TjssNetworkInterface> {

    public DeleteAccountRequest(Class<TjssNetworkInterface> networkInterface, RequestCallback<DeleteAccountResponse> callback) {
        super(networkInterface, callback);
    }

    public void delete(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<DeleteAccountResponse> call = mNetworkInterface.deleteAccount(url, headerMap, params);
        call.enqueue(this);
    }

}
