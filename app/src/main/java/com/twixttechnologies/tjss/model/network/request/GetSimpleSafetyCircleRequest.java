package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.SafetyCirclesNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.SafetyCircle;

import java.util.List;

import retrofit2.Call;

/**
 * @author Sony Raj on 08-08-17.
 */

public class GetSimpleSafetyCircleRequest extends AbstractRequest<List<SafetyCircle>, SafetyCirclesNetworkInterface> {

    public GetSimpleSafetyCircleRequest(Class<SafetyCirclesNetworkInterface> networkInterface, RequestCallback<List<SafetyCircle>> callback) {
        super(networkInterface, callback);
    }

    public void get(String path, String authHeader, String userId) {
        Call<List<SafetyCircle>> call = mNetworkInterface.getSimpleSafetyCirclesForUser(path,
                authHeader, userId, userId);
        call.enqueue(this);
    }

}
