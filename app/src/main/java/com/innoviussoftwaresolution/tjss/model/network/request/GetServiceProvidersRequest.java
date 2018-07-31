package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.ServiceProvidersNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.ServiceProvider;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * @author Sony Raj on 16-09-17.
 */

public class GetServiceProvidersRequest extends AbstractRequest<List<ServiceProvider>, ServiceProvidersNetworkInterface> {
    public GetServiceProvidersRequest(Class<ServiceProvidersNetworkInterface> networkInterface, RequestCallback<List<ServiceProvider>> callback) {
        super(networkInterface, callback);
    }

    public void get(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<List<ServiceProvider>> call = mNetworkInterface.getServiceProviders(url, headerMap, params);
        call.enqueue(this);
    }

}
