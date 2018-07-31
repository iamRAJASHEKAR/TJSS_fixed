package com.twixttechnologies.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.GsonBuilder;
import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.TjssApplication;
import com.twixttechnologies.tjss.model.network.request.RequestCallback;
import com.twixttechnologies.tjss.model.network.request.UpdateFirebaseIdToken;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.twixttechnologies.tjss.model.network.response.Log;
import com.twixttechnologies.tjss.model.network.response.SignUpAndLoginResponse;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.utils.PreferenceHelper;

import java.util.HashMap;

/**
 * @author Sony Raj on 08-08-17.
 */

abstract class LoginSignUpViewModelBase extends AndroidViewModel {
    final MutableLiveData<String> mError = new MutableLiveData<>();
    private final MutableLiveData<SignUpAndLoginResponse> mSignUpResponseLiveData
            = new MutableLiveData<>();
//    SharedPreferences preferences,preferences1;
//    SharedPreferences.Editor editor,editor1;
    RequestCallback<SignUpAndLoginResponse> mSignUpLoginRequestCallback
            = new RequestCallback<SignUpAndLoginResponse>() {
        @Override
        public void onSuccess(SignUpAndLoginResponse result) {
            try {
                if (result != null) {
                    if (result.message != null && result.message.equals("Success")) {

//                        preferences=getApplication().getSharedPreferences("imageName",0);
//                        editor=preferences.edit();
                        PreferenceHelper.saveString(getApplication(), PreferenceHelper.KEY_FIRST_NAME, result.fname);
                        PreferenceHelper.saveString(getApplication(), PreferenceHelper.KEY_LAST_NAME,result.lname);
                        PreferenceHelper.saveString(getApplication(), PreferenceHelper.KEY_EMAIL, result.email);
                        PreferenceHelper.saveString(getApplication(), PreferenceHelper.KEY_PRIMARY_CIRCLE, result.primaryCircle);
                        PreferenceHelper.saveString(getApplication(), PreferenceHelper.KEY_PROFILE_IMAGE, result.profileImage);

                        //editor.clear();
                        //editor.putString("image", result.profileImage);


                        PreferenceHelper.saveString(getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, result.accesstoken);
                        PreferenceHelper.saveString(getApplication(), PreferenceHelper.KEY_REFRESH_TOKEN, result.refreshtoken);
                        PreferenceHelper.saveString(getApplication(), PreferenceHelper.KEY_PHONE, result.phone);
                        PreferenceHelper.saveString(getApplication(), PreferenceHelper.KEY_USER_ID, result.userid);
//
//                        preferences1=getApplication().getSharedPreferences("userId",0);
//                        editor1=preferences1.edit();
//                        editor1.putString("userId",result.userid);
//                        editor1.commit();
////                      editor.putString("userId",result.userid);
//                        editor.commit();

                        PreferenceHelper.saveString(getApplication(), PreferenceHelper.KEY_SUBSCRIPTION_PLAN_ID, result.subscriptionPlan);
                        PreferenceHelper.saveString(getApplication(), PreferenceHelper.KEY_MAP_OPTIONS, result.mapOptions);
                        PreferenceHelper.saveBoolean(getApplication(), PreferenceHelper.KEY_USER_LOGGED_IN, true);
                        PreferenceHelper.saveInt(getApplication(), PreferenceHelper.KEY_SERVICE_PROVIDER, 0);
                        boolean hasSecurity = result.securityQuestion != null && !result.securityQuestion.equals("") && result.securityQuestion.equals("1");
                        PreferenceHelper.saveBoolean(getApplication(), PreferenceHelper.KEY_ADDED_SECURITY, hasSecurity);
                        ((TjssApplication) getApplication()).startLocationUpdates();
                        ((TjssApplication) getApplication()).startBatteryMonitor();
                        ((TjssApplication) getApplication()).checkHasSecurityQuestion(result.userid);
                        String firebaseToken = FirebaseInstanceId.getInstance().getToken();
                        if (firebaseToken == null || firebaseToken.equals("")) return;
                        String userId = PreferenceHelper.getString(getApplication(), PreferenceHelper.KEY_USER_ID, "");
                        String accessToken = PreferenceHelper.getString(getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
                        String url = getApplication().getString(R.string.firebase_id_update_path);

                        HashMap<String, String> headerMap = new HashMap<>(2);
                        headerMap.put("api_token", accessToken);
                        headerMap.put("userid", userId);

                        HashMap<String, String> params = new HashMap<>(2);
                        params.put("userId", userId);
                        params.put("firebaseId", firebaseToken);

                        new UpdateFirebaseIdToken(TjssNetworkInterface.class, null)
                                .update(url, headerMap, params);

                    } else {
                        mError.setValue(result.message);
                    }
                }
            } catch (Exception e) {
                M.log(e.getMessage());
            }

            mSignUpResponseLiveData.setValue(result);
        }

        @Override
        public void onFailure(String reason) {
            mError.setValue(reason);
        }
    };

    LoginSignUpViewModelBase(Application application) {
        super(application);
    }

    public MutableLiveData<SignUpAndLoginResponse> getSignUpResponseLiveData() {
        return mSignUpResponseLiveData;
    }

    public MutableLiveData<String> getError() {
        return mError;
    }
}
