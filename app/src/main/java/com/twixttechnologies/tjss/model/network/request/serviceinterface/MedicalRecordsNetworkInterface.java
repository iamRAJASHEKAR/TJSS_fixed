package com.twixttechnologies.tjss.model.network.request.serviceinterface;

import com.twixttechnologies.tjss.model.network.response.MedicalRecord;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

/**
 * @author Sony Raj on 20-10-17.
 */

public interface MedicalRecordsNetworkInterface {

    @POST
    @Multipart
    Call<StatusMessage> upload(@Url String url,
                               @HeaderMap HashMap<String, String> headerMap,
                               @PartMap HashMap<String, RequestBody> params,
                               @Part MultipartBody.Part file);


    @POST
    @FormUrlEncoded
    Call<ArrayList<MedicalRecord>> getRecords(@Url String url,
                                              @HeaderMap HashMap<String, String> headerMap,
                                              @FieldMap HashMap<String, String> params);

    @POST
    @FormUrlEncoded
    Call<StatusMessage> deleteRecord(@Url String url,
                                     @HeaderMap HashMap<String, String> headerMap,
                                     @FieldMap HashMap<String, String> params);

}
