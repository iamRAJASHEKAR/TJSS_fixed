package com.innoviussoftwaresolution.tjss.model.repository;

import com.innoviussoftwaresolution.tjss.model.network.request.GetNearbyPlaceRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.nearby.NearbySearchResponse;

import java.util.HashMap;

/**
 * @author Sony Raj on 15-09-17.
 */

public class NearbyPlacesRepository extends
        AbstractRepository<TjssNetworkInterface> {


    public void get(String path, HashMap<String, String> queryParams,
                    RequestCallback<NearbySearchResponse> callback) {
        new GetNearbyPlaceRequest(TjssNetworkInterface.class, callback)
                .get(path, queryParams);

    }

}
