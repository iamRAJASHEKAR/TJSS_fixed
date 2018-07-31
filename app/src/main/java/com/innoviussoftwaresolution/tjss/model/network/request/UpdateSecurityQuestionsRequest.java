package com.innoviussoftwaresolution.tjss.model.network.request;


import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.SecurityQuestionsNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 31-10-17.
 */

public class UpdateSecurityQuestionsRequest extends AbstractRequest<StatusMessage, SecurityQuestionsNetworkInterface> {


    public UpdateSecurityQuestionsRequest(Class<SecurityQuestionsNetworkInterface> networkInterface, RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }

    public void update(String path, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<StatusMessage> call = mNetworkInterface.updateSecurityQuestion(path, headerMap, params);
        call.enqueue(this);
    }

}
