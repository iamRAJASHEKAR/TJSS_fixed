package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.IsdCode;
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyCircleContact;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;
import com.innoviussoftwaresolution.tjss.model.repository.HelpAlertRepository;
import com.innoviussoftwaresolution.tjss.model.repository.IsdRepository;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 13/11/17.
 */
public class EditHelpAlertViewModel extends ViewModelBase {

    private final HelpAlertRepository mRepository;
    private final IsdRepository mIsdRepository;

    private final MutableLiveData<StatusMessage> mStatusMessageData
            = new MutableLiveData<>();

    private final MutableLiveData<List<IsdCode>> mIsdCodesData
            = new MutableLiveData<>();


    private final RequestCallback<StatusMessage> mStatusMessageRequestCallback
            = new RequestCallback<StatusMessage>() {
        @Override
        public void onSuccess(StatusMessage result) {
            mStatusMessageData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private final RequestCallback<List<IsdCode>> mIsdCodesCallback
            = new RequestCallback<List<IsdCode>>() {
        @Override
        public void onSuccess(List<IsdCode> result) {
            mIsdCodesData.setValue(result);

        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };


    public MutableLiveData<StatusMessage> getStatusMessageData() {
        return mStatusMessageData;
    }

    public MutableLiveData<List<IsdCode>> getIsdCodesData() {
        return mIsdCodesData;
    }

    private EditHelpAlertViewModel(Application application) {
        super(application);
        mRepository = new HelpAlertRepository();
        mIsdRepository = new IsdRepository();
        String path = getApplication().getString(R.string.isd_codes_url);
        mIsdRepository.get(path,mIsdCodesCallback);
    }



    public void edit(SafetyCircleContact contact){
        String path = getApplication().getString(R.string.update_help_alert_contact_path);
        HashMap<String, String> params = getUserIdParamsMap();
        params.put("phone",contact.phone);
        params.put("name",contact.name);
        params.put("contactId",contact.id);
        params.put("email",contact.email);
        mRepository.updateOrDelete(path,getHeaders(),params,mStatusMessageRequestCallback);
    }


    @SuppressWarnings("unchecked")
    public static class EditHelpAlertViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public EditHelpAlertViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new EditHelpAlertViewModel(mApplication);
        }
    }


}