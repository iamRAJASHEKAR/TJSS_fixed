package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.SettingsNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.GeneralAlerts;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 03-11-17.
 */

public class PopulateGeneralAlertRequest extends AbstractRequest<GeneralAlerts, SettingsNetworkInterface> {


    public PopulateGeneralAlertRequest(Class<SettingsNetworkInterface> networkInterface, RequestCallback<GeneralAlerts> callback) {
        super(networkInterface, callback);
    }

    public void populate(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<GeneralAlerts> call = mNetworkInterface.populateGeneralAlerts(url, headerMap, params);
        call.enqueue(this);
    }

}
