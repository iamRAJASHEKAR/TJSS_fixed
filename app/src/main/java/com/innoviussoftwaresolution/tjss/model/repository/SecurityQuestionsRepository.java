package com.innoviussoftwaresolution.tjss.model.repository;

import com.innoviussoftwaresolution.tjss.model.network.request.GetSecirityQuestionsListRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.GetSelectedSecurityQuestionsRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.request.UpdateSecurityQuestionsRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.SecurityQuestionsNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.SecurityQuestion;
import com.innoviussoftwaresolution.tjss.model.network.response.SelectedSecurityQuestionResponse;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 31-10-17.
 */

public class SecurityQuestionsRepository extends AbstractRepository<SecurityQuestionsNetworkInterface> {


    public void listSecurityQuestions(String url, RequestCallback<List<SecurityQuestion>> callback) {
        new GetSecirityQuestionsListRequest(SecurityQuestionsNetworkInterface.class, callback)
                .get(url);
    }


    public void update(String path, HashMap<String, String> headerMap, HashMap<String, String> params,
                       RequestCallback<StatusMessage> callback)
    {
        new UpdateSecurityQuestionsRequest(SecurityQuestionsNetworkInterface.class, callback)
                .update(path, headerMap, params);
    }


    public void getSelected(String path, HashMap<String, String> headerMap, HashMap<String, String> params,
                            RequestCallback<SelectedSecurityQuestionResponse> callback) {
        new GetSelectedSecurityQuestionsRequest(SecurityQuestionsNetworkInterface.class, callback)
                .get(path, headerMap, params);
    }

}
