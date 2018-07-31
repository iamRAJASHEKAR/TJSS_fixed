package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.FavoritePlacesNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.FavoritePlace;

import java.util.List;

import retrofit2.Call;

/**
 * @author Sony Raj on 07-08-17.
 */

public class GetFavoritePlacesRequest extends AbstractRequest<List<FavoritePlace>, FavoritePlacesNetworkInterface> {

    public GetFavoritePlacesRequest(Class<FavoritePlacesNetworkInterface> networkInterface, RequestCallback<List<FavoritePlace>> callback) {
        super(networkInterface, callback);
    }

    public void get(String path, String authHeader, String userId) {
        Call<List<FavoritePlace>> call = mNetworkInterface.getFavoritePlaces(path, authHeader, userId, userId);
        call.enqueue(this);
    }

}
