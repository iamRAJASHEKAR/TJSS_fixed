package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.UserProfileNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.UserProfile;

import retrofit2.Call;

/**
 * @author Sony Raj on 09-08-17.
 */

public class UserProfileRequest extends AbstractRequest<UserProfile, UserProfileNetworkInterface> {

    public UserProfileRequest(Class<UserProfileNetworkInterface> networkInterface,
                              RequestCallback<UserProfile> callback) {
        super(networkInterface, callback);
    }

    public void get(String url, String authHeader, String userId) {
        Call<UserProfile> call = mNetworkInterface.getUserProfile(url, authHeader, userId, userId);
        call.enqueue(this);
    }

}
