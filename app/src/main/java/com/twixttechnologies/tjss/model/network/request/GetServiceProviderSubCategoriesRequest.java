package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.ServiceProvidersNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.ServiceProviderSubCategory;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 21-10-17.
 */

public class GetServiceProviderSubCategoriesRequest extends AbstractRequest<ArrayList<ServiceProviderSubCategory>, ServiceProvidersNetworkInterface> {

    public GetServiceProviderSubCategoriesRequest(Class<ServiceProvidersNetworkInterface> networkInterface, RequestCallback<ArrayList<ServiceProviderSubCategory>> callback) {
        super(networkInterface, callback);
    }

    public void get(String path, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<ArrayList<ServiceProviderSubCategory>> call = mNetworkInterface.getSubCategories(path, headerMap, params);
        call.enqueue(this);
    }

}
