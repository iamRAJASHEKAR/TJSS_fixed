package com.twixttechnologies.tjss.model.network.request.serviceinterface;

import com.twixttechnologies.tjss.model.network.response.CircleSwitchResponse;
import com.twixttechnologies.tjss.model.network.response.CreateSafetyCircleResponse;
import com.twixttechnologies.tjss.model.network.response.InviteCode;
import com.twixttechnologies.tjss.model.network.response.SafetyCircle;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.model.network.response.UserIdAndStatus;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

/**
 * @author Sony Raj on 08-08-17.
 */

public interface SafetyCirclesNetworkInterface {

    @POST
    @FormUrlEncoded
    Call<List<SafetyCircle>> getSimpleSafetyCirclesForUser(@Url String url,
                                                           @Header("api_token") String token,
                                                           @Header("userid") String userId,
                                                           @Field("userid") String userID);


    @POST
    @FormUrlEncoded
    Call<CreateSafetyCircleResponse> createSafetyCircle(@Url String url,
                                                        @Header("api_token") String token,
                                                        @Header("userid") String userId,
                                                        @FieldMap HashMap<String, String> params);

    @POST
    @FormUrlEncoded
    Call<StatusMessage> joinCircle(@Url String url,
                                   @Header("api_token") String token,
                                   @Header("userid") String userId,
                                   @Field("userId") String userID,
                                   @Field("inviteCode") String inviteCode);


    @POST
    @FormUrlEncoded
    Call<SafetyCircleMember[]> getActiveCircleMembers(@Url String url,
                                                      @Header("api_token") String authToken,
                                                      @Header("userid") String userId,
                                                      @Field("safetyCircle") String circleId);

    @POST
    @FormUrlEncoded
    Call<UserIdAndStatus> deleteCircleMember(@Url String url,
                                             @HeaderMap HashMap<String, String> headerMap,
                                             @FieldMap HashMap<String, String> params);

    @POST
    @FormUrlEncoded
    Call<CircleSwitchResponse> switchSafetyCircle(@Url String url,
                                                  @HeaderMap HashMap<String, String> headerMap,
                                                  @FieldMap HashMap<String, String> params);



    @POST
    @FormUrlEncoded
    Call<StatusMessage> updateAdmin(@Url String url,
                                    @HeaderMap HashMap<String, String> headerMap,
                                    @FieldMap HashMap<String, String> params);


    @POST
    @Multipart
    Call<StatusMessage> update(@Url String url,
                               @HeaderMap HashMap<String, String> headerMap,
                               @PartMap HashMap<String, RequestBody> params,
                               @Part MultipartBody.Part image);

    @POST
    @FormUrlEncoded
    Call<InviteCode> getInviteCode(@Url String path,
                                   @HeaderMap HashMap<String, String> headerMap,
                                   @FieldMap HashMap<String, String> params);


}
