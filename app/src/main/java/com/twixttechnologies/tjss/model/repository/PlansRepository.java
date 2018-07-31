package com.twixttechnologies.tjss.model.repository;

import com.twixttechnologies.tjss.model.network.request.PlanDetailsRequest;
import com.twixttechnologies.tjss.model.network.request.PlansListRequest;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.Plan;
import com.twixttechnologies.tjss.model.network.response.PlanDetails;

import java.util.List;

/**
 * @author Sony Raj on 05-08-17.
 */

public class PlansRepository extends AbstractRepository<TjssNetworkInterface> {

    public void get(String path , RequestCallback<List<Plan>> callback) {
        PlansListRequest request = new PlansListRequest(TjssNetworkInterface.class, callback);
        request.get(path);
    }

    public void getPlanDetails(String url, String planId, RequestCallback<PlanDetails> callback) {
        new PlanDetailsRequest(TjssNetworkInterface.class, callback)
                .get(url, planId);
    }

}
