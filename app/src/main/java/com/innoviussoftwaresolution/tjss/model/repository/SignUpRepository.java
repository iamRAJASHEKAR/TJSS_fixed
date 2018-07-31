package com.innoviussoftwaresolution.tjss.model.repository;

import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.request.SignUpWithInviteCodeRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.VerifyInviteCodeRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.helper.RequestHelper;
import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.InviteCodeVerifyResponse;
import com.innoviussoftwaresolution.tjss.model.network.response.SignUpAndLoginResponse;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
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

        params.put("fname",RequestBody.create(MediaType.parse("text/plain"),firstName));
        params.put("email",RequestBody.create(MediaType.parse("text/plain"),email));
        params.put("userPhone",RequestBody.create(MediaType.parse("text/plain"),phone));
        params.put("isdcode",RequestBody.create(MediaType.parse("text/plain"),isdCode));
        params.put("password",RequestBody.create(MediaType.parse("text/plain"),password));
        params.put("userslist",RequestBody.create(MediaType.parse("text/plain"),userList));
        params.put("circle",RequestBody.create(MediaType.parse("text/plain"),circleId));
        params.put("deviceid",RequestBody.create(MediaType.parse("text/plain"),deviceId));



//        HashMap<String ,RequestBody> params =new HashMap<>();
//
//        params.put("fname",RequestHelper.getRequestBody(firstName));

        SignUpWithInviteCodeRequest request = new SignUpWithInviteCodeRequest(
                TjssNetworkInterface.class,callback);

        request.signIn(url,params, RequestHelper.getMultipartBodyPart("profileimage",image));
    }

}
