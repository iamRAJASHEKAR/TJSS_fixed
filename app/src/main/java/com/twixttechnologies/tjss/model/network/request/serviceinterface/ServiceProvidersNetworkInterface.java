package com.twixttechnologies.tjss.model.network.request.serviceinterface;

import com.twixttechnologies.tjss.model.network.response.AppServiceProvider;
import com.twixttechnologies.tjss.model.network.response.ServiceProvider;
import com.twixttechnologies.tjss.model.network.response.ServiceProviderCategory;
import com.twixttechnologies.tjss.model.network.response.ServiceProviderSubCategory;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @author Sony Raj on 21-10-17.
 */

public interface ServiceProvidersNetworkInterface {

    @POST
    @FormUrlEncoded
    Call<ArrayList<ServiceProviderCategory>> getCategories(@Url String path,
                                                           @HeaderMap HashMap<String, String> headerMap,
                                                           @FieldMap HashMap<String, String> params);


    @POST
    @FormUrlEncoded
    Call<ArrayList<ServiceProviderSubCategory>> getSubCategories(@Url String path,
                                                                 @HeaderMap HashMap<String, String> headerMap,
                                                                 @FieldMap HashMap<String, String> params);


    @POST
    @FormUrlEncoded
    Call<List<ServiceProvider>> getServiceProviders(@Url String path,
                                                    @HeaderMap HashMap<String, String> headerMap,
                                                    @FieldMap HashMap<String, String> params);


    @POST
    @FormUrlEncoded
    Call<List<AppServiceProvider>> getAppServiceProviders(@Url String url,
                                                          @HeaderMap HashMap<String, String> headerMap,
                                                          @FieldMap HashMap<String, String> params);


    @POST
    @FormUrlEncoded
    Call<StatusMessage> saveOrDeleteSelectedServiceProviders(@Url String path,
                                                             @HeaderMap HashMap<String, String> headerMap,
                                                             @FieldMap HashMap<String, String> params);

}
