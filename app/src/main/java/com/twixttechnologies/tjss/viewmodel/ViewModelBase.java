package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.twixttechnologies.tjss.utils.PreferenceHelper;

import java.util.HashMap;

/**
 * @author Sony Raj on 08-08-17.
 */

abstract class ViewModelBase extends AndroidViewModel {

    final MutableLiveData<String> mError = new MutableLiveData<>();

    ViewModelBase(Application application) {
        super(application);
    }

    public MutableLiveData<String> getError() {
        return mError;
    }

    @NonNull
    String getUserId() {
        return PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_USER_ID, "");
    }

    @NonNull
    HashMap<String, String> getHeaders() {
        String token = getToken();
        String userId = getUserId();

        HashMap<String, String> headers = new HashMap<>(2);
        headers.put("api_token", token);
        headers.put("userid", userId);
        return headers;
    }

    @NonNull
    String getToken() {
        return PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
    }


    @NonNull
    HashMap<String, String> getUserIdParamsMap() {
        HashMap<String, String> params = new HashMap<>(1);
        params.put("userId", getUserId());
        return params;
    }


}
