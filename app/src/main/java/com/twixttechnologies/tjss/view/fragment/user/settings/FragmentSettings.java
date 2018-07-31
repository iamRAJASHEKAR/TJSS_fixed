package com.twixttechnologies.tjss.view.fragment.user.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.TjssApplication;
import com.twixttechnologies.tjss.model.network.request.UpdateFirebaseIdToken;
import com.twixttechnologies.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.twixttechnologies.tjss.service.ChatListenerService;
import com.twixttechnologies.tjss.utils.PreferenceHelper;
import com.twixttechnologies.tjss.view.activity.AccountSettingsActivity;
import com.twixttechnologies.tjss.view.activity.AlertOptionsSettingsActivity;
import com.twixttechnologies.tjss.view.activity.LoginOrSignUpActivity;
import com.twixttechnologies.tjss.view.activity.MapSettingsActivity;
import com.twixttechnologies.tjss.view.activity.MedicalRecordsActivity;
import com.twixttechnologies.tjss.view.activity.SafetyCircleSettingsActivity;
import com.twixttechnologies.tjss.view.activity.SafetyTipsSettingsActivity;
import com.twixttechnologies.tjss.view.activity.ServiceProviderSelectionActivity;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Sony Raj on 25-07-17.
 */

public class FragmentSettings extends SettingsFragmentBase {

    Unbinder unbinder;
    @BindView(R.id.imgSettingsUserImage)
    CircleImageView mImgSettingsUserImage;
    @BindView(R.id.lblSettingsUserName)
    AppCompatTextView mLblSettingsUserName;
    @BindView(R.id.lblSettingsEmail)
    AppCompatTextView mLblSettingsEmail;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        String name = PreferenceHelper.getString(activity, PreferenceHelper.KEY_FIRST_NAME, "");
        String email = PreferenceHelper.getString(activity, PreferenceHelper.KEY_EMAIL, "");
        String profileImage = getString(R.string.image_base_url) + PreferenceHelper.getString(activity, PreferenceHelper.KEY_PROFILE_IMAGE, "");

        ((TjssApplication) activity.getApplication()).getGlide()
                .load(profileImage)
                .into(mImgSettingsUserImage);

        mLblSettingsEmail.setText(email);
        mLblSettingsUserName.setText(name);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSettingsSafetyCircle)
    public void onBtnSettingsSafetyCircleClicked() {
        startActivity(new Intent(getActivity(), SafetyCircleSettingsActivity.class));
    }

    @OnClick(R.id.btnSettingsMap)
    public void onBtnSettingsMapClicked() {
        startActivity(new Intent(getActivity(), MapSettingsActivity.class));
    }

    @OnClick(R.id.btnSettingsAlert)
    public void onBtnSettingsAlertClicked() {
        startActivity(new Intent(getActivity(), AlertOptionsSettingsActivity.class));
    }

    @OnClick(R.id.btnSettingsAccount)
    public void onBtnSettingsAccountClicked() {
        startActivity(new Intent(getActivity(), AccountSettingsActivity.class));
    }

    @OnClick(R.id.btnSettingsSafetyTips)
    public void onBtnSafetyTipsClicked() {
        startActivity(new Intent(getActivity(), SafetyTipsSettingsActivity.class));
    }

    @OnClick(R.id.btnSettingsMedicalRecords)
    public void onBtnMedicalRecordsClicked() {
        startActivity(new Intent(getActivity(), MedicalRecordsActivity.class));
    }

    @OnClick(R.id.btnSettingsServiceProviders)
    public void onServiceProvidersClicked() {
        startActivity(new Intent(getActivity(), ServiceProviderSelectionActivity.class));
    }

    @OnClick(R.id.btnLogout)
    public void onBtnLogoutClicked() {

        String userId = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_USER_ID, "");
        String authToken = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
        HashMap<String, String> headerMap = new HashMap<>(2);
        headerMap.put("api_token", authToken);
        headerMap.put("userid", userId);

        HashMap<String, String> params = new HashMap<>(2);
        params.put("userId", userId);
        params.put("firebaseId", "");

        String url = getString(R.string.firebase_id_update_path);

        new UpdateFirebaseIdToken(TjssNetworkInterface.class, null)
                .update(url, headerMap, params);

        PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_FIRST_NAME, "");
        PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_LAST_NAME, "");
        PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_EMAIL, "");
        PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_PRIMARY_CIRCLE, "");
        PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_PROFILE_IMAGE, "");
        PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
        PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_REFRESH_TOKEN, "");
        PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_PHONE, "");
        PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_USER_ID, "");
        PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_SUBSCRIPTION_PLAN_ID, "");
        PreferenceHelper.saveString(getActivity().getApplication(), PreferenceHelper.KEY_MAP_OPTIONS, "");
        PreferenceHelper.saveBoolean(getActivity().getApplication(), PreferenceHelper.KEY_USER_LOGGED_IN, false);


        ((TjssApplication) getActivity().getApplication()).stopLocationUpdates();
        ((TjssApplication) getActivity().getApplication()).stopBatteryMonitor();
        getActivity().stopService(new Intent(getActivity(), ChatListenerService.class));
        Intent signInOrSignUp = new Intent(getActivity(), LoginOrSignUpActivity.class);
        signInOrSignUp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(signInOrSignUp);
        getActivity().finishAffinity();
    }


    @Override
    public void onStatusChanged() {

    }

}
