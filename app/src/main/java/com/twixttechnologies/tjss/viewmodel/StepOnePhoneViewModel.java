package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.response.IsdCode;
import com.twixttechnologies.tjss.model.repository.IsdRepository;
import com.twixttechnologies.tjss.utils.M;

import java.util.List;

/**
 * @author Sony Raj on 04-08-17.
 */

public class StepOnePhoneViewModel extends AndroidViewModel implements RequestCallback<List<IsdCode>> {


    private final MutableLiveData<List<IsdCode>> mIsdCodesData = new MutableLiveData<>();
    private final MutableLiveData<String> mError = new MutableLiveData<>();

    private String mPhoneNumber;
    private IsdCode mSelectedIsdCode;

    private StepOnePhoneViewModel(Application application) {
        super(application);
        IsdRepository mIsdRepository = new IsdRepository();
        String url = getApplication().getString(R.string.isd_codes_url);
        mIsdRepository.get(url, this);
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.mPhoneNumber = phoneNumber;
    }

    public IsdCode getSelectedIsdCode() {
        return mSelectedIsdCode;
    }

    public void setSelectedIsdCode(IsdCode mSelectedIsdCode) {
        this.mSelectedIsdCode = mSelectedIsdCode;
    }

    public MutableLiveData<List<IsdCode>> getIsdCodesData() {
        return mIsdCodesData;
    }

    @Override
    public void onSuccess(List<IsdCode> result) {
        mIsdCodesData.setValue(result);
    }

    @Override
    public void onFailure(String reason) {
        mError.setValue(reason);
        if (reason == null) return;
        M.log(reason);
    }


    @SuppressWarnings("unchecked")
    public static class StepOnePhoneViewModeFactory implements ViewModelProvider.Factory {

        private final Application application;


        public StepOnePhoneViewModeFactory(Application application) {
            this.application = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new StepOnePhoneViewModel(application);
        }
    }

}
