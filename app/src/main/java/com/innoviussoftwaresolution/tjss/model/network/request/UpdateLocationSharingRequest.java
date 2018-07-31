package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.LocationNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 20-09-17.
 */

public class UpdateLocationSharingRequest extends AbstractRequest<StatusMessage, LocationNetworkInterface> {

    public UpdateLocationSharingRequest(Class<LocationNetworkInterface> networkInterface,
                                        RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }

    public void update(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<StatusMessage> call = mNetworkInterface.updateLocationSharing(url, headerMap, params);
        call.enqueue(this);
    }

}
