package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.HelpAlertNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.HelpAlert;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * @author Sony Raj on 02-11-17.
 */

public class HelpAlertHistoryRequest extends AbstractRequest<List<HelpAlert>, HelpAlertNetworkInterface> {


    public HelpAlertHistoryRequest(Class<HelpAlertNetworkInterface> networkInterface, RequestCallback<List<HelpAlert>> callback) {
        super(networkInterface, callback);
    }


    public void get(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<List<HelpAlert>> call = mNetworkInterface.getHelpAlertsHistory(url, headerMap, params);
        call.enqueue(this);
    }


}
