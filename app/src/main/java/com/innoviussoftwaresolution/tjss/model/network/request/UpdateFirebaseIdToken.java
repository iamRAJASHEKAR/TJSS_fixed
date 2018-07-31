package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.TjssNetworkInterface;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 25-10-17.
 */

public class UpdateFirebaseIdToken extends AbstractRequest<Void, TjssNetworkInterface> {

    public UpdateFirebaseIdToken(Class<TjssNetworkInterface> networkInterface, RequestCallback<Void> callback) {
        super(networkInterface, callback);
    }

    public void update(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<Void> call = mNetworkInterface.updateFirebaseToken(url, headerMap, params);
        call.enqueue(this);
    }

}
