package com.innoviussoftwaresolution.tjss.ServiceProviderModule;

import android.Manifest;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.innoviussoftwaresolution.tjss.APIcalls.EndpointsServices;
import com.innoviussoftwaresolution.tjss.AddDeleteCheckInHistory.APIClient;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.CheckStatusRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.CheckStatusResponseModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.NearByServiceProviderRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.NearByServiceProviderResponseModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.ProviderList;
import com.innoviussoftwaresolution.tjss.model.internal.InternalContact;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;
import com.innoviussoftwaresolution.tjss.model.repository.HelpAlertRepository;
import com.innoviussoftwaresolution.tjss.utils.LocationUtil;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;
import com.innoviussoftwaresolution.tjss.view.fragment.helpalert.alert.CountDownAlert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.HEAD;

public class Test_help_alert_activity extends AppCompatActivity implements CountDownAlert.OnCountDownFinishedListener

{
    private String status, userId;
    private Intent intent;
    private NearByServiceProviderRequestModel model;

    private ArrayList<ProviderList> nearByProviderList, selectedProviderList, mergeList;

    private SharedPreferences preferences;
    private HelpAlertRepository mHelpAlertRepository;
    private SharedPreferences.Editor editor;
    CheckStatusRequestModel requestModel;
    CheckStatusResponseModel responseModel;
    private String providerId;

    private int countFinal = 0;

    private RequestCallback<StatusMessage> mRequestCallback = new RequestCallback<StatusMessage>() {
        @Override
        public void onSuccess(StatusMessage result) {

            if (result.status.equalsIgnoreCase("success")) {
                //  M.showToast(getApplicationContext(), "Check status");
                //checkStatus();
                // showTimer();
                showCountDown();
            }


        }

        @Override
        public void onFailure(String reason) {

            M.showToast(getApplicationContext(), "Failed to sent alert");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_help_alert_activity);
//    getSupportActionBar().show();
//    getSupportActionBar().setTitle("Help Alerts");

        userId = PreferenceHelper.getString(getApplicationContext(), PreferenceHelper.KEY_USER_ID, "");
        status = getIntent().getStringExtra("status");
        if (status.equalsIgnoreCase("success")) {
            searchServiceProvider();
        }


    }

    private void searchServiceProvider() {


        nearByProviderList = new ArrayList<>();
        selectedProviderList = new ArrayList<>();

        mergeList = new ArrayList<>();
        LocationUtil locationUtil = new LocationUtil(getApplicationContext());
        android.location.Location location = locationUtil.getFineLocation();
        model = new NearByServiceProviderRequestModel();
        model.setLatitude(String.valueOf(location.getLatitude()));
        model.setLongitude(String.valueOf(location.getLongitude()));
        model.setUserId(userId);
        //  model.setUserId(PreferenceHelper.getString(getApplicationContext(), PreferenceHelper.KEY_USER_ID, ""));

        EndpointsServices services = APIClient.getClient().create(EndpointsServices.class);
        /*Call<List<NearByServiceProviderResponseModel>> call = services.getNearByProviders(model);*/
        Call<NearByServiceProviderResponseModel> call = services.getNearByProviders(model);
        call.enqueue(new Callback<NearByServiceProviderResponseModel>() {
            @Override
            public void onResponse(Call<NearByServiceProviderResponseModel> call, Response<NearByServiceProviderResponseModel> response) {
                Log.e("Response", new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if (response != null) {

        /*            if response.body().getNearByProviderList().size() == 0 && response.body().getSelectedProviderList().size() == 0)
                    {
                       callControlCenter();

                        callControlCenter();

                    } else {
                       // nearByProviderList.addAll(response.body().getNearByProviderList());
                        selectedProviderList.addAll(response.body().getSelectedProviderList());

                        mergeList.addAll(selectedProviderList);
                        mergeList.addAll(nearByProviderList);
                        initHelpAlert(countFinal);

                    }

                        initHelpAlert(countFinal);
*/

                }

            }

            @Override
            public void onFailure(Call<NearByServiceProviderResponseModel> call, Throwable t) {

            }
        });
    }

    /*private void initHelpAlert(int count)
    {
        switch (count) {
            case 0:

                if (mergeList.size() > count) {
                    sentHelpAlert(mergeList.get(count).getProvider_id(), "");
                } else {
                    callControlCenter();
                }
                break;
            case 1:
                if (mergeList.size() > count) {
                    sentHelpAlert(mergeList.get(count).getProvider_id(), "");
                } else {
                    callControlCenter();
                }
                break;
            case 2:
                if (mergeList.size() > count) {
                    sentHelpAlert(mergeList.get(count).getProvider_id(), "");
                } else {
                    callControlCenter();
                }
                break;
            default:
               callControlCenter();

                if (selectedProviderList.size() > 0 && selectedProviderList.size() > count) {
                    if (count != selectedProviderList.size()) {
                        sentHelpAlert(selectedProviderList.get(count).getProvider_id(), "");
                        //selectedProviderList.remove(countFinal);
                        setProviderId(selectedProviderList.get(count).getProvider_id());
                        //showTimer();
                    }

                } else {
                    if (nearByProviderList.size() > 0 && nearByProviderList.size() > count) {
                        sentHelpAlert(nearByProviderList.get(count).getProvider_id(), "");
                        // nearByProviderList.remove(countFinal);
                        setProviderId(nearByProviderList.get(count).getProvider_id());
                        //  showTimer();
                    }
                }
                break;
            case 1:
                if (selectedProviderList.size() > 0 && selectedProviderList.size() > count) {
                    if (count != selectedProviderList.size()) {
                        sentHelpAlert(selectedProviderList.get(count).getProvider_id(), "");
                        //selectedProviderList.remove(countFinal);
                        setProviderId(selectedProviderList.get(count).getProvider_id());
                        //showTimer();
                    }
                } else {
                    if (nearByProviderList.size() > 0 && nearByProviderList.size() > count) {
                        sentHelpAlert(nearByProviderList.get(count).getProvider_id(), "");
                        // nearByProviderList.remove(countFinal);
                        setProviderId(nearByProviderList.get(count).getProvider_id());
                        // showTimer();
                    }
                }
                break;
            case 2:
                if (selectedProviderList.size() > 0 && selectedProviderList.size() > count) {
                    if (count != selectedProviderList.size()) {
                        sentHelpAlert(selectedProviderList.get(count).getProvider_id(), "");
                        //selectedProviderList.remove(countFinal);
                        setProviderId(selectedProviderList.get(count).getProvider_id());
                        //showTimer();
                    }
                } else {
                    if (nearByProviderList.size() > 0 && nearByProviderList.size() > count) {
                        sentHelpAlert(nearByProviderList.get(count).getProvider_id(), "");
                        // nearByProviderList.remove(countFinal);
                        setProviderId(nearByProviderList.get(count).getProvider_id());
                        //showTimer();
                    }
                }
                break;
            case 3:
                callControlCenter();
                break;
            default:
                callControlCenter();
        }
    }*/

    private void callControlCenter() {


        AlertDialog.Builder builder = new AlertDialog.Builder(Test_help_alert_activity.this);
        builder.setTitle("Alert");
        builder.setMessage("No nearby service provider found. We are redirecting you to TJSS Control Center");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + "9037052150"));
                if (ActivityCompat.checkSelfPermission(Test_help_alert_activity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Test_help_alert_activity.this, new String[]{Manifest.permission.CALL_PHONE}, 100);

                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
                Test_help_alert_activity.this.finish();
            }
        });

        AlertDialog dialog;
        dialog = builder.create();
        dialog.show();
    }

    private void sentHelpAlert(String providerId, String subCategoryid) {
//        preferences = getApplicationContext().getSharedPreferences("CategotyIds", 0);
//        String spId = preferences.getString("serviceProviderId", "");
//        String subCatId = preferences.getString("subCategoryId", "");
//        editor.commit();
        LocationUtil locationUtil = new LocationUtil(this.getApplication());
        android.location.Location location = locationUtil.getFineLocation();
        if (location == null) {
            M.showToast(getApplicationContext(), "Enable location");
            return;
        }
        String path = getApplication().getString(R.string.help_alert_path);
        String phone = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_PHONE, "");
        if (phone.equals("")) return;
        HashMap<String, String> params = new HashMap<>(4);
        params.put("userId", userId);
        params.put("phone", phone);
        params.put("latitude", String.valueOf(location.getLatitude()));
        params.put("longitude", String.valueOf(location.getLongitude()));
        params.put("service_providerid", providerId);
        params.put("sub_category_id", subCategoryid);
        if (mHelpAlertRepository == null) mHelpAlertRepository = new HelpAlertRepository();
        mHelpAlertRepository.sendHelpAlert(path, getHeaders(), params, mRequestCallback);
    }

    HashMap<String, String> getHeaders() {
        String token = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, "");


        HashMap<String, String> headers = new HashMap<>(2);
        headers.put("api_token", token);
        headers.put("userId", userId);
        return headers;
    }


    @Override
    public void onFinish() {


    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    private void checkStatus() {

        requestModel = new CheckStatusRequestModel();
        requestModel.setUserId(userId);
        requestModel.setProviderId(getProviderId());

        responseModel = new CheckStatusResponseModel();

        EndpointsServices services = APIClient.getClient().create(EndpointsServices.class);
        Call<CheckStatusResponseModel> call = services.checkStatus(requestModel);
        call.enqueue(new Callback<CheckStatusResponseModel>() {
            @Override
            public void onResponse(Call<CheckStatusResponseModel> call, Response<CheckStatusResponseModel> response) {
                Log.e("Response", new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if (response != null) {

                    Log.d("status", response.body().getStatus());
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("1")) {

                            M.showToast(Test_help_alert_activity.this, "Help alert sent successfully");
                            Test_help_alert_activity.this.finish();
                        } else if ((response.body().getStatus().equalsIgnoreCase("0"))) {
                            if (mergeList.size() == countFinal || countFinal > 2) {
                                callControlCenter();
                            } else {
                                countFinal = countFinal + 1;
                                showAlert();
                            }
                        }
                    }
            if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase("0") || countFinal > 3) {

                            callControlCenter();
                            //  M.showToast(getApplicationContext(), "Status check " + String.valueOf(countFinal));
                        } else {
                            countFinal = countFinal + 1;
                            showAlert();
                            //M.showToast(getApplicationContext(), "Provider Unavailable. Trying to reach other provider.");
                        }
                    }


                }
            }


            @Override
            public void onFailure(Call<CheckStatusResponseModel> call, Throwable t) {
                M.showToast(getApplicationContext(), "Failed to send");
            }
        });


    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Test_help_alert_activity.this);
        builder.setTitle("Provider Unavailable");
        builder.setMessage("Trying to reach other provider");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //initHelpAlert(countFinal);
            }
        });

        AlertDialog dialog;
        dialog = builder.create();
        dialog.show();
    }

    protected void showCountDown() {

        LayoutInflater layoutInflater = LayoutInflater.from(Test_help_alert_activity.this);
        final View promptView = layoutInflater.inflate(R.layout.extra_count_down_timer_text, null);
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(
                Test_help_alert_activity.this);

        alertDialogBuilder.setView(promptView);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false);

        android.support.v7.app.AlertDialog dialog = alertDialogBuilder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            private static final int AUTO_DISMISS_MILLIS = 30000;

            @Override
            public void onShow(final DialogInterface dialog) {
                final AppCompatTextView tvTimer = (AppCompatTextView) promptView.findViewById(R.id.tvTimer);
                new CountDownTimer(AUTO_DISMISS_MILLIS, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        tvTimer.setText(String.valueOf(millisUntilFinished / 1000));

                    }

                    @Override
                    public void onFinish() {
                        if (((AlertDialog) dialog).isShowing()) {

                            checkStatus();
                            dialog.dismiss();


                        }
                    }
                }.start();
            }
        });

        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
        dialog.show();
    }

}
