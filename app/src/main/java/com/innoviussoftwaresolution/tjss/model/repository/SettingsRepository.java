package com.innoviussoftwaresolution.tjss.model.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.innoviussoftwaresolution.tjss.model.network.request.PopulateCircleAlertsDataRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.PopulateGeneralAlertRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.PopulateMapOptionsRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.request.UpdateSettingsRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.SettingsNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.CircleAlertsData;
import com.innoviussoftwaresolution.tjss.model.network.response.GeneralAlerts;
import com.innoviussoftwaresolution.tjss.model.network.response.MapOptionsAndCrime;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sony Raj on 24-08-17.
 */

public class SettingsRepository extends AbstractRepository<SettingsNetworkInterface> {

    public void updateSettings(@NonNull String url,
                               @NonNull Map<String, String> headers,
                               @NonNull Map<String, String> params,
                               @Nullable RequestCallback<StatusMessage> callback) {
        new UpdateSettingsRequest(SettingsNetworkInterface.class, callback)
                .update(url, headers, params);
    }

    public void populate(String path, HashMap<String, String> headerMap, HashMap<String, String> params,
                         RequestCallback<MapOptionsAndCrime> callback) {
        new PopulateMapOptionsRequest(SettingsNetworkInterface.class, callback)
                .populate(path, headerMap, params);
    }


    public void populateCircleAlerts(String path, HashMap<String, String> headerMap, HashMap<String, String> params,
                                     RequestCallback<CircleAlertsData> callback) {
        new PopulateCircleAlertsDataRequest(SettingsNetworkInterface.class, callback)
                .populate(path, headerMap, params);
    }

    public void populateGeneralAlerts(String path, HashMap<String, String> headerMap, HashMap<String, String> params,
                                      RequestCallback<GeneralAlerts> callback) {
        new PopulateGeneralAlertRequest(SettingsNetworkInterface.class, callback)
                .populate(path, headerMap, params);
    }



}
