package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.SignUpAndLoginResponse;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * @author Sony Raj on 07-08-17.
 */

public class SignUpWithInviteCodeRequest extends AbstractRequest<SignUpAndLoginResponse,
        TjssNetworkInterface> {


    public SignUpWithInviteCodeRequest(Class<TjssNetworkInterface> networkInterface,
                                       RequestCallback<SignUpAndLoginResponse> callback) {
        super(networkInterface, callback);
    }

    public void signIn(String url, HashMap<String, RequestBody> params, MultipartBody.Part image){
        Call<SignUpAndLoginResponse> call = mNetworkInterface
                .signInWithInviteCode(url, params, image);
        call.enqueue(this);
    }

}
