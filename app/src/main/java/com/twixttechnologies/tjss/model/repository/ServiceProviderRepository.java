package com.twixttechnologies.tjss.model.repository;

import com.twixttechnologies.tjss.model.network.request.GetAppServiceProvidersRequest;
import com.twixttechnologies.tjss.model.network.request.GetServiceProviderCategoriesRequest;
import com.twixttechnologies.tjss.model.network.request.GetServiceProviderSubCategoriesRequest;
import com.twixttechnologies.tjss.model.network.request.GetServiceProvidersRequest;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.request.SaveOrDeleteSelectedServiceProvidersRequest;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.ServiceProvidersNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.AppServiceProvider;
import com.twixttechnologies.tjss.model.network.response.ServiceProvider;
import com.twixttechnologies.tjss.model.network.response.ServiceProviderCategory;
import com.twixttechnologies.tjss.model.network.response.ServiceProviderSubCategory;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 21-10-17.
 */

public class ServiceProviderRepository extends AbstractRepository<ServiceProvidersNetworkInterface> {

    public void getCategories(String path, HashMap<String, String> headerMap,
                              HashMap<String, String> params,
                              RequestCallback<ArrayList<ServiceProviderCategory>> callback) {
        new GetServiceProviderCategoriesRequest(ServiceProvidersNetworkInterface.class, callback)
                .get(path, headerMap, params);

    }

    public void getSubCategories(String path, HashMap<String, String> headerMap,
                                 HashMap<String, String> params,
                                 RequestCallback<ArrayList<ServiceProviderSubCategory>> callback) {
        new GetServiceProviderSubCategoriesRequest(ServiceProvidersNetworkInterface.class, callback)
                .get(path, headerMap, params);
    }


    public void getServiceProviders(String path, HashMap<String, String> headerMap, HashMap<String, String> params,
                                    RequestCallback<List<ServiceProvider>> callback) {
        new GetServiceProvidersRequest(ServiceProvidersNetworkInterface.class, callback)
                .get(path, headerMap, params);

    }


    public void getAppServiceProviders(String path, HashMap<String, String> headerMap, HashMap<String, String> params,
                                       RequestCallback<List<AppServiceProvider>> callback) {
        new GetAppServiceProvidersRequest(ServiceProvidersNetworkInterface.class, callback)
                .get(path, headerMap, params);

    }

    public void saveSelectedServiceProviders(String path, HashMap<String, String> headerMap,
                                             HashMap<String, String> params,
                                             RequestCallback<StatusMessage> callback) {
        new SaveOrDeleteSelectedServiceProvidersRequest(ServiceProvidersNetworkInterface.class, callback)
                .save(path, headerMap, params);
    }


}
