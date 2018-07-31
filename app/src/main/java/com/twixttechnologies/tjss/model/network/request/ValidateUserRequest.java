package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.UserValidationResponse;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 16-10-17.
 */

public class ValidateUserRequest extends AbstractRequest<UserValidationResponse, TjssNetworkInterface> {

    public ValidateUserRequest(Class<TjssNetworkInterface> networkInterface, RequestCallback<UserValidationResponse> callback) {
        super(networkInterface, callback);
    }

    public void validate(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<UserValidationResponse> call = mNetworkInterface.validateUser(url, headerMap, params);
        call.enqueue(this);
    }
}
