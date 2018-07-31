package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.ServiceProvidersNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.AppServiceProvider;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * @author Sony Raj on 25-09-17.
 */

public class GetAppServiceProvidersRequest extends AbstractRequest<List<AppServiceProvider>, ServiceProvidersNetworkInterface> {

    public GetAppServiceProvidersRequest(Class<ServiceProvidersNetworkInterface> networkInterface, RequestCallback<List<AppServiceProvider>> callback) {
        super(networkInterface, callback);
    }

    public void get(String path, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<List<AppServiceProvider>> call = mNetworkInterface.getAppServiceProviders(path, headerMap, params);
        call.enqueue(this);
    }
}
