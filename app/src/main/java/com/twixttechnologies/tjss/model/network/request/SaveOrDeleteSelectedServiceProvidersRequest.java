package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.ServiceProvidersNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 23-10-17.
 */

public class SaveOrDeleteSelectedServiceProvidersRequest extends AbstractRequest<StatusMessage, ServiceProvidersNetworkInterface> {

    public SaveOrDeleteSelectedServiceProvidersRequest(Class<ServiceProvidersNetworkInterface> networkInterface, RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }

    public void save(String path, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<StatusMessage> call = mNetworkInterface.saveOrDeleteSelectedServiceProviders(path, headerMap, params);
        call.enqueue(this);
    }

}
