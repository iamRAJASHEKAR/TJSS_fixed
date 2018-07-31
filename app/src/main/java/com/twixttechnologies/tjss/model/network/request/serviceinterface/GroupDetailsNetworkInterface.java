package com.twixttechnologies.tjss.model.network.request.serviceinterface;

import com.twixttechnologies.tjss.model.network.request.GroupMember;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @author Sony Raj on 28-10-17.
 */

public interface GroupDetailsNetworkInterface {

    @POST
    @FormUrlEncoded
    Call<List<GroupMember>> getGroupMembers(@Url String url,
                                            @HeaderMap HashMap<String, String> headerMap,
                                            @FieldMap HashMap<String, String> params);


}
