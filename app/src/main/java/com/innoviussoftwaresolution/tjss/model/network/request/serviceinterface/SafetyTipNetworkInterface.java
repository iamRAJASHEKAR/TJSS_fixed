package com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface;

import com.innoviussoftwaresolution.tjss.model.network.response.SafetyTip;
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyTipCategory;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @author Sony Raj on 16-09-17.
 */

public interface SafetyTipNetworkInterface {


    @POST
    @FormUrlEncoded
    Call<List<SafetyTip>> getSafetyTips(@Url String path,
                                        @Field("categoryId") String categoryId,
                                        @HeaderMap HashMap<String, String> headers);


    @POST
    @FormUrlEncoded
    Call<List<SafetyTipCategory>> getSafetyTipCategories(@Url String path,
                                                         @HeaderMap HashMap<String, String> headers,
                                                         @FieldMap HashMap<String, String> params);


    @POST
    @FormUrlEncoded
    Call<StatusMessage> updateSafetyTipSelectedCategory(@Url String url,
                                                        @HeaderMap HashMap<String, String> headerMap,
                                                        @Field("userId") String userId,
                                                        @Field("safetipCategories") String categories);


    @POST
    @FormUrlEncoded
    Call<List<SafetyTipCategory>> getSelectedSafetyTipCategories(@Url String path,
                                                                 @HeaderMap HashMap<String, String> headerMap,
                                                                 @FieldMap HashMap<String, String> params);


}
