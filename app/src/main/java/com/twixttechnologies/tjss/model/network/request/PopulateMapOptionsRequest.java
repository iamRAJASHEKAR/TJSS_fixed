package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.SettingsNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.MapOptionsAndCrime;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 02-11-17.
 */

public class PopulateMapOptionsRequest extends AbstractRequest<MapOptionsAndCrime, SettingsNetworkInterface> {


    public PopulateMapOptionsRequest(Class<SettingsNetworkInterface> networkInterface, RequestCallback<MapOptionsAndCrime> callback) {
        super(networkInterface, callback);
    }

    public void populate(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<MapOptionsAndCrime> call = mNetworkInterface.populateMapOptionsAndCrime(url, headerMap, params);
        call.enqueue(this);
    }


}
