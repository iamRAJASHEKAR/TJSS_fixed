package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 19-10-17.
 */

public class UpdateBatteryRequest extends AbstractRequest<Void, TjssNetworkInterface> {

    public UpdateBatteryRequest(Class<TjssNetworkInterface> networkInterface, RequestCallback<Void> callback) {
        super(networkInterface, callback);
    }

    public void update(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<Void> call = mNetworkInterface.updateBattery(url, headerMap, params);
        call.enqueue(this);
    }

}
