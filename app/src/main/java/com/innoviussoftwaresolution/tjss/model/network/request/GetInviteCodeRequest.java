package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.SafetyCirclesNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.InviteCode;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 28-10-17.
 */

public class GetInviteCodeRequest extends AbstractRequest<InviteCode, SafetyCirclesNetworkInterface> {

    public GetInviteCodeRequest(Class<SafetyCirclesNetworkInterface> networkInterface, RequestCallback<InviteCode> callback) {
        super(networkInterface, callback);
    }

    public void get(String path, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<InviteCode> call = mNetworkInterface.getInviteCode(path, headerMap, params);
        call.enqueue(this);

    }

}
