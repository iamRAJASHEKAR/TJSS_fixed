package com.innoviussoftwaresolution.tjss.model.repository;

import com.innoviussoftwaresolution.tjss.model.network.request.GetCoinBalanceRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.GetInAppPurchaseServices;
import com.innoviussoftwaresolution.tjss.model.network.request.GetPayStackToken;
import com.innoviussoftwaresolution.tjss.model.network.request.GetPlanAmountForId;
import com.innoviussoftwaresolution.tjss.model.network.request.MakePaymentRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.PayStackSaveData;
import com.innoviussoftwaresolution.tjss.model.network.request.PurchaseCoinsRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.request.SavePayStackData;
import com.innoviussoftwaresolution.tjss.model.network.request.SavePaypalDataRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.TransferCoinsRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.PaymentsNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.CoinBalance;
import com.innoviussoftwaresolution.tjss.model.network.response.InAppPurchaseServiceItem;
import com.innoviussoftwaresolution.tjss.model.network.response.PayStackTokenData;
import com.innoviussoftwaresolution.tjss.model.network.response.PaymentResponse;
import com.innoviussoftwaresolution.tjss.model.network.response.PaymentStatus;
import com.innoviussoftwaresolution.tjss.model.network.response.PlanAmount;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 20-09-17.
 */

public class PaymentsRepository extends AbstractRepository<PaymentsNetworkInterface> {

    public void purchasePlan(String url, String authToken, String userId, String plainId,
                             String phoneNumber, String stripePlan, String stripeToken,
                             RequestCallback<PaymentResponse> callback) {
        new MakePaymentRequest(PaymentsNetworkInterface.class, callback)
                .makePayment(url, authToken, userId, plainId, phoneNumber, stripePlan, stripeToken);
    }


    public void purchaseCoin(String url, HashMap<String, String> headerMap,
                             HashMap<String, String> params,
                             RequestCallback<PaymentResponse> callback) {
        new PurchaseCoinsRequest(PaymentsNetworkInterface.class, callback)
                .purchase(url, headerMap, params);

    }

    public void getCoinBalance(String url, HashMap<String, String> headerMap, String userId,
                               RequestCallback<CoinBalance> callback) {
        new GetCoinBalanceRequest(PaymentsNetworkInterface.class, callback)
                .get(url, headerMap, userId);
    }

    public void getServices(String path, HashMap<String, String> headerMap, HashMap<String, String> params,
                            RequestCallback<List<InAppPurchaseServiceItem>> callback) {
        new GetInAppPurchaseServices(PaymentsNetworkInterface.class, callback)
                .get(path, headerMap, params);
    }

    public void getPayStackToken(String path, HashMap<String, String> headerMap,
                                 HashMap<String, String> params,
                                 RequestCallback<PayStackTokenData> callback) {
        new GetPayStackToken(PaymentsNetworkInterface.class, callback)
                .get(path, headerMap, params);
    }

    public void savePayStackData(String path, HashMap<String, String> headerMap,
                                 HashMap<String, String> params,
                                 RequestCallback<PayStackSaveData> callback) {
        new SavePayStackData(PaymentsNetworkInterface.class, callback)
                .save(path, headerMap, params);
    }

    public void getPlanAmount(String path, HashMap<String, String> headerMap, String planId,
                              RequestCallback<PlanAmount> callback) {
        new GetPlanAmountForId(PaymentsNetworkInterface.class, callback)
                .get(path, headerMap, planId);
    }

    public void savePaypalData(String path, HashMap<String, String> headerMap,
                               HashMap<String, String> params, RequestCallback<PaymentStatus> callback) {
        new SavePaypalDataRequest(PaymentsNetworkInterface.class, callback)
                .save(path, headerMap, params);
    }

    public void transferCoins(String path, HashMap<String, String> headerMap,
                              HashMap<String, String> params, RequestCallback<StatusMessage> callback) {
        new TransferCoinsRequest(PaymentsNetworkInterface.class, callback)
                .transfer(path, headerMap, params);
    }

}
