package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.TokenRefreshResponse;

import retrofit2.Call;

/**
 * @author Sony Raj on 11-08-17.
 */

public class TokenRefreshRequest extends AbstractRequest<TokenRefreshResponse, TjssNetworkInterface> {

    public TokenRefreshRequest(Class<TjssNetworkInterface> networkInterface, RequestCallback<TokenRefreshResponse> callback) {
        super(networkInterface, callback);
    }

    public void refresh(String url, String refreshToken, String deviceId) {
        Call<TokenRefreshResponse> call = mNetworkInterface.refreshToken(url, refreshToken, deviceId);
        call.enqueue(this);
    }

}
