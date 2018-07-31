package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.SafetyCirclesNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.UserIdAndStatus;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 23-08-17.
 */

public class DeleteMemberFromCircleRequest extends AbstractRequest<UserIdAndStatus, SafetyCirclesNetworkInterface> {

    public DeleteMemberFromCircleRequest(Class<SafetyCirclesNetworkInterface> networkInterface, RequestCallback<UserIdAndStatus> callback) {
        super(networkInterface, callback);
    }

    public void delete(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<UserIdAndStatus> call = mNetworkInterface.deleteCircleMember(url, headerMap, params);
        call.enqueue(this);
    }
}
