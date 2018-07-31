package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.CoinBalance;
import com.innoviussoftwaresolution.tjss.model.network.response.InAppPurchaseServiceItem;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;
import com.innoviussoftwaresolution.tjss.model.repository.PaymentsRepository;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 09-10-17.
 */
public class InAppServicesViewModel extends ViewModelBase {

    private final PaymentsRepository mPaymentsRepository = new PaymentsRepository();

    private final MutableLiveData<List<InAppPurchaseServiceItem>> mServicesData
            = new MutableLiveData<>();

    private final MutableLiveData<CoinBalance> mCoinBalanceData
            = new MutableLiveData<>();

    private final MutableLiveData<StatusMessage> mTransferStatusData
            = new MutableLiveData<>();

    private RequestCallback<List<InAppPurchaseServiceItem>> mRequestCallback
            = new RequestCallback<List<InAppPurchaseServiceItem>>() {
        @Override
        public void onSuccess(List<InAppPurchaseServiceItem> result) {
            mServicesData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };


    private RequestCallback<StatusMessage> mTransferStatusCallback
            = new RequestCallback<StatusMessage>() {
        @Override
        public void onSuccess(StatusMessage result) {
            mTransferStatusData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };


    private RequestCallback<CoinBalance> mCoinBalanceRequestCallback
            = new RequestCallback<CoinBalance>() {
        @Override
        public void onSuccess(CoinBalance result) {
            mCoinBalanceData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private InAppServicesViewModel(Application application) {
        super(application);
        getServices();
        getCoinBalance();
    }

    public MutableLiveData<List<InAppPurchaseServiceItem>> getServicesData() {
        return mServicesData;
    }

    public MutableLiveData<CoinBalance> getCoinBalanceData() {
        return mCoinBalanceData;
    }

    public MutableLiveData<StatusMessage> getTransferStatusData() {
        return mTransferStatusData;
    }

    private void getServices() {
        String path = getApplication().getString(R.string.in_app_service_providers_list_path);
        mPaymentsRepository.getServices(path, getHeaders(), getUserIdParamsMap(), mRequestCallback);
    }


    public void getCoinBalance() {
        String url = getApplication().getString(R.string.coin_balance_path);
        mPaymentsRepository.getCoinBalance(url, getHeaders(), getUserId(), mCoinBalanceRequestCallback);
    }


    public void transfer(InAppPurchaseServiceItem item, String coinBalance) {
        String path = getApplication().getString(R.string.transfer_coins_path);
        HashMap<String, String> params = getUserIdParamsMap();
        params.put("providerId", item.providerId);
        params.put("amount", item.points);
        params.put("balance", coinBalance);
        mPaymentsRepository.transferCoins(path, getHeaders(), params, mTransferStatusCallback);
    }


    @SuppressWarnings("unchecked")
    public static class InAppServicesViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public InAppServicesViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new InAppServicesViewModel(mApplication);
        }
    }


}