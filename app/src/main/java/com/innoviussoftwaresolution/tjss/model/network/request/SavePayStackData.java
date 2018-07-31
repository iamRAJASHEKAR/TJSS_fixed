package com.innoviussoftwaresolution.tjss.model.network.request;

import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.PaymentsNetworkInterface;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 11-10-17.
 */

public class SavePayStackData extends AbstractRequest<PayStackSaveData, PaymentsNetworkInterface> {

    public SavePayStackData(Class<PaymentsNetworkInterface> networkInterface, RequestCallback<PayStackSaveData> callback) {
        super(networkInterface, callback);
    }

    public void save(String url, HashMap<String, String> header, HashMap<String, String> params) {
        Call<PayStackSaveData> call = mNetworkInterface.savePaySatckData(url, header, params);
        call.enqueue(this);
    }

}
