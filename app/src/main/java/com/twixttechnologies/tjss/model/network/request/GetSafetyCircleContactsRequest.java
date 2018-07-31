package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.HelpAlertNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleContact;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * @author Sony Raj on 22-09-17.
 */

public class GetSafetyCircleContactsRequest extends AbstractRequest<List<SafetyCircleContact>, HelpAlertNetworkInterface> {

    public GetSafetyCircleContactsRequest(Class<HelpAlertNetworkInterface> networkInterface, RequestCallback<List<SafetyCircleContact>> callback) {
        super(networkInterface, callback);
    }

    public void get(String path, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<List<SafetyCircleContact>> call = mNetworkInterface.getSafetyCircleContacts(path, headerMap, params);
        call.enqueue(this);
    }

}
