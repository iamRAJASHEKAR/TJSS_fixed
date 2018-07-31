package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.SafetyCirclesNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.CircleSwitchResponse;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 19-10-17.
 */

public class ChangeSafetyCircleRequest extends AbstractRequest<CircleSwitchResponse, SafetyCirclesNetworkInterface> {

    public ChangeSafetyCircleRequest(Class<SafetyCirclesNetworkInterface> networkInterface, RequestCallback<CircleSwitchResponse> callback) {
        super(networkInterface, callback);
    }

    public void switchCircle(String url, HashMap<String, String> hashMap, HashMap<String, String> params) {
        Call<CircleSwitchResponse> call = mNetworkInterface.switchSafetyCircle(url, hashMap, params);
        call.enqueue(this);
    }

}
