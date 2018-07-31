package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.SafetyCirclesNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import retrofit2.Call;

/**
 * @author Sony Raj on 09-08-17.
 */

public class JoinCircleRequest extends AbstractRequest<StatusMessage, SafetyCirclesNetworkInterface> {


    public JoinCircleRequest(Class<SafetyCirclesNetworkInterface> networkInterface, RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }


    public void join(String url, String authToken, String userId, String joinCode) {
        Call<StatusMessage> call = mNetworkInterface.joinCircle(url, authToken, userId, userId, joinCode);
        call.enqueue(this);
    }

}
