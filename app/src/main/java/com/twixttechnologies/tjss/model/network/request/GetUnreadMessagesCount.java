package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.UnreadMessage;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 04-11-17.
 */

public class GetUnreadMessagesCount extends AbstractRequest<UnreadMessage, TjssNetworkInterface> {


    public GetUnreadMessagesCount(Class<TjssNetworkInterface> networkInterface, RequestCallback<UnreadMessage> callback) {
        super(networkInterface, callback);
    }


    public void get(String path, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<UnreadMessage> call = mNetworkInterface.getUnreadMessagesCount(path, headerMap, params);
        call.enqueue(this);
    }

}
