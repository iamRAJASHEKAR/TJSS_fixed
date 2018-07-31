package com.innoviussoftwaresolution.tjss.model.network.request;


import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.SecurityQuestionsNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.SecurityQuestion;

import java.util.List;

import retrofit2.Call;

/**
 * @author Sony Raj on 31-10-17.
 */

public class GetSecirityQuestionsListRequest extends AbstractRequest<List<SecurityQuestion>, SecurityQuestionsNetworkInterface> {


    public GetSecirityQuestionsListRequest(Class<SecurityQuestionsNetworkInterface> networkInterface, RequestCallback<List<SecurityQuestion>> callback) {
        super(networkInterface, callback);
    }

    public void get(String path) {
        Call<List<SecurityQuestion>> call = mNetworkInterface.getSecurityQuestions(path);
        call.enqueue(this);
    }

}
