package com.twixttechnologies.tjss.model.repository;

import com.twixttechnologies.tjss.model.network.request.IsdCodesRequest;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.IsdCode;

import java.util.List;

/**
 * @author Sony Raj on 04-08-17.
 */

public class IsdRepository extends AbstractRepository<TjssNetworkInterface> {

    public void get(String path , RequestCallback<List<IsdCode>> callback) {
        IsdCodesRequest request = new IsdCodesRequest(TjssNetworkInterface.class, callback);
        request.get(path);
    }
}
