package com.twixttechnologies.tjss.model.network.request;

import android.support.annotation.NonNull;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.SettingsNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.Map;

import retrofit2.Call;

/**
 * @author Sony Raj on 24-08-17.
 */

public class UpdateSettingsRequest extends AbstractRequest<StatusMessage, SettingsNetworkInterface> {

    public UpdateSettingsRequest(Class<SettingsNetworkInterface> networkInterface, RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }

    public void update(@NonNull String url,
                       @NonNull Map<String, String> headers,
                       @NonNull Map<String, String> params) {
        Call<StatusMessage> call = mNetworkInterface.updateSettings(url, headers, params);
        call.enqueue(this);
    }

}
