package com.twixttechnologies.tjss.model.network.request.serviceinterface;

import com.twixttechnologies.tjss.model.network.response.StatusMessage;

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
 * @author Sony Raj on 21-08-17.
 */

public interface LocationNetworkInterface {

    @POST
    @FormUrlEncoded
    Call<StatusMessage> updateLocation(@Url String url,
                                       @Header("api_token") String authToken,
                                       @Header("userid") String userId,
                                       @Field("latitude") double latitude,
                                       @Field("longitude") double longitude,
                                       @Field("userId") String userID,
                                       @Field("type") int isIdle);

    @POST
    @FormUrlEncoded
    Call<StatusMessage> updateLocationSharing(@Url String url,
                                              @HeaderMap HashMap<String, String> headerMap,
                                              @FieldMap HashMap<String, String> params);


}
