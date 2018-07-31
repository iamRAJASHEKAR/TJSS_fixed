package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.UserProfileNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.UserTimeLineItem;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * @author Sony Raj on 07-10-17.
 */

public class GetUserTimeLineRequest extends AbstractRequest<List<UserTimeLineItem>, UserProfileNetworkInterface> {

    public GetUserTimeLineRequest(Class<UserProfileNetworkInterface> networkInterface, RequestCallback<List<UserTimeLineItem>> callback) {
        super(networkInterface, callback);
    }

    public void get(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<List<UserTimeLineItem>> call = mNetworkInterface.getTimeLine(url, headerMap, params);
        call.enqueue(this);
    }

}
