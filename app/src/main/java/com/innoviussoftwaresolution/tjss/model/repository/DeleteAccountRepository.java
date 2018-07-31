package com.innoviussoftwaresolution.tjss.model.repository;

import com.innoviussoftwaresolution.tjss.model.network.request.DeleteAccountRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.DeleteAccountResponse;

import java.util.HashMap;

/**
 * @author Sony Raj on 30-10-17.
 */

public class DeleteAccountRepository extends AbstractRepository<TjssNetworkInterface> {


    public void delete(String url, HashMap<String, String> headerMap, HashMap<String, String> params,
                       RequestCallback<DeleteAccountResponse> callback) {
        new DeleteAccountRequest(TjssNetworkInterface.class, callback)
                .delete(url, headerMap, params);
    }

}
