package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.IsdCode;

import java.util.List;

import retrofit2.Call;

/**
 * @author Sony Raj on 04-08-17.
 */

public class IsdCodesRequest extends AbstractRequest<List<IsdCode>, TjssNetworkInterface> {


    public IsdCodesRequest(Class<TjssNetworkInterface> networkInterface, RequestCallback<List<IsdCode>> callback) {
        super(networkInterface, callback);
    }

    public void get(String path) {
        mNetworkInterface.getIsdCodes(path);
        Call<List<IsdCode>> call = mNetworkInterface.getIsdCodes(path);
        call.enqueue(this);
    }

}
