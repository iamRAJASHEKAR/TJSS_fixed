package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.Plan;

import java.util.List;

import retrofit2.Call;

/**
 * @author Sony Raj on 05-08-17.
 */

public class PlansListRequest extends AbstractRequest<List<Plan>,TjssNetworkInterface> {

    public PlansListRequest(Class<TjssNetworkInterface> networkInterface, RequestCallback<List<Plan>> callback) {
        super(networkInterface, callback);
    }

    public void get(String path){
        Call<List<Plan>> call = mNetworkInterface.getPlans(path);
        call.enqueue(this);
    }

}
