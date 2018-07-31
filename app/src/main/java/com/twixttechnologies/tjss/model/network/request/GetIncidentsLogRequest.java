package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.IncidentsNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.Incident;

import retrofit2.Call;

/**
 * @author Sony Raj on 22-08-17.
 */

public class GetIncidentsLogRequest extends AbstractRequest<Incident[], IncidentsNetworkInterface> {

    public GetIncidentsLogRequest(Class<IncidentsNetworkInterface> networkInterface, RequestCallback<Incident[]> callback) {
        super(networkInterface, callback);
    }

    public void get(String path, String authToken, String userId) {
        Call<Incident[]> call = mNetworkInterface.getLog(path, authToken, userId, userId);
        call.enqueue(this);
    }

}
