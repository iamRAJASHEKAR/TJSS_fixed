package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.CoinBalance;
import com.innoviussoftwaresolution.tjss.model.repository.PaymentsRepository;

/**
 * @author Sony Raj on 07-10-17.
 */
public class CoinPurchaseViewModel extends PaymentsViewModelBase {


    private final MutableLiveData<CoinBalance> mCoinBalanceData
            = new MutableLiveData<>();


    private RequestCallback<CoinBalance> mCoinBalanceCallback
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


    //constructor
    private CoinPurchaseViewModel(Application application) {
        super(application);
        mRepository = new PaymentsRepository();
    }

    public MutableLiveData<CoinBalance> getCoinBalanceData() {
        return mCoinBalanceData;
    }


    //region private methods

    public void getCoinBalance() {
        String url = getApplication().getString(R.string.coin_balance_path);
        mRepository.getCoinBalance(url, getHeaders(), getUserId(), mCoinBalanceCallback);
    }

    //endregion

    //region public methods


    //endregion

    @SuppressWarnings("unchecked")
    public static class CoinPurchaseViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public CoinPurchaseViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new CoinPurchaseViewModel(mApplication);
        }
    }


}