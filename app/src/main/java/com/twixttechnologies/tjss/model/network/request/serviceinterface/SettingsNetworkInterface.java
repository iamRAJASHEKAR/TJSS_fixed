package com.twixttechnologies.tjss.model.network.request.serviceinterface;

import com.twixttechnologies.tjss.model.network.response.CircleAlertsData;
import com.twixttechnologies.tjss.model.network.response.GeneralAlerts;
import com.twixttechnologies.tjss.model.network.response.MapOptionsAndCrime;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @author Sony Raj on 24-08-17.
 */

public interface SettingsNetworkInterface {

    @POST
    @FormUrlEncoded
    Call<StatusMessage> updateSettings(@Url String path,
                                       @HeaderMap Map<String, String> headers,
                                       @FieldMap Map<String, String> params);


    @POST
    @FormUrlEncoded
    Call<MapOptionsAndCrime> populateMapOptionsAndCrime(@Url String url,
                                                        @HeaderMap HashMap<String, String> headerMap,
                                                        @FieldMap HashMap<String, String> params);


    @POST
    @FormUrlEncoded
    Call<CircleAlertsData> getCircleAlertsData(@Url String url,
                                               @HeaderMap HashMap<String, String> headerMap,
                                               @FieldMap HashMap<String, String> params);

    @POST
    @FormUrlEncoded
    Call<GeneralAlerts> populateGeneralAlerts(@Url String url,
                                              @HeaderMap HashMap<String, String> headerMap,
                                              @FieldMap HashMap<String, String> params);


}
