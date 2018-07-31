package com.innoviussoftwaresolution.tjss.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.request.UpdateFirebaseIdToken;
import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;

import java.util.HashMap;

public class TjssFireBaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        M.log("Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        String userId = PreferenceHelper.getString(getApplication(), PreferenceHelper.KEY_USER_ID, "");
        String authToken = PreferenceHelper.getString(getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
        HashMap<String, String> headerMap = new HashMap<>(2);
        headerMap.put("api_token", authToken);
        headerMap.put("userid", userId);

        HashMap<String, String> params = new HashMap<>(2);
        params.put("userId", userId);
        params.put("firebaseId", refreshedToken);

        String url = getString(R.string.firebase_id_update_path);

        new UpdateFirebaseIdToken(TjssNetworkInterface.class, null)
                .update(url, headerMap, params);
    }

}
