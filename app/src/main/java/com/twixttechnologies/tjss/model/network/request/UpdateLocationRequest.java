package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.LocationNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import retrofit2.Call;

/**
 * @author Sony Raj on 21-08-17.
 */

public class UpdateLocationRequest extends AbstractRequest<StatusMessage, LocationNetworkInterface> {

    public UpdateLocationRequest(Class<LocationNetworkInterface> networkInterface, RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }

    public void update(String url, String userId, String authToken, double latitude, double longitude, int isIdle) {
        Call<StatusMessage> call = mNetworkInterface.updateLocation(url, authToken, userId, latitude, longitude, userId, isIdle);
        call.enqueue(this);

    }

}
