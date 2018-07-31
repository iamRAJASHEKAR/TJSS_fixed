package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.ChatFileUploadResponse;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * @author Sony Raj on 26-10-17.
 */

public class UploadChatFileRequest extends AbstractRequest<ChatFileUploadResponse, TjssNetworkInterface> {

    public UploadChatFileRequest(Class<TjssNetworkInterface> networkInterface, RequestCallback<ChatFileUploadResponse> callback) {
        super(networkInterface, callback);
    }

    public void upload(String path, HashMap<String, String> header, HashMap<String, RequestBody> params,
                       MultipartBody.Part file) {
        Call<ChatFileUploadResponse> call = mNetworkInterface.uploadChatFile(path, header, params, file);
        call.enqueue(this);
    }

}
