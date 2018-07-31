package com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface;

import com.innoviussoftwaresolution.tjss.model.network.response.Incident;
import com.innoviussoftwaresolution.tjss.model.network.response.IncidentDetail;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @author Sony Raj on 22-08-17.
 */

public interface IncidentsNetworkInterface {

    @POST
    @FormUrlEncoded
    Call<Incident[]> getLog(@Url String path,
                            @Header("api_token") String authToken,
                            @Header("userid") String userId,
                            @Field("userId") String userID);


    @POST
    @FormUrlEncoded
    Call<IncidentDetail> getIncidentDetail(@Url String path,
                                           @HeaderMap HashMap<String, String> headerMap,
                                           @FieldMap HashMap<String, String> params);

}
