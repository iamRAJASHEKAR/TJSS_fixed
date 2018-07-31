package com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface;

import com.innoviussoftwaresolution.tjss.model.network.response.PathInfo;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;
import com.innoviussoftwaresolution.tjss.model.network.response.UserProfile;
import com.innoviussoftwaresolution.tjss.model.network.response.UserTimeLineItem;

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
 * @author Sony Raj on 09-08-17.
 */

public interface UserProfileNetworkInterface {

    @POST
    @FormUrlEncoded
    Call<UserProfile> getUserProfile(@Url String url, @Header("api_token") String accessToken,
                                     @Header("userid") String userId, @Field("userid") String userID);


    @POST
    @FormUrlEncoded
    Call<List<UserTimeLineItem>> getTimeLine(@Url String url,
                                             @HeaderMap HashMap<String, String> headerMap,
                                             @FieldMap HashMap<String, String> params);


    @POST
    @FormUrlEncoded
    Call<List<PathInfo>> getPath(@Url String url,
                                 @HeaderMap HashMap<String, String> headerMap,
                                 @FieldMap HashMap<String, String> params);


    @Multipart
    @POST
    Call<StatusMessage> updateProfileImage(@Url String url,
                                           @HeaderMap HashMap<String, String> headerMap,
                                           @PartMap HashMap<String, RequestBody> params,
                                           @Part MultipartBody.Part image);

    @POST
    @FormUrlEncoded
    Call<StatusMessage> updateFirstAndLastName(@Url String url,
                                               @HeaderMap HashMap<String, String> headerMap,
                                               @FieldMap HashMap<String, String> params);

}
