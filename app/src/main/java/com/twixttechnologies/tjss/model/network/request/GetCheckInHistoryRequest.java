package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.CheckInHistory;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * @author Sony Raj on 31-10-17.
 */

public class GetCheckInHistoryRequest extends AbstractRequest<List<CheckInHistory>, TjssNetworkInterface> {


    public GetCheckInHistoryRequest(Class<TjssNetworkInterface> networkInterface, RequestCallback<List<CheckInHistory>> callback) {
        super(networkInterface, callback);
    }

    public void get(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<List<CheckInHistory>> call = mNetworkInterface.getCheckInHistory(url, headerMap, params);
        call.enqueue(this);
    }


}
