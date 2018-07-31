package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.FavoritePlacesNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 30-10-17.
 */

public class EditFavPlaceRequest extends AbstractRequest<StatusMessage, FavoritePlacesNetworkInterface> {

    public EditFavPlaceRequest(Class<FavoritePlacesNetworkInterface> networkInterface, RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }

    public void edit(String path, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<StatusMessage> call = mNetworkInterface.editFavPlace(path, headerMap, params);
        call.enqueue(this);
    }

}
