package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.MedicalRecordsNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * @author Sony Raj on 20-10-17.
 */

public class UploadMedicalDocRequest extends AbstractRequest<StatusMessage, MedicalRecordsNetworkInterface> {

    public UploadMedicalDocRequest(Class<MedicalRecordsNetworkInterface> networkInterface, RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }

    public void upload(String path, HashMap<String, String> headerMap,
                       HashMap<String, RequestBody> params,
                       MultipartBody.Part file) {
        Call<StatusMessage> call = mNetworkInterface.upload(path, headerMap, params, file);
        call.enqueue(this);
    }

}
