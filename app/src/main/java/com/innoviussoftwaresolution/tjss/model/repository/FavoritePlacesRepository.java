package com.innoviussoftwaresolution.tjss.model.repository;

import com.innoviussoftwaresolution.tjss.model.network.request.AddFavPlaceRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.DeleteFavPlaceRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.EditFavPlaceRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.GetFavoritePlacesRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.FavoritePlacesNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.AddFavPlaceResponse;
import com.innoviussoftwaresolution.tjss.model.network.response.FavoritePlace;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 07-08-17.
 */

public class FavoritePlacesRepository extends AbstractRepository<FavoritePlacesNetworkInterface> {


    public void getFavoritePlaces(String path, String authHeader, String userId, RequestCallback<List<FavoritePlace>> callback) {
        GetFavoritePlacesRequest request = new GetFavoritePlacesRequest(FavoritePlacesNetworkInterface.class, callback);
        request.get(path, authHeader, userId);
    }

    public void addFavPlace(String path, String authHeader, String userId, String name, String address,
                            double latitude, double longitude, String placeId, int radius, RequestCallback<AddFavPlaceResponse> callback) {
        AddFavPlaceRequest request = new AddFavPlaceRequest(FavoritePlacesNetworkInterface.class,
                callback);
        request.add(path, authHeader, userId, name, address, latitude, longitude, placeId, radius);
    }


    public void edit(String path, HashMap<String, String> headerMap, HashMap<String, String> params,
                     RequestCallback<StatusMessage> callback) {
        new EditFavPlaceRequest(FavoritePlacesNetworkInterface.class, callback)
                .edit(path, headerMap, params);
    }

    public void delete(String path, HashMap<String, String> headerMap, HashMap<String, String> params,
                       RequestCallback<StatusMessage> callback) {
        new DeleteFavPlaceRequest(FavoritePlacesNetworkInterface.class, callback)
                .delete(path, headerMap, params);
    }


}
