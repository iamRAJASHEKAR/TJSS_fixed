package com.twixttechnologies.tjss.model.repository;

import com.twixttechnologies.tjss.model.network.request.GetUserPathRequest;
import com.twixttechnologies.tjss.model.network.request.GetUserTimeLineRequest;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.request.UpdateFirstAndLastNameRequest;
import com.twixttechnologies.tjss.model.network.request.UpdateImageRequest;
import com.twixttechnologies.tjss.model.network.request.UserProfileRequest;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.UserProfileNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.PathInfo;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.model.network.response.UserProfile;
import com.twixttechnologies.tjss.model.network.response.UserTimeLineItem;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Sony Raj on 09-08-17.
 */

public class UserProfileRepository extends AbstractRepository<UserProfileNetworkInterface> {

    public void get(String url, String authToken, String userId, RequestCallback<UserProfile> callback) {
        UserProfileRequest request = new UserProfileRequest(UserProfileNetworkInterface.class, callback);
        request.get(url, authToken, userId);
    }

    public void getTimeLine(String url, HashMap<String, String> headerMap, HashMap<String,
            String> params, RequestCallback<List<UserTimeLineItem>> requestCallback) {
        new GetUserTimeLineRequest(UserProfileNetworkInterface.class, requestCallback)
                .get(url, headerMap, params);
    }

    public void getPath(String url, HashMap<String, String> headerMap, HashMap<String, String> params,
                        RequestCallback<List<PathInfo>> callback) {
        new GetUserPathRequest(UserProfileNetworkInterface.class, callback)
                .get(url, headerMap, params);
    }

    public void updateUserProfileImage(String url, HashMap<String, String> headerMap, HashMap<String, RequestBody> params,
                                       MultipartBody.Part image, RequestCallback<StatusMessage> callback) {
        new UpdateImageRequest(UserProfileNetworkInterface.class, callback)
                .update(url, headerMap, params, image);

    }


    public void updateFirstAndLastName(String url, HashMap<String, String> headerMap, HashMap<String, String> params,
                                       RequestCallback<StatusMessage> callback) {
        new UpdateFirstAndLastNameRequest(UserProfileNetworkInterface.class, callback)
                .update(url, headerMap, params);
    }



}
