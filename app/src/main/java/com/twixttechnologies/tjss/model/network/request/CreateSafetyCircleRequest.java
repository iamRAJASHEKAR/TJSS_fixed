package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.SafetyCirclesNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.CreateSafetyCircleResponse;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 09-08-17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class CreateSafetyCircleRequest extends AbstractRequest<CreateSafetyCircleResponse,
        SafetyCirclesNetworkInterface> {


    public CreateSafetyCircleRequest(Class<SafetyCirclesNetworkInterface> networkInterface,
                                     RequestCallback<CreateSafetyCircleResponse> callback) {
        super(networkInterface, callback);
    }

    public void create(String url, String authHeader, String userId, String circleName,
                       boolean isPrimary) {
        HashMap<String, String> params = new HashMap<>(3);
        params.put("userId", userId.toString().trim());
        params.put("name", circleName);
        params.put("primaryCircle", isPrimary ? "1" : "0");

        Call<CreateSafetyCircleResponse> call = mNetworkInterface.createSafetyCircle(url, authHeader,
                userId, params);
        call.enqueue(this);

    }

}
