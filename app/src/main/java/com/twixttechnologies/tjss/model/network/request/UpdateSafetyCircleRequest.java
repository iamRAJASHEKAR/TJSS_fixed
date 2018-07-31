package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.SafetyCirclesNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * @author Sony Raj on 23-10-17.
 */

public class UpdateSafetyCircleRequest extends AbstractRequest<StatusMessage, SafetyCirclesNetworkInterface> {

    public UpdateSafetyCircleRequest(Class<SafetyCirclesNetworkInterface> networkInterface, RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }

    public void update(String path, HashMap<String, String> headerMap, HashMap<String, RequestBody> params, MultipartBody.Part image) {
        Call<StatusMessage> call = mNetworkInterface.update(path, headerMap, params, image);
        call.enqueue(this);
    }

}
