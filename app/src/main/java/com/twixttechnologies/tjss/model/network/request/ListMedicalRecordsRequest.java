package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.MedicalRecordsNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.MedicalRecord;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 21-10-17.
 */

public class ListMedicalRecordsRequest extends AbstractRequest<ArrayList<MedicalRecord>, MedicalRecordsNetworkInterface> {

    public ListMedicalRecordsRequest(Class<MedicalRecordsNetworkInterface> networkInterface, RequestCallback<ArrayList<MedicalRecord>> callback) {
        super(networkInterface, callback);
    }

    public void list(String url, HashMap<String, String> headerMap, HashMap<String, String> params) {
        Call<ArrayList<MedicalRecord>> call = mNetworkInterface.getRecords(url, headerMap, params);
        call.enqueue(this);
    }

}
