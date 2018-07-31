package com.twixttechnologies.tjss.model.repository;

import com.twixttechnologies.tjss.model.network.request.GetIncidentDetailsRequest;
import com.twixttechnologies.tjss.model.network.request.GetIncidentsLogRequest;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.IncidentsNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.Incident;
import com.twixttechnologies.tjss.model.network.response.IncidentDetail;

import java.util.HashMap;

/**
 * @author Sony Raj on 22-08-17.
 */

public class IncidentsRepository extends AbstractRepository<IncidentsNetworkInterface> {

    public void getIncidentLog(String path, String authToken, String userId,
                               RequestCallback<Incident[]> callback) {
        new GetIncidentsLogRequest(IncidentsNetworkInterface.class, callback)
                .get(path, authToken, userId);
    }

    public void getIncidentDetail(String path, HashMap<String, String> headerMap,
                                  HashMap<String, String> params,
                                  RequestCallback<IncidentDetail> callback) {
        new GetIncidentDetailsRequest(IncidentsNetworkInterface.class, callback)
                .get(path, headerMap, params);
    }


}
