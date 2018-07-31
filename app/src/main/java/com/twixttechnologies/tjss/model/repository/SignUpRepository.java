package com.twixttechnologies.tjss.model.repository;

import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.request.SignUpWithInviteCodeRequest;
import com.twixttechnologies.tjss.model.network.request.VerifyInviteCodeRequest;
import com.twixttechnologies.tjss.model.network.request.helper.RequestHelper;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.InviteCodeVerifyResponse;
import com.twixttechnologies.tjss.model.network.response.Log;
import com.twixttechnologies.tjss.model.network.response.SignUpAndLoginResponse;

import java.io.File;
import java.util.HashMap;

import okhttp3.RequestBody;

/**
 * @author Sony Raj on 05-08-17.
 */

public class SignUpRepository extends AbstractRepository<TjssNetworkInterface> {

    public void verify(String path, String inviteCode, RequestCallback<InviteCodeVerifyResponse> callback) {
        VerifyInviteCodeRequest request = new VerifyInviteCodeRequest(TjssNetworkInterface.class, callback);
        request.verify(path, inviteCode);
    }


    public void signUp(String url , String firstName, String email, String phone, String isdCode,
                       String password, String userList, String circleId, String profileImage,
                       String deviceId, RequestCallback<SignUpAndLoginResponse> callback){
        File image = new File(profileImage);
        HashMap<String , RequestBody> params = new HashMap<>();
        params.put("fname",RequestHelper.getRequestBody(firstName));
        params.put("email",RequestHelper.getRequestBody(email));
        params.put("userPhone",RequestHelper.getRequestBody(phone));
        params.put("isdcode",RequestHelper.getRequestBody(isdCode));
        params.put("password",RequestHelper.getRequestBody(password));
        params.put("userslist",RequestHelper.getRequestBody(userList));
        params.put("circle",RequestHelper.getRequestBody(circleId));
        params.put("deviceid",RequestHelper.getRequestBody(deviceId));

        SignUpWithInviteCodeRequest request = new SignUpWithInviteCodeRequest(
                TjssNetworkInterface.class,callback);

        request.signIn(url,params, RequestHelper.getMultipartBodyPart("profileimage",image));
    }

}
