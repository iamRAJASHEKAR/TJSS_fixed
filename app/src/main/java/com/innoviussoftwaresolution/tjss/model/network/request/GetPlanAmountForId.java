package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.PaymentsNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.PlanAmount;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 12-10-17.
 */

public class GetPlanAmountForId extends AbstractRequest<PlanAmount, PaymentsNetworkInterface> {

    public GetPlanAmountForId(Class<PaymentsNetworkInterface> networkInterface, RequestCallback<PlanAmount> callback) {
        super(networkInterface, callback);
    }

    public void get(String path, HashMap<String, String> headerMap, String planId) {
        Call<PlanAmount> call = mNetworkInterface.getPlanAmount(path, headerMap, planId);
        call.enqueue(this);
    }

}
