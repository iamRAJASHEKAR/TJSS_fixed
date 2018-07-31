package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.SafetyCirclesNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;

import retrofit2.Call;

/**
 * @author Sony Raj on 22-08-17.
 */

public class GetActiveSafetyCircleMembersRequest extends AbstractRequest<SafetyCircleMember[],
        SafetyCirclesNetworkInterface> {

    public GetActiveSafetyCircleMembersRequest(Class<SafetyCirclesNetworkInterface> networkInterface,
                                               RequestCallback<SafetyCircleMember[]> callback) {
        super(networkInterface, callback);
    }


    public void get(String path, String authToken, String userId, String circleId) {
        Call<SafetyCircleMember[]> call = mNetworkInterface.getActiveCircleMembers(path,
                authToken, userId, circleId);
        call.enqueue(this);
    }

}
