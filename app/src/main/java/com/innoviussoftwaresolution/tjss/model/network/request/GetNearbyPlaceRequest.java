package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.nearby.NearbySearchResponse;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 15-09-17.
 */

public class GetNearbyPlaceRequest extends AbstractRequest<NearbySearchResponse, TjssNetworkInterface> {
    public GetNearbyPlaceRequest(Class<TjssNetworkInterface> networkInterface, RequestCallback<NearbySearchResponse> callback) {
        super(networkInterface, callback);
    }

    public void get(String path, HashMap<String, String> queryParams) {
        Call<NearbySearchResponse> call = mNetworkInterface.getNearbyPlaces(path, queryParams);
        call.enqueue(this);
    }

}
