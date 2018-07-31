package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleContact;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.model.repository.HelpAlertRepository;
import com.twixttechnologies.tjss.utils.LocationUtil;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.utils.PreferenceHelper;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 19-09-17.
 */

/**
 * @author Sony Raj on 22-09-17.
 */
public class HelpAlertViewModel extends ViewModelBase {

    private final MutableLiveData<List<SafetyCircleContact>> mContactsData
            = new MutableLiveData<>();

    private final MutableLiveData<StatusMessage> mDeleteStatusData
            = new MutableLiveData<>();

    private final HelpAlertRepository mRepository;

    private final RequestCallback<List<SafetyCircleContact>> mContactsCallback
            = new RequestCallback<List<SafetyCircleContact>>() {
        @Override
        public void onSuccess(List<SafetyCircleContact> result) {
            mContactsData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private final RequestCallback<StatusMessage> mRequestCallback
            = new RequestCallback<StatusMessage>() {
        @Override
        public void onSuccess(StatusMessage result) {
            //M.log(result.status);
        }

        @Override
        public void onFailure(String reason) {
            M.log(reason);
        }
    };

    private RequestCallback<StatusMessage> mDeleteRequestCallback;

    public MutableLiveData<StatusMessage> getDeleteStatusData() {
        return mDeleteStatusData;
    }

    private HelpAlertViewModel(Application application) {
        super(application);
        mRepository = new HelpAlertRepository();
    }

    public void delete(String contactId){
        String path = getApplication().getString(R.string.delete_contact_path);
        HashMap<String,String> params = new HashMap<>(1);
        params.put("contactId",contactId);

        if (mDeleteRequestCallback == null){
            mDeleteRequestCallback = new RequestCallback<StatusMessage>() {
                @Override
                public void onSuccess(StatusMessage result) {
                    mDeleteStatusData.setValue(result);
                }

                @Override
                public void onFailure(String reason) {
                    mError.setValue(reason);
                }
            };
        }

        mRepository.updateOrDelete(path,getHeaders(),params,mDeleteRequestCallback);
    }

    public void getContacts() {
        String path = getApplication().getString(R.string.list_safety_circle_member_path);
        HashMap<String, String> params = new HashMap<>(1);
        params.put("userId", getUserId());
        mRepository.getContacts(path, getHeaders(), params, mContactsCallback);
    }

    public MutableLiveData<List<SafetyCircleContact>> getContactsData() {
        return mContactsData;
    }

    public void sendHelpAlert() {
        LocationUtil locationUtil = new LocationUtil(this.getApplication());
        android.location.Location location = locationUtil.getFineLocation();
        if (location == null) {
            mError.setValue("Please enable location");
            return;
        }
        String path = getApplication().getString(R.string.help_alert_path);
        String phone = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_PHONE, "");
        if (phone.equals("")) return;
        HashMap<String, String> params = new HashMap<>(4);
        params.put("userId", getUserId());
        params.put("phone", phone);
        params.put("latitude", String.valueOf(location.getLatitude()));
        params.put("longitude", String.valueOf(location.getLongitude()));
        mRepository.sendHelpAlert(path, getHeaders(), params, mRequestCallback);
    }

    @SuppressWarnings("unchecked")
    public static class HelpAlertViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public HelpAlertViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new HelpAlertViewModel(mApplication);
        }
    }


}