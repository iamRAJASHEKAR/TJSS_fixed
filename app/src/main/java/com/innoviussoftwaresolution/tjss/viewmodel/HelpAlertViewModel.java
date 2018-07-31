package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.Activities.Test_help_alert_activity;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyCircleContact;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;
import com.innoviussoftwaresolution.tjss.model.repository.HelpAlertRepository;
import com.innoviussoftwaresolution.tjss.utils.LocationUtil;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;
import com.innoviussoftwaresolution.tjss.view.activity.HelpAlertActivity;
import com.innoviussoftwaresolution.tjss.view.activity.ServiceProvidersListingActivity;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 19-09-17.
 */

/**
 * @author Sony Raj on 22-09-17.
 */
public class HelpAlertViewModel extends ViewModelBase {
    Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
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


            Intent intent=new Intent(context, Test_help_alert_activity.class);
            if(result!=null) {
                if (result.status.equalsIgnoreCase("success")) {

                    intent.putExtra("status", result.status);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // M.showToast(context,"alert sent");
                    context.startActivity(intent);

                } else {
                    intent.putExtra("status", "failed");
                }


            }
            else {
                M.showToast(context,"An error occurred. Please try again latter.");
            }



        }

        @Override
        public void onFailure(String reason) {
            M.log(reason);
            M.showToast(context, "Help alert can not be sent");
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

    public void delete(String contactId) {
        String path = getApplication().getString(R.string.delete_contact_path);
        HashMap<String, String> params = new HashMap<>(1);
        params.put("contactId", contactId);

        if (mDeleteRequestCallback == null) {
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

        mRepository.updateOrDelete(path, getHeaders(), params, mDeleteRequestCallback);
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

    public void sendHelpAlert(Context activity) {
        context = activity;
        preferences=activity.getSharedPreferences("CategotyIds",0);
        String spId =  preferences.getString("serviceProviderId","");
        String subCatId = preferences.getString("subCategoryId","");
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
        // params.put("phone", phone);
        params.put("latitude", String.valueOf(location.getLatitude()));
        params.put("longitude", String.valueOf(location.getLongitude()));
        params.put("safety_circle_id", PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_PRIMARY_CIRCLE, ""));
        params.put("service_providerid",spId);
        params.put("sub_category_id",subCatId);
        SharedPreferences sharedPreferences=activity.getSharedPreferences("isdCode",0);
        params.put("isd_code",sharedPreferences.getString("code",""));
        params.put("safety_circle_id",PreferenceHelper.getString(this.getApplication(),PreferenceHelper.KEY_PRIMARY_CIRCLE,""));
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