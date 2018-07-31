package com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface;

import com.innoviussoftwaresolution.tjss.model.network.response.AddFavPlaceResponse;
import com.innoviussoftwaresolution.tjss.model.network.response.FavoritePlace;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;

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
 * @author Sony Raj on 07-08-17.
 */

public interface FavoritePlacesNetworkInterface {

    @FormUrlEncoded
    @POST
    Call<List<FavoritePlace>> getFavoritePlaces(@Url String url, @Header("api_token") String token,
                                          @Header("userid") String userId, @Field("userid") String userID);


    @FormUrlEncoded
    @POST
    Call<AddFavPlaceResponse> addFavPlace(@Url String url,
                                          @Header("api_token") String token,
                                          @Header("userid") String userId,
                                          @FieldMap HashMap<String, String> params);


    @FormUrlEncoded
    @POST
    Call<StatusMessage> editFavPlace(@Url String url,
                                     @HeaderMap HashMap<String, String> headerMap,
                                     @FieldMap HashMap<String, String> params);


    @FormUrlEncoded
    @POST
    Call<StatusMessage> deleteFavPlace(@Url String url,
                                       @HeaderMap HashMap<String, String> headerMap,
                                       @FieldMap HashMap<String, String> params);


}
