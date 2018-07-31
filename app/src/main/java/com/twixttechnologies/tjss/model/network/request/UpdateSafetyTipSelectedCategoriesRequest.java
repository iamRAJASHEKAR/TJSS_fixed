package com.twixttechnologies.tjss.model.network.request;

import com.twixttechnologies.tjss.model.network.request.serviceinterface.SafetyTipNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;

import java.util.HashMap;

import retrofit2.Call;

/**
 * @author Sony Raj on 16-09-17.
 */

public class UpdateSafetyTipSelectedCategoriesRequest extends
        AbstractRequest<StatusMessage, SafetyTipNetworkInterface> {

    public UpdateSafetyTipSelectedCategoriesRequest(Class<SafetyTipNetworkInterface> networkInterface,
                                                    RequestCallback<StatusMessage> callback) {
        super(networkInterface, callback);
    }

    public void update(String path, HashMap<String, String> headerMap, String userId,
                       String selectedCategories) {
        Call<StatusMessage> call = mNetworkInterface
                .updateSafetyTipSelectedCategory(path, headerMap, userId, selectedCategories);
        call.enqueue(this);
    }


}
