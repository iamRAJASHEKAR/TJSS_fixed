package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.MedicalRecordsNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 21-10-17.
 */

public class DeleteMedicalRecordRequest extends AbstractRequest<StatusMessage, MedicalRecordsNetworkInterface> {

    public DeleteMedicalRecordRequest(Class<MedicalRecordsNetworkInterface> networkInterface, RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }

    public void delete(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<StatusMessage> call = mNetworkInterface.deleteRecord(url, headerMap, params);
        call.enqueue(this);
    }

}
