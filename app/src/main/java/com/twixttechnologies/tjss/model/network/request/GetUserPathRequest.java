package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.UserProfileNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.PathInfo;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * @author Sony Raj on 09-10-17.
 */

public class GetUserPathRequest extends AbstractRequest<List<PathInfo>, UserProfileNetworkInterface> {

    public GetUserPathRequest(Class<UserProfileNetworkInterface> networkInterface, RequestCallback<List<PathInfo>> callback) {
        super(networkInterface, callback);
    }

    public void get(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<List<PathInfo>> call = mNetworkInterface.getPath(url, headerMap, params);
        call.enqueue(this);
    }

}
