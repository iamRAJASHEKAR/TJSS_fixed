package com.twixttechnologies.tjss.model.network.request;

import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.Log;
import com.twixttechnologies.tjss.model.network.response.SignUpAndLoginResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Sony Raj on 08-08-17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class LoginRequest extends AbstractRequest<SignUpAndLoginResponse, TjssNetworkInterface> {

    public LoginRequest(Class<TjssNetworkInterface> networkInterface, RequestCallback<SignUpAndLoginResponse> callback) {
        super(networkInterface, callback);
    }

    public void login(String url, String userPhone, String password) {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("userPhone", userPhone.toString().trim());
        params.put("password", password);

        Call<SignUpAndLoginResponse> call = mNetworkInterface.login(url,params);
        call.enqueue(this);

    }


}
