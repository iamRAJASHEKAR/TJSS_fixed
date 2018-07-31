package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.SecurityQuestionsNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.SelectedSecurityQuestionResponse;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 31-10-17.
 */

public class GetSelectedSecurityQuestionsRequest extends AbstractRequest<SelectedSecurityQuestionResponse, SecurityQuestionsNetworkInterface> {


    public GetSelectedSecurityQuestionsRequest(Class<SecurityQuestionsNetworkInterface> networkInterface, RequestCallback<SelectedSecurityQuestionResponse> callback) {
        super(networkInterface, callback);
    }

    public void get(String path, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<SelectedSecurityQuestionResponse> call = mNetworkInterface.getSeleted(path, headerMap, params);
        call.enqueue(this);
    }


}
