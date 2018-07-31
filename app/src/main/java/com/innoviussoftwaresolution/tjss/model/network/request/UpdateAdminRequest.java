package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.SafetyCirclesNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 23-10-17.
 */

public class UpdateAdminRequest extends AbstractRequest<StatusMessage, SafetyCirclesNetworkInterface> {

    public UpdateAdminRequest(Class<SafetyCirclesNetworkInterface> networkInterface, RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }

    public void update(String path, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<StatusMessage> call = mNetworkInterface.updateAdmin(path, headerMap, params);
        call.enqueue(this);
    }

}
