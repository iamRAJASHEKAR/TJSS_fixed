package com.twixttechnologies.tjss.model.network.request.serviceinterface;

import com.twixttechnologies.tjss.model.network.response.HelpAlert;
import com.twixttechnologies.tjss.model.network.response.HelpAlertDetail;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleContact;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @author Sony Raj on 22-09-17.
 */

public interface HelpAlertNetworkInterface {

    @POST
    @FormUrlEncoded
    Call<List<SafetyCircleContact>> getSafetyCircleContacts(@Url String path,
                                                            @HeaderMap HashMap<String, String> headerMap,
                                                            @FieldMap HashMap<String, String> params);

    @POST
    @FormUrlEncoded
    Call<StatusMessage> sendHelpAlert(@Url String url,
                                      @HeaderMap HashMap<String, String> headerMap,
                                      @FieldMap HashMap<String, String> params);


    @POST
    @FormUrlEncoded
    Call<List<HelpAlert>> getHelpAlertsHistory(@Url String url,
                                               @HeaderMap HashMap<String, String> headerMap,
                                               @FieldMap HashMap<String, String> params);

    @POST
    @FormUrlEncoded
    Call<HelpAlertDetail> getHelpAlertDetail(@Url String url,
                                             @HeaderMap HashMap<String, String> headerMap,
                                             @FieldMap HashMap<String, String> params);


    @POST
    @FormUrlEncoded
    Call<StatusMessage> updateOrDeleteHelpAlertContact(@Url String url,
                                                       @HeaderMap HashMap<String, String> headerMap,
                                                       @FieldMap HashMap<String, String> params);

}
