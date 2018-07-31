package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.Faq;

import java.util.List;

import retrofit2.Call;

/**
 * @author Sony Raj on 13-09-17.
 */

public class GetFaqsRequest extends AbstractRequest<List<Faq>, TjssNetworkInterface> {
    public GetFaqsRequest(Class<TjssNetworkInterface> networkInterface, RequestCallback<List<Faq>> callback) {
        super(networkInterface, callback);
    }

    public void get(String path)
    {
        Call<List<Faq>> call = mNetworkInterface.getFaqs(path);
        call.enqueue(this);
    }
}
