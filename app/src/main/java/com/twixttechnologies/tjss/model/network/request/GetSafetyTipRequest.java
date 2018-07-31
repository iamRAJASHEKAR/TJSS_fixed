package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.SafetyTipNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.SafetyTip;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * @author Sony Raj on 16-09-17.
 */

public class GetSafetyTipRequest extends AbstractRequest<List<SafetyTip>, SafetyTipNetworkInterface> {


    public GetSafetyTipRequest(Class<SafetyTipNetworkInterface> networkInterface,
                               RequestCallback<List<SafetyTip>> callback) {
        super(networkInterface, callback);
    }

    public void get(String path, String categoryId, HashMap<String, String> headers) {
        Call<List<SafetyTip>> call = mNetworkInterface.getSafetyTips(path, categoryId, headers);
        call.enqueue(this);
    }

}
