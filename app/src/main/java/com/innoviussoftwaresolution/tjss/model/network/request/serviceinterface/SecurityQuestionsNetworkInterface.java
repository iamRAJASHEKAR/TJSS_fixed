package com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface;

import com.innoviussoftwaresolution.tjss.model.network.response.SecurityQuestion;
import com.innoviussoftwaresolution.tjss.model.network.response.SelectedSecurityQuestionResponse;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @author Sony Raj on 31-10-17.
 */

public interface SecurityQuestionsNetworkInterface {

    @GET
    Call<List<SecurityQuestion>> getSecurityQuestions(@Url String url);

    @POST
    @FormUrlEncoded
    Call<StatusMessage> updateSecurityQuestion(@Url String path,
                                               @HeaderMap HashMap<String, String> headerMap,
                                               @FieldMap HashMap<String, String> params);


    @POST
    @FormUrlEncoded
    Call<SelectedSecurityQuestionResponse> getSeleted(@Url String url,
                                                      @HeaderMap HashMap<String, String> headerMap,
                                                      @FieldMap HashMap<String, String> params);

}
