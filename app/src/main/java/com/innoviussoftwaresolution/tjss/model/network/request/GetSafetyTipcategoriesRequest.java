package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.SafetyTipNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyTipCategory;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * @author Sony Raj on 16-09-17.
 */

public class GetSafetyTipcategoriesRequest extends
        AbstractRequest<List<SafetyTipCategory>, SafetyTipNetworkInterface> {


    public GetSafetyTipcategoriesRequest(Class<SafetyTipNetworkInterface> networkInterface,
                                         RequestCallback<List<SafetyTipCategory>> callback) {
        super(networkInterface, callback);
    }

    public void get(String path, HashMap<String, String> headers, HashMap<String, String> params) {
        Call<List<SafetyTipCategory>> call = mNetworkInterface.getSafetyTipCategories(path, headers, params);
        call.enqueue(this);
    }

}
