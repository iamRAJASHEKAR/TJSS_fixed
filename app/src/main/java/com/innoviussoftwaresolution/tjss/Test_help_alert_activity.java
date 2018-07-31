package com.innoviussoftwaresolution.tjss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.innoviussoftwaresolution.tjss.AddDeleteCheckInHistory.APIClient;
import com.innoviussoftwaresolution.tjss.AddDeleteCheckInHistory.EndpointsServices;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.NearByServiceProviderRequestModel;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersModels.NearByServiceProviderResponseModel;
import com.innoviussoftwaresolution.tjss.utils.LocationUtil;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Test_help_alert_activity extends AppCompatActivity

{
    private String status,userId;
    private Intent intent;
    private NearByServiceProviderRequestModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_help_alert_activity);
//        getActionBar().show();
//        getActionBar().setTitle("Help alert");


        userId=PreferenceHelper.getString(getApplicationContext(),PreferenceHelper.KEY_USER_ID,"");
        status = getIntent().getStringExtra("status");
        if (status.equalsIgnoreCase("success")) {
         //   searchServiceProvider();
        }

    }

   /*   private void searchServiceProvider(){


        LocationUtil locationUtil = new LocationUtil(getApplicationContext());
        android.location.Location location = locationUtil.getFineLocation();
        model = new NearByServiceProviderRequestModel();
        model.setLatitude(String.valueOf(location.getLatitude()));
        model.setLongitude(String.valueOf(location.getLongitude()));
        model.setUserId(userId);
      //  model.setUserId(PreferenceHelper.getString(getApplicationContext(), PreferenceHelper.KEY_USER_ID, ""));

        EndpointsServices services = APIClient.getClient().create(EndpointsServices.class);
        Call<NearByServiceProviderResponseModel> call = services.getNearByProviders(model);
        call.enqueue(new Callback<List<NearByServiceProviderResponseModel>>() {
            @Override
            public void onResponse(Call<List<NearByServiceProviderResponseModel>> call, Response<List<NearByServiceProviderResponseModel>> response) {
                if (response != null) {
                    Log.e("Response", new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    if (response.body() != null) {
                        Toast.makeText(getApplicationContext(), response.body().get(1).getProviderId() + " " + response.body().get(2).getProviderId() + " "
                                + response.body().get(3).getProviderId(), Toast.LENGTH_LONG).show();

                    }
                }
//                        for(int i = 0;i<response.body().size();i++)
//                        {
//                            //list.add(new NearByServiceProviderResponseModel(response.body().get(i).getProviderId()));
//
//                        }
            }

            @Override
            public void onFailure(Call<List<NearByServiceProviderResponseModel>> call, Throwable t) {

            }
        });
*/
    }



