package com.twixttechnologies.tjss.model.repository;

import com.twixttechnologies.tjss.model.network.request.DeleteMedicalRecordRequest;
import com.twixttechnologies.tjss.model.network.request.ListMedicalRecordsRequest;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.request.UploadMedicalDocRequest;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.MedicalRecordsNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.MedicalRecord;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Sony Raj on 20-10-17.
 */

public class MedicalRecordsRepository extends AbstractRepository<MedicalRecordsNetworkInterface> {

    public void uploadRecords(String path, HashMap<String, String> headerMap,
                              HashMap<String, RequestBody> params,
                              MultipartBody.Part file, RequestCallback<StatusMessage> callback) {
        new UploadMedicalDocRequest(MedicalRecordsNetworkInterface.class, callback)
                .upload(path, headerMap, params, file);
    }


    public void list(String url, HashMap<String, String> headerMap, HashMap<String, String> params,
                     RequestCallback<ArrayList<MedicalRecord>> callback) {
        new ListMedicalRecordsRequest(MedicalRecordsNetworkInterface.class, callback)
                .list(url, headerMap, params);
    }

    public void delete(String url, HashMap<String, String> headerMap, HashMap<String, String> params,
                       RequestCallback<StatusMessage> callback) {
        new DeleteMedicalRecordRequest(MedicalRecordsNetworkInterface.class, callback)
                .delete(url, headerMap, params);
    }



}
