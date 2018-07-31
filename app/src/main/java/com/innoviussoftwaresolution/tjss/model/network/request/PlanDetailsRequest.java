package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.PlanDetails;

import retrofit2.Call;

/**
 * @author Sony Raj on 05-08-17.
 */

public class PlanDetailsRequest extends AbstractRequest<PlanDetails, TjssNetworkInterface> {

    public PlanDetailsRequest(Class<TjssNetworkInterface> networkInterface, RequestCallback<PlanDetails> callback) {
        super(networkInterface, callback);
    }

    public void get(String path, String planId) {
        Call<PlanDetails> call = mNetworkInterface.getPlanDetails(path, planId);
        call.enqueue(this);
    }

}
