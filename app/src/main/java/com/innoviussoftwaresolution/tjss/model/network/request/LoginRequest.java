package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.SignUpAndLoginResponse;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 08-08-17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class LoginRequest extends AbstractRequest<SignUpAndLoginResponse, TjssNetworkInterface> {

    public LoginRequest(Class<TjssNetworkInterface> networkInterface, RequestCallback<SignUpAndLoginResponse> callback) {
        super(networkInterface, callback);
    }

    public void login(String url, String userPhone, String password, String firebaseId) {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("userPhone", userPhone.toString().trim());
        params.put("password", password);
        params.put("firebse_id",firebaseId);

        Call<SignUpAndLoginResponse> call = mNetworkInterface.login(url,params);
        call.enqueue(this);
    }
}
