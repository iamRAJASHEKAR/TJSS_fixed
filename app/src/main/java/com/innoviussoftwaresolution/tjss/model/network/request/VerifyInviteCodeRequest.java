package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.InviteCodeVerifyResponse;

import retrofit2.Call;

/**
 * @author Sony Raj on 05-08-17.
 */

public class VerifyInviteCodeRequest extends AbstractRequest<InviteCodeVerifyResponse, TjssNetworkInterface> {

    public VerifyInviteCodeRequest(Class<TjssNetworkInterface> networkInterface, RequestCallback<InviteCodeVerifyResponse> callback) {
        super(networkInterface, callback);
    }

    public void verify(String path, String inviteCode) {
        Call<InviteCodeVerifyResponse> call = mNetworkInterface.verifyInviteCode(path, inviteCode);
        call.enqueue(this);
    }

}
