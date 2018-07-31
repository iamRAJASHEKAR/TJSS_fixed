package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.UserProfileNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 03-11-17.
 */

public class UpdateFirstAndLastNameRequest extends AbstractRequest<StatusMessage, UserProfileNetworkInterface> {


    public UpdateFirstAndLastNameRequest(Class<UserProfileNetworkInterface> networkInterface, RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }

    public void update(String url, HashMap<String, String> headerMap, HashMap<String, String> prams) {
        Call<StatusMessage> call = mNetworkInterface.updateFirstAndLastName(url, headerMap, prams);
        call.enqueue(this);
    }


}
