package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.FavoritePlacesNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.AddFavPlaceResponse;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 11-08-17.
 */

public class AddFavPlaceRequest extends AbstractRequest<AddFavPlaceResponse, FavoritePlacesNetworkInterface> {

    public AddFavPlaceRequest(Class<FavoritePlacesNetworkInterface> networkInterface, RequestCallback<AddFavPlaceResponse> callback) {
        super(networkInterface, callback);
    }

    public void add(String url, String authHeader,
                    String userId, String name, String address, double latitude,
                    double longitude, String placeId, int radius) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("name", name);
        params.put("address", address);
        params.put("latitude", String.valueOf(latitude));
        params.put("longitude", String.valueOf(longitude));
        params.put("placeId", placeId);
        params.put("radius", String.valueOf(radius));
        Call<AddFavPlaceResponse> call = mNetworkInterface.addFavPlace(url, authHeader, userId, params);
        call.enqueue(this);
    }

}
