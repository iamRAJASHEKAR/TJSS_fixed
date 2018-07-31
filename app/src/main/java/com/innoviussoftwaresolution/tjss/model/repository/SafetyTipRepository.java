package com.innoviussoftwaresolution.tjss.model.repository;

import com.innoviussoftwaresolution.tjss.model.network.request.GetSafetyTipRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.GetSafetyTipcategoriesRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.GetSelectedSafetyTipsCategories;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.request.UpdateSafetyTipSelectedCategoriesRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.SafetyTipNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyTip;
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyTipCategory;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 16-09-17.
 */

public class SafetyTipRepository extends AbstractRepository<SafetyTipNetworkInterface> {


    public void getSafetyTips(String path, String categoryId, HashMap<String, String> headers,
                              RequestCallback<List<SafetyTip>> callback) {
        new GetSafetyTipRequest(SafetyTipNetworkInterface.class, callback)
                .get(path, categoryId, headers);
    }


    public void getSafetyTipCategories(String path, HashMap<String, String> headers,
                                       HashMap<String, String> params,
                                       RequestCallback<List<SafetyTipCategory>> callback) {
        new GetSafetyTipcategoriesRequest(SafetyTipNetworkInterface.class, callback)
                .get(path, headers, params);
    }


    public void updateSelectedCategories(String path, HashMap<String, String> headerMap,
                                         String userId, String selectedCategories,
                                         RequestCallback<StatusMessage> callback) {
        new UpdateSafetyTipSelectedCategoriesRequest(SafetyTipNetworkInterface.class, callback)
                .update(path, headerMap, userId, selectedCategories);
    }

    public void getSelectedSafetyTipCategory(String url, HashMap<String, String> headerMap,
                                             HashMap<String, String> params,
                                             RequestCallback<List<SafetyTipCategory>> callback) {
        new GetSelectedSafetyTipsCategories(SafetyTipNetworkInterface.class, callback)
                .get(url, headerMap, params);
    }


}
