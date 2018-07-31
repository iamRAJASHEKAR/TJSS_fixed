package com.twixttechnologies.tjss.model.repository;

import com.twixttechnologies.tjss.model.network.request.LoginRequest;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.SignUpAndLoginResponse;

/**
 * @author Sony Raj on 08-08-17.
 */

public class LoginRepository extends AbstractRepository<TjssNetworkInterface> {

    public void login(String url, String userPhone, String password,
                      RequestCallback<SignUpAndLoginResponse> callback){
        LoginRequest loginRequest = new LoginRequest(TjssNetworkInterface.class,callback);
        loginRequest.login(url, userPhone, password);
    }

}
