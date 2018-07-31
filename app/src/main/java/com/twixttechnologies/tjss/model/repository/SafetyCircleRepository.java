package com.twixttechnologies.tjss.model.repository;

import com.twixttechnologies.tjss.model.network.request.ChangeSafetyCircleRequest;
import com.twixttechnologies.tjss.model.network.request.CreateSafetyCircleRequest;
import com.twixttechnologies.tjss.model.network.request.DeleteMemberFromCircleRequest;
import com.twixttechnologies.tjss.model.network.request.GetActiveSafetyCircleMembersRequest;
import com.twixttechnologies.tjss.model.network.request.GetInviteCodeRequest;
import com.twixttechnologies.tjss.model.network.request.GetSimpleSafetyCircleRequest;
import com.twixttechnologies.tjss.model.network.request.JoinCircleRequest;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.request.UpdateAdminRequest;
import com.twixttechnologies.tjss.model.network.request.UpdateSafetyCircleRequest;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.SafetyCirclesNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.CircleSwitchResponse;
import com.twixttechnologies.tjss.model.network.response.CreateSafetyCircleResponse;
import com.twixttechnologies.tjss.model.network.response.InviteCode;
import com.twixttechnologies.tjss.model.network.response.SafetyCircle;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.model.network.response.UserIdAndStatus;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Sony Raj on 08-08-17.
 */

public class SafetyCircleRepository extends AbstractRepository<SafetyCirclesNetworkInterface> {

    public void getSimpleSafetyCircles(String path, String authHeader, String userId,
                                       RequestCallback<List<SafetyCircle>> callback) {
        GetSimpleSafetyCircleRequest request = new GetSimpleSafetyCircleRequest(SafetyCirclesNetworkInterface.class,
                callback);
        request.get(path, authHeader, userId);

    }

    public void createSafetyCircle(String url, String authHeader, String userId, String circleName,
                                   boolean isPrimary,
                                   RequestCallback<CreateSafetyCircleResponse> callback) {
        CreateSafetyCircleRequest request
                = new CreateSafetyCircleRequest(SafetyCirclesNetworkInterface.class,
                callback);
        request.create(url, authHeader, userId, circleName, isPrimary);
    }


    public void join(String url, String authHeader, String userId, String joinCode,
                     RequestCallback<StatusMessage> callback) {
        JoinCircleRequest request = new JoinCircleRequest(SafetyCirclesNetworkInterface.class,
                callback);
        request.join(url, authHeader, userId, joinCode);
    }

    public void getCircleMembers(String url, String authToken, String userId, String circleId,
                                 RequestCallback<SafetyCircleMember[]> callback) {
        GetActiveSafetyCircleMembersRequest request
                = new GetActiveSafetyCircleMembersRequest(SafetyCirclesNetworkInterface.class, callback);
        request.get(url, authToken, userId, circleId);
    }

    public void deleteMemberFromCircle(String url, HashMap<String, String> headerMap, HashMap<String, String> params,
                                       RequestCallback<UserIdAndStatus> callback) {
        new DeleteMemberFromCircleRequest(SafetyCirclesNetworkInterface.class, callback)
                .delete(url, headerMap, params);
    }

    public void switchCircle(String url, HashMap<String, String> headerMap,
                             HashMap<String, String> params,
                             RequestCallback<CircleSwitchResponse> callback) {
        new ChangeSafetyCircleRequest(SafetyCirclesNetworkInterface.class, callback)
                .switchCircle(url, headerMap, params);
    }

    public void updateAdminStatus(String url, HashMap<String, String> headerMap,
                                  HashMap<String, String> params,
                                  RequestCallback<StatusMessage> callback) {
        new UpdateAdminRequest(SafetyCirclesNetworkInterface.class, callback)
                .update(url, headerMap, params);
    }

    public void updateSafetyCircle(String url, HashMap<String, String> headerMap,
                                   HashMap<String, RequestBody> params,
                                   MultipartBody.Part image,
                                   RequestCallback<StatusMessage> callback) {
        new UpdateSafetyCircleRequest(SafetyCirclesNetworkInterface.class, callback)
                .update(url, headerMap, params, image);
    }

    public void getInviteCode(String path, HashMap<String, String> headerMap, HashMap<String, String> params,
                              RequestCallback<InviteCode> callback) {
        new GetInviteCodeRequest(SafetyCirclesNetworkInterface.class, callback)
                .get(path, headerMap, params);
    }


}
