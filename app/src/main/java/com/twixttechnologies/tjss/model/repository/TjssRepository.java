package com.twixttechnologies.tjss.model.repository;

import com.twixttechnologies.tjss.model.network.request.AddContactRequest;
import com.twixttechnologies.tjss.model.network.request.GetCheckInHistoryRequest;
import com.twixttechnologies.tjss.model.network.request.GetFaqsRequest;
import com.twixttechnologies.tjss.model.network.request.GetUnreadMessagesCount;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.request.UpdateBatteryRequest;
import com.twixttechnologies.tjss.model.network.request.UpdateLocationRequest;
import com.twixttechnologies.tjss.model.network.request.ValidateUserRequest;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.LocationNetworkInterface;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.CheckInHistory;
import com.twixttechnologies.tjss.model.network.response.Faq;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.model.network.response.UnreadMessage;
import com.twixttechnologies.tjss.model.network.response.UserValidationResponse;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 13-09-17.
 */

public class TjssRepository extends AbstractRepository<TjssNetworkInterface> {


    public void getFaqs(String path, RequestCallback<List<Faq>> callback) {
        new GetFaqsRequest(TjssNetworkInterface.class, callback).get(path);
    }


    public void addContact(String path, HashMap<String, String> headerMap,
                           HashMap<String, String> params,
                           RequestCallback<StatusMessage> callback) {
        new AddContactRequest(TjssNetworkInterface.class, callback)
                .add(path, headerMap, params);
    }

    public void validateUser(String path, HashMap<String, String> headerMap,
                             HashMap<String, String> params,
                             RequestCallback<UserValidationResponse> callback) {
        new ValidateUserRequest(TjssNetworkInterface.class, callback)
                .validate(path, headerMap, params);

    }

    public void updateBattery(String url, HashMap<String, String> headerMap,
                              HashMap<String, String> params) {

        new UpdateBatteryRequest(TjssNetworkInterface.class, null)
                .update(url, headerMap, params);

    }

    public void updateLocation(String url, String authToken, String userId, double lat, double lng,
                               RequestCallback<StatusMessage> callback) {
        new UpdateLocationRequest(LocationNetworkInterface.class, callback)
                .update(url, userId, authToken, lat, lng, 1);
    }

    public void getCheckInChistory(String url, HashMap<String, String> headerMap, HashMap<String, String> params,
                                   RequestCallback<List<CheckInHistory>> callback) {
        new GetCheckInHistoryRequest(TjssNetworkInterface.class, callback)
                .get(url, headerMap, params);
    }

    public void getUnreadMessages(String path, HashMap<String, String> headerMap,
                                  HashMap<String, String> params,
                                  RequestCallback<UnreadMessage> callback) {
        new GetUnreadMessagesCount(TjssNetworkInterface.class, callback)
                .get(path, headerMap, params);
    }


}
