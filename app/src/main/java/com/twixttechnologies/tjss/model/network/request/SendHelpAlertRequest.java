package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.HelpAlertNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 25-09-17.
 */

public class SendHelpAlertRequest extends AbstractRequest<StatusMessage, HelpAlertNetworkInterface> {

    public SendHelpAlertRequest(Class<HelpAlertNetworkInterface> networkInterface, RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }


    public void send(String path, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<StatusMessage> call = mNetworkInterface.sendHelpAlert(path, headerMap, params);
        call.enqueue(this);
    }

}
