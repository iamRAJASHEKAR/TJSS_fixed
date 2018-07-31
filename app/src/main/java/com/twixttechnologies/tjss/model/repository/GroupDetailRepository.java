package com.twixttechnologies.tjss.model.repository;

import com.twixttechnologies.tjss.model.network.request.GroupMember;
import com.twixttechnologies.tjss.model.network.request.ListGroupMembersRequest;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.GroupDetailsNetworkInterface;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 28-10-17.
 */

public class GroupDetailRepository extends AbstractRepository<GroupDetailsNetworkInterface> {

    public void listMembers(String url, HashMap<String, String> headerMap,
                            HashMap<String, String> params, RequestCallback<List<GroupMember>> callback) {
        new ListGroupMembersRequest(GroupDetailsNetworkInterface.class, callback)
                .list(url, headerMap, params);
    }

}
