package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.HelpAlertNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 13/11/17.
 */

public class UpdateOrDeleteHelpAlertContact extends AbstractRequest<StatusMessage, HelpAlertNetworkInterface> {


    public UpdateOrDeleteHelpAlertContact(Class<HelpAlertNetworkInterface> networkInterface, RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }

    public void updateOrDelete(String path, HashMap<String, String> headerMap, HashMap<String, String> params){
        Call<StatusMessage> call = mNetworkInterface.updateOrDeleteHelpAlertContact(path,headerMap,params);
        call.enqueue(this);
    }

}
