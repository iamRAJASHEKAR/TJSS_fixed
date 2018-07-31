package com.twixttechnologies.tjss.model.network.request.serviceinterface;

import com.twixttechnologies.tjss.model.network.response.ChatFileUploadResponse;
import com.twixttechnologies.tjss.model.network.response.CheckInHistory;
import com.twixttechnologies.tjss.model.network.response.DeleteAccountResponse;
import com.twixttechnologies.tjss.model.network.response.Faq;
import com.twixttechnologies.tjss.model.network.response.InviteCodeVerifyResponse;
import com.twixttechnologies.tjss.model.network.response.IsdCode;
import com.twixttechnologies.tjss.model.network.response.Plan;
import com.twixttechnologies.tjss.model.network.response.PlanDetails;
import com.twixttechnologies.tjss.model.network.response.SignUpAndLoginResponse;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.model.network.response.TokenRefreshResponse;
import com.twixttechnologies.tjss.model.network.response.UnreadMessage;
import com.twixttechnologies.tjss.model.network.response.UserValidationResponse;
import com.twixttechnologies.tjss.model.network.response.nearby.NearbySearchResponse;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @author Sony Raj on 04-08-17.
 */

public interface TjssNetworkInterface {


    @GET
    Call<List<IsdCode>> getIsdCodes(@Url String path);

    @GET
    Call<List<Plan>> getPlans(@Url String plans);

    @FormUrlEncoded
    @POST
    Call<PlanDetails> getPlanDetails(@Url String url,
                                     @Field("plan_id") String planId);

    @FormUrlEncoded
    @POST
    Call<InviteCodeVerifyResponse> verifyInviteCode(@Url String path,
                                                    @Field("inviteCode") String inviteCode);

    @Multipart
    @POST
    Call<SignUpAndLoginResponse> signInWithInviteCode(@Url String url,
                                                      @PartMap HashMap<String, RequestBody> params,
                                                      @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST
    Call<SignUpAndLoginResponse> login(@Url String path,
                                       @FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST
    Call<TokenRefreshResponse> refreshToken(@Url String url,
                                            @Field("refreshtoken") String refreshToken,
                                            @Field("deviceid") String deviceId);


    @GET
    Call<List<Faq>> getFaqs(@Url String path);

    @GET
    Call<NearbySearchResponse> getNearbyPlaces(@Url String path,
                                               @QueryMap HashMap<String, String> queries);


    @POST
    @FormUrlEncoded
    Call<StatusMessage> addContact(@Url String path,
                                   @HeaderMap HashMap<String, String> headerMap,
                                   @FieldMap HashMap<String, String> params);


    @POST
    @FormUrlEncoded
    Call<UserValidationResponse> validateUser(@Url String url,
                                              @HeaderMap HashMap<String, String> headerMap,
                                              @FieldMap HashMap<String, String> params);


    @POST
    @FormUrlEncoded
    Call<Void> updateBattery(@Url String url,
                             @HeaderMap HashMap<String, String> headerMap,
                             @FieldMap HashMap<String, String> params);

    @POST
    @FormUrlEncoded
    Call<Void> updateFirebaseToken(@Url String url,
                                   @HeaderMap HashMap<String, String> headerMap,
                                   @FieldMap HashMap<String, String> params);


    @POST
    @Multipart
    Call<ChatFileUploadResponse> uploadChatFile(@Url String url,
                                                @HeaderMap HashMap<String, String> headerMap,
                                                @PartMap HashMap<String, RequestBody> params,
                                                @Part MultipartBody.Part file);


    @POST
    @FormUrlEncoded
    Call<DeleteAccountResponse> deleteAccount(@Url String url,
                                              @HeaderMap HashMap<String, String> headerMap,
                                              @FieldMap HashMap<String, String> params);


    @POST
    @FormUrlEncoded
    Call<List<CheckInHistory>> getCheckInHistory(@Url String url,
                                                 @HeaderMap HashMap<String, String> headerMap,
                                                 @FieldMap HashMap<String, String> params);

    @POST
    @FormUrlEncoded
    Call<StatusMessage> forgotPassword(@Url String url,
                                       @HeaderMap HashMap<String, String> headerMap,
                                       @FieldMap HashMap<String, String> params);

    @POST
    @FormUrlEncoded
    Call<UnreadMessage> getUnreadMessagesCount(@Url String url,
                                               @HeaderMap HashMap<String, String> headerMap,
                                               @FieldMap HashMap<String, String> params);

}
