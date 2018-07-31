package com.innoviussoftwaresolution.tjss.model.repository;

import com.innoviussoftwaresolution.tjss.model.network.request.GetAppServiceProvidersRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.request.SaveOrDeleteSelectedServiceProvidersRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.ServiceProvidersNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.AppServiceProvider;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 25-09-17.
 */

public class AppServiceProvidersRepository extends AbstractRepository<ServiceProvidersNetworkInterface> {

    public void get(String path, HashMap<String, String> headerMap, HashMap<String, String> params,
                    RequestCallback<List<AppServiceProvider>> callback) {
        new GetAppServiceProvidersRequest(ServiceProvidersNetworkInterface.class, callback)
                .get(path, headerMap, params);
    }

    public void delete(String path, HashMap<String,String> headerMap, HashMap<String,String> params,
                       RequestCallback<StatusMessage> callback){
        new SaveOrDeleteSelectedServiceProvidersRequest(ServiceProvidersNetworkInterface.class,callback)
                .save(path,headerMap,params);
    }


}
