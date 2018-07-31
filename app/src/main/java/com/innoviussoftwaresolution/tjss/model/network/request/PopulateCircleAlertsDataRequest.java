package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.SettingsNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.CircleAlertsData;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 02-11-17.
 */

public class PopulateCircleAlertsDataRequest extends AbstractRequest<CircleAlertsData, SettingsNetworkInterface> {


    public PopulateCircleAlertsDataRequest(Class<SettingsNetworkInterface> networkInterface, RequestCallback<CircleAlertsData> callback) {
        super(networkInterface, callback);
    }


    public void populate(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<CircleAlertsData> call = mNetworkInterface.getCircleAlertsData(url, headerMap, params);
        call.enqueue(this);
    }

}
