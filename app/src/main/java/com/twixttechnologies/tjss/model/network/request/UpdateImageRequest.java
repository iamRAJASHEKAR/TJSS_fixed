package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.UserProfileNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * @author Sony Raj on 03-11-17.
 */

public class UpdateImageRequest extends AbstractRequest<StatusMessage, UserProfileNetworkInterface> {


    public UpdateImageRequest(Class<UserProfileNetworkInterface> networkInterface, RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }

    public void update(String url, HashMap<String, String> headerMap, HashMap<String, RequestBody> params,
                       MultipartBody.Part image) {
        Call<StatusMessage> call = mNetworkInterface.updateProfileImage(url, headerMap, params, image);
        call.enqueue(this);
    }


}
