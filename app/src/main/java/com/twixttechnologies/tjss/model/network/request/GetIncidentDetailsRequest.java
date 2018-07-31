package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.IncidentsNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.IncidentDetail;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 31-10-17.
 */

public class GetIncidentDetailsRequest extends AbstractRequest<IncidentDetail, IncidentsNetworkInterface> {


    public GetIncidentDetailsRequest(Class<IncidentsNetworkInterface> networkInterface, RequestCallback<IncidentDetail> callback) {
        super(networkInterface, callback);
    }

    public void get(String path, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<IncidentDetail> call = mNetworkInterface.getIncidentDetail(path, headerMap, params);
        call.enqueue(this);
    }

}
