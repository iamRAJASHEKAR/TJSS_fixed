package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.CircleAlertsData;
import com.innoviussoftwaresolution.tjss.model.network.response.GeneralAlerts;
import com.innoviussoftwaresolution.tjss.model.network.response.MapOptionsAndCrime;
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyCircle;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;
import com.innoviussoftwaresolution.tjss.model.repository.SafetyCircleRepository;
import com.innoviussoftwaresolution.tjss.model.repository.SettingsRepository;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;

import java.util.HashMap;
import java.util.List;

/**
 * @author Sony Raj on 24-08-17.
 */

public class SettingsViewModel extends ViewModelBase {

    private final MutableLiveData<StatusMessage> mStatusMessageData
            = new MutableLiveData<>();

    private final MutableLiveData<List<SafetyCircle>> mSafetyCirclesData
            = new MutableLiveData<>();

    private final MutableLiveData<MapOptionsAndCrime> mMapOptionsData
            = new MutableLiveData<>();

    private final MutableLiveData<CircleAlertsData> mCircleAlertsData
            = new MutableLiveData<>();

    private final MutableLiveData<GeneralAlerts> mGeneralAlertsData
            = new MutableLiveData<>();

    private final SettingsRepository mRepository;
    private final RequestCallback<StatusMessage> mStatusCallback
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
    private final RequestCallback<MapOptionsAndCrime> mMapOptionsRequestCallback
            = new RequestCallback<MapOptionsAndCrime>() {
        @Override
        public void onSuccess(MapOptionsAndCrime result) {
            mMapOptionsData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private final RequestCallback<CircleAlertsData> mCircleAlertsDataRequestCallback
            = new RequestCallback<CircleAlertsData>() {
        @Override
        public void onSuccess(CircleAlertsData result) {
            mCircleAlertsData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private final RequestCallback<GeneralAlerts> mGeneralAlertsRequestCallback
            = new RequestCallback<GeneralAlerts>() {
        @Override
        public void onSuccess(GeneralAlerts result) {
            mGeneralAlertsData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    private SafetyCircleRepository mSafetyCircleRepository;
    private RequestCallback<List<SafetyCircle>> mSafetyCircleListCallback;
    private SettingsViewModel(Application application) {
        super(application);
        mRepository = new SettingsRepository();
    }

    public MutableLiveData<CircleAlertsData> getCircleAlertsData() {
        return mCircleAlertsData;
    }

    public MutableLiveData<MapOptionsAndCrime> getMapOptionsData() {
        return mMapOptionsData;
    }

    public MutableLiveData<StatusMessage> getStatusMessageData() {
        return mStatusMessageData;
    }

    public MutableLiveData<List<SafetyCircle>> getSafetyCirclesData() {
        return mSafetyCirclesData;
    }

    public MutableLiveData<GeneralAlerts> getGeneralAlertsData() {
        return mGeneralAlertsData;
    }

    @NonNull
    private HashMap<String, String> getParamsMap() {
        HashMap<String, String> params = new HashMap<>(2);
        params.put("userId", getUserId());
        return params;
    }

    public void populate() {
        String path = getApplication().getString(R.string.populate_map_options_and_crime_path);
        mRepository.populate(path, getHeaders(), getUserIdParamsMap(), mMapOptionsRequestCallback);
    }


    public void updateLocationDuration(int interval) {
        HashMap<String, String> params = getParamsMap();
        params.put("locationDuration", String.valueOf(interval));
        String path = getApplication().getString(R.string.location_update_interval_path);

        update(path, params);
    }

    public void getCircleAlertsData(SafetyCircle safetyCircle) {
        String path = getApplication().getString(R.string.populate_circle_alerts_path);
        HashMap<String, String> params = getUserIdParamsMap();
        params.put("circleId", safetyCircle.circleId);
        mRepository.populateCircleAlerts(path, getHeaders(), params, mCircleAlertsDataRequestCallback);
    }

    public void updateMapOptions(boolean hospital, boolean fireStation, boolean policeStation) {
        HashMap<String, String> params = getParamsMap();
        String s = "";
        if (hospital) s += "1,";
        if (fireStation) s += "2,";
        if (policeStation) s += "3,";
        s = s.trim();
        if (s.endsWith(","))
            s = s.substring(0, s.lastIndexOf(","));
        params.put("alertOptions", s);
        String path = getApplication().getString(R.string.map_options_update_path);
        update(path, params);

    }

    public void updateCrimeAndOffenders(String interval) {
        String path = getApplication().getString(R.string.crime_and_offenders_update_interval_path);
        HashMap<String, String> params = getParamsMap();
        params.put("crimeDuration", interval);
        update(path, params);
    }

    public void changePassword(String oldPassword, String newPassword) {
        String path = getApplication().getString(R.string.change_password_link);
        HashMap<String, String> params = getParamsMap();
        params.put("oldPassword", oldPassword);
        params.put("newPassword", newPassword);
        update(path, params);
    }

    public void updateAlertOptionsforCircle(String circleId,
                                            boolean email,
                                            boolean appNotifications,
                                            boolean sms) {

        String path = getApplication().getString(R.string.circle_alert_update_path);
        HashMap<String, String> params = getParamsMap();
        String s = "";
        if (email) s += "1,";
        if (appNotifications) s += "2,";
        if (sms) s += "3,";
        s = s.trim();
        if (s.endsWith(","))
            s = s.substring(0, s.lastIndexOf(","));

        params.put("alertOptions", s);
        params.put("circleId", circleId);
        update(path, params);
    }

    public void getSafetyCircles() {
        if (mSafetyCircleRepository == null) mSafetyCircleRepository = new SafetyCircleRepository();
        if (mSafetyCircleListCallback == null)
            mSafetyCircleListCallback = new RequestCallback<List<SafetyCircle>>() {
                @Override
                public void onSuccess(List<SafetyCircle> result) {
                    mSafetyCirclesData.setValue(result);
                }

                @Override
                public void onFailure(String reason) {
                    mError.setValue(reason);
                }
            };

        String path = getApplication().getString(R.string.user_s_safety_circle_link_path);
        String authHeader = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
        mSafetyCircleRepository.getSimpleSafetyCircles(path, authHeader, getUserId(), mSafetyCircleListCallback);
    }

    private void update(String path, HashMap<String, String> params) {
        mRepository.updateSettings(path, getHeaders(), params, mStatusCallback);
    }

    public void updateGeneralAlerts(String options) {
        HashMap<String, String> params = getUserIdParamsMap();
        params.put("generalAlerts", options);
        String path = getApplication().getString(R.string.update_general_alerts_path);
        update(path, params);
    }


    public void populateGeneralAlerts() {
        String path = getApplication().getString(R.string.populate_general_alerts_path);
        mRepository.populateGeneralAlerts(path, getHeaders(), getUserIdParamsMap(), mGeneralAlertsRequestCallback);
    }



    @SuppressWarnings("unchecked")
    public static class SettingsViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public SettingsViewModelFactory(Application application) {
            this.mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new SettingsViewModel(mApplication);
        }
    }

}
