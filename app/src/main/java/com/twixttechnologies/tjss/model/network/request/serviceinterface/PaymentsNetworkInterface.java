package com.twixttechnologies.tjss.model.network.request.serviceinterface;

import com.twixttechnologies.tjss.model.network.request.PayStackSaveData;
import com.twixttechnologies.tjss.model.network.response.CoinBalance;
import com.twixttechnologies.tjss.model.network.response.InAppPurchaseServiceItem;
import com.twixttechnologies.tjss.model.network.response.PayStackTokenData;
import com.twixttechnologies.tjss.model.network.response.PaymentResponse;
import com.twixttechnologies.tjss.model.network.response.PaymentStatus;
import com.twixttechnologies.tjss.model.network.response.PlanAmount;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @author Sony Raj on 11-09-17.
 */

public interface PaymentsNetworkInterface {


    @POST
    @FormUrlEncoded
    Call<PaymentResponse> purchasePlan(@Url String url,
                                       @Header("api_token") String authToken,
                                       @Header("userid") String userId,
                                       @Field("planId") String planId,
                                       @Field("phoneNumber") String phoneNumber,
                                       @Field("stripePlan") String stripePlan,
                                       @Field("token") String token,
                                       @Field("userId") String userID);


    @POST
    @FormUrlEncoded
    Call<PaymentResponse> purchaseCoins(@Url String url,
                                        @HeaderMap HashMap<String, String> headerMap,
                                        @FieldMap HashMap<String, String> params);


    @POST
    @FormUrlEncoded
    Call<CoinBalance> getCoinBalance(@Url String url,
                                     @HeaderMap HashMap<String, String> headerMap,
                                     @Field("userId") String userId);

    @POST
    @FormUrlEncoded
    Call<List<InAppPurchaseServiceItem>> getServices(@Url String path,
                                                     @HeaderMap HashMap<String, String> headerMap,
                                                     @FieldMap HashMap<String, String> params);


    @POST
    @FormUrlEncoded
    Call<PayStackTokenData> getPayStackToken(@Url String path,
                                             @HeaderMap HashMap<String, String> headerMap,
                                             @FieldMap HashMap<String, String> params);


    @POST
    @FormUrlEncoded
    Call<PayStackSaveData> savePaySatckData(@Url String url,
                                            @HeaderMap HashMap<String, String> headerMap,
                                            @FieldMap HashMap<String, String> params);

    @POST
    @FormUrlEncoded
    Call<PlanAmount> getPlanAmount(@Url String url,
                                   @HeaderMap HashMap<String, String> params,
                                   @Field("planId") String planId);


    @POST
    @FormUrlEncoded
    Call<PaymentStatus> updatePaypalPayment(@Url String url,
                                            @HeaderMap HashMap<String, String> headerMap,
                                            @FieldMap HashMap<String, String> params);

    @POST
    @FormUrlEncoded
    Call<StatusMessage> transferCoins(@Url String url,
                                      @HeaderMap HashMap<String, String> headerMap,
                                      @FieldMap HashMap<String, String> params);


}
