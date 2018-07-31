package com.innoviussoftwaresolution.tjss.model.network.request;


import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.GroupDetailsNetworkInterface;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * @author Sony Raj on 28-10-17.
 */

public class ListGroupMembersRequest extends AbstractRequest<List<GroupMember>, GroupDetailsNetworkInterface> {

    public ListGroupMembersRequest(Class<GroupDetailsNetworkInterface> networkInterface, RequestCallback<List<GroupMember>> callback) {
        super(networkInterface, callback);
    }

    public void list(String path, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<List<GroupMember>> call = mNetworkInterface.getGroupMembers(path, headerMap, params);
        call.enqueue(this);
    }

}
