package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.ServiceProvidersNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.ServiceProviderCategory;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 21-10-17.
 */

public class GetServiceProviderCategoriesRequest extends AbstractRequest<ArrayList<ServiceProviderCategory>, ServiceProvidersNetworkInterface> {

    public GetServiceProviderCategoriesRequest(Class<ServiceProvidersNetworkInterface> networkInterface, RequestCallback<ArrayList<ServiceProviderCategory>> callback) {
        super(networkInterface, callback);
    }

    public void get(String path, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<ArrayList<ServiceProviderCategory>> call = mNetworkInterface.getCategories(path, headerMap, params);
        call.enqueue(this);
    }

}
