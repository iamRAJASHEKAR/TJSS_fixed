package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.Activities.Test_help_alert_activity;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.AppServiceProvider;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;
import com.innoviussoftwaresolution.tjss.model.repository.AppServiceProvidersRepository;
import com.innoviussoftwaresolution.tjss.model.repository.HelpAlertRepository;
import com.innoviussoftwaresolution.tjss.utils.LocationUtil;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;
import com.innoviussoftwaresolution.tjss.view.activity.ServiceProvidersListingActivity;

import java.util.HashMap;
import java.util.List;


/**
 * @author Sony Raj on 25-09-17.
 */
public class AppServiceProvidersViewModel extends ViewModelBase {

    Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    private final AppServiceProvidersRepository mRepository;
    private final MutableLiveData<List<AppServiceProvider>> mServiceProvidersData
            = new MutableLiveData<>();

    private final MutableLiveData<StatusMessage> mStatusMessageData
            = new MutableLiveData<>();


    public MutableLiveData<StatusMessage> getStatusMessageData() {
        return mStatusMessageData;
    }

    private final RequestCallback<List<AppServiceProvider>> mRequestCallabck
            = new RequestCallback<List<AppServiceProvider>>() {
        @Override
        public void onSuccess(List<AppServiceProvider> result) {
            mServiceProvidersData.setValue(result);
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

            if(result!=null)
            {
                Intent intent=new Intent(context, Test_help_alert_activity.class);
                if(result.status.equalsIgnoreCase("success"))
                {

                    intent.putExtra("status",result.status);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // M.showToast(context,"alert sent");
                    context.startActivity(intent);

                }
                else
                {
                    M.showToast(context,"Failed to send help alert");
                    intent.putExtra("status","failed");
                }

            }
            else
            {
                M.showToast(context,"An error occurred.Try again latter");
            }



        }

        @Override
        public void onFailure(String reason) {
            M.log(reason);
            M.showToast(context, "Help alert failed to send");
        }
    };

    private final RequestCallback<StatusMessage> mDeleteRequestCallback
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


    private HelpAlertRepository mHelpAlertRepository;


    private AppServiceProvidersViewModel(Application application) {
        super(application);
        mRepository = new AppServiceProvidersRepository();
        getAppServiceProviders();
    }

    public MutableLiveData<List<AppServiceProvider>> getServiceProvidersData() {
        return mServiceProvidersData;
    }

    private void getAppServiceProviders() {
        String path = getApplication().getString(R.string.app_service_providers_list_path);
        mRepository.get(path, getHeaders(), getUserIdParamsMap(), mRequestCallabck);
    }

    public void sendHelpAlert(Context activity) {
        context = activity;


        preferences = activity.getSharedPreferences("CategotyIds", 0);
        String spId = preferences.getString("serviceProviderId", "");
        String subCatId = preferences.getString("subCategoryId", "");
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
        params.put("service_providerid", spId);
        params.put("sub_category_id", subCatId);

        SharedPreferences sharedPreferences=activity.getSharedPreferences("isdCode",0);
        params.put("isd_code",sharedPreferences.getString("code",""));
        params.put("safety_circle_id",PreferenceHelper.getString(this.getApplication(),PreferenceHelper.KEY_PRIMARY_CIRCLE,""));
        if (mHelpAlertRepository == null) mHelpAlertRepository = new HelpAlertRepository();
        mHelpAlertRepository.sendHelpAlert(path, getHeaders(), params, mRequestCallback);
    }

    public void delete(AppServiceProvider provider) {
        String path = getApplication().getString(R.string.app_service_providers_delete_path);
        HashMap<String, String> params = getUserIdParamsMap();
        params.put("deleteId", String.valueOf(provider.deleteId));
        mRepository.delete(path, getHeaders(), params, mDeleteRequestCallback);
    }


    @SuppressWarnings("unchecked")
    public static class AppServiceProvidersViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public AppServiceProvidersViewModelFactory(Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new AppServiceProvidersViewModel(mApplication);
        }
    }


}