package com.twixttechnologies.tjss.model.repository;

import com.twixttechnologies.tjss.model.network.request.GetHelpAlertDetailsRequest;
import com.twixttechnologies.tjss.model.network.request.GetSafetyCircleContactsRequest;
import com.twixttechnologies.tjss.model.network.request.HelpAlertHistoryRequest;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.request.SendHelpAlertRequest;
import com.twixttechnologies.tjss.model.network.request.UpdateOrDeleteHelpAlertContact;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.HelpAlertNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.HelpAlert;
import com.twixttechnologies.tjss.model.network.response.HelpAlertDetail;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleContact;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 22-09-17.
 */

public class HelpAlertRepository extends AbstractRepository<HelpAlertNetworkInterface> {

    public void getContacts(String path, HashMap<String, String> headerMap, HashMap<String,
            String> params, RequestCallback<List<SafetyCircleContact>> callback) {
        new GetSafetyCircleContactsRequest(HelpAlertNetworkInterface.class, callback)
                .get(path, headerMap, params);
    }

    public void sendHelpAlert(String path, HashMap<String, String> headers,
                              HashMap<String, String> params, RequestCallback<StatusMessage> requestCallback) {
        new SendHelpAlertRequest(HelpAlertNetworkInterface.class, requestCallback)
                .send(path, headers, params);
    }


    public void getHelpAlertHistory(String path, HashMap<String, String> headers,
                                    HashMap<String, String> params, RequestCallback<List<HelpAlert>> requestCallback) {
        new HelpAlertHistoryRequest(HelpAlertNetworkInterface.class, requestCallback)
                .get(path, headers, params);
    }

    public void getHelpAlertDetail(String path, HashMap<String, String> headers,
                                   HashMap<String, String> params, RequestCallback<HelpAlertDetail> requestCallback) {
        new GetHelpAlertDetailsRequest(HelpAlertNetworkInterface.class, requestCallback)
                .get(path, headers, params);
    }

    public void updateOrDelete(String path , HashMap<String,String> headerMap, HashMap<String,String> params,
                       RequestCallback<StatusMessage> callback){
        new UpdateOrDeleteHelpAlertContact(HelpAlertNetworkInterface.class, callback)
                .updateOrDelete(path,headerMap,params);

    }


}
