package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.PaymentsNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.InAppPurchaseServiceItem;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * @author Sony Raj on 09-10-17.
 */

public class GetInAppPurchaseServices extends AbstractRequest<List<InAppPurchaseServiceItem>, PaymentsNetworkInterface> {

    public GetInAppPurchaseServices(Class<PaymentsNetworkInterface> networkInterface, RequestCallback<List<InAppPurchaseServiceItem>> callback) {
        super(networkInterface, callback);
    }

    public void get(String path, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<List<InAppPurchaseServiceItem>> call = mNetworkInterface.getServices(path, headerMap, params);
        call.enqueue(this);
    }

}
