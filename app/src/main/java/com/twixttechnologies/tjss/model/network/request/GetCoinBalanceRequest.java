package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.PaymentsNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.CoinBalance;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 07-10-17.
 */

public class GetCoinBalanceRequest extends AbstractRequest<CoinBalance, PaymentsNetworkInterface> {

    public GetCoinBalanceRequest(Class<PaymentsNetworkInterface> networkInterface, RequestCallback<CoinBalance> callback) {
        super(networkInterface, callback);
    }

    public void get(String url, HashMap<String, String> headerMap, String userId) {
        Call<CoinBalance> call = mNetworkInterface.getCoinBalance(url, headerMap, userId);
        call.enqueue(this);
    }

}
