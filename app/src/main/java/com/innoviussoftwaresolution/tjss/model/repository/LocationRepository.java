package com.innoviussoftwaresolution.tjss.model.repository;

import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.request.UpdateLocationRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.UpdateLocationSharingRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.LocationNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;

import java.util.HashMap;


/**
 * @author Sony Raj on 21-08-17.
 */

public class LocationRepository extends AbstractRepository<LocationNetworkInterface> {

    public void updateLocation(String url, String authToken, String userId, double latitude,
                               double longitude, int isIdle) {
        new UpdateLocationRequest(LocationNetworkInterface.class, null).update(
                url, userId, authToken, latitude, longitude, isIdle);

    }


    public void updateLocationSharing(String url, HashMap<String, String> headerMap,
                                      HashMap<String, String> params,
                                      RequestCallback<StatusMessage> callback) {
        new UpdateLocationSharingRequest(LocationNetworkInterface.class, callback)
                .update(url, headerMap, params);
    }


}
