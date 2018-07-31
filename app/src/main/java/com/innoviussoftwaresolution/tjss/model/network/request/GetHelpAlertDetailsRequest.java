package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.HelpAlertNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.HelpAlertDetail;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 02-11-17.
 */

public class GetHelpAlertDetailsRequest extends AbstractRequest<HelpAlertDetail, HelpAlertNetworkInterface> {


    public GetHelpAlertDetailsRequest(Class<HelpAlertNetworkInterface> networkInterface, RequestCallback<HelpAlertDetail> callback) {
        super(networkInterface, callback);
    }


    public void get(String path, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<HelpAlertDetail> call = mNetworkInterface.getHelpAlertDetail(path, headerMap, params);
        call.enqueue(this);
    }


}
