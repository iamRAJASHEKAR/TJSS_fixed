package com.innoviussoftwaresolution.tjss.ServiceProviderModule.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersAdapters.ViewPageAdaper;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersFragments.ServiceProviderRequestList;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersFragments.ServiceProviderTaskList;
import com.innoviussoftwaresolution.tjss.TjssApplication;
import com.innoviussoftwaresolution.tjss.model.network.request.UpdateFirebaseIdToken;
import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.innoviussoftwaresolution.tjss.service.ChatListenerService;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;
import com.innoviussoftwaresolution.tjss.view.activity.LoginOrSignUpActivity;

import java.util.HashMap;

public class ServiceProviderHome extends AppCompatActivity  {

    private android.support.v7.widget.Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AppCompatTextView tvLogOut;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_home);

        mapViews();
    }

    //Map views
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void mapViews() {
        tabLayout = (TabLayout) findViewById(R.id.serviceProviderTablayout);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.serviceProviderToolbar);
        viewPager = (ViewPager) findViewById(R.id.serviceProviderViewpager);
        tvLogOut = (AppCompatTextView) findViewById(R.id.tvLogOut);
        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        setSupportActionBar(toolbar);
      //  getSupportActionBar().setTitle(R.string.serviceproviderActionBarText);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.provider_logout_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.menuProviderLogOut:
//
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//    }

    private void setViewPager(ViewPager viewPager) {
        ViewPageAdaper adapter = new ViewPageAdaper(getSupportFragmentManager());
        adapter.addFragment(new ServiceProviderTaskList(), getString(R.string.taskListTabTxt));
        adapter.addFragment(new ServiceProviderRequestList(), getString(R.string.requestTabTxt));
        viewPager.setAdapter(adapter);
    }

  private void logout()
  {
      String userId = PreferenceHelper.getString(getApplicationContext(), PreferenceHelper.KEY_USER_ID, "");
      String authToken = PreferenceHelper.getString(getApplicationContext(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
      HashMap<String, String> headerMap = new HashMap<>(2);
      headerMap.put("api_token", authToken);
      headerMap.put("userid", userId);

      HashMap<String, String> params = new HashMap<>(2);
      params.put("userId", userId);
      params.put("firebaseId", "");

      String url = getString(R.string.firebase_id_update_path);

      new UpdateFirebaseIdToken(TjssNetworkInterface.class, null)
              .update(url, headerMap, params);

      PreferenceHelper.saveString(getApplicationContext(), PreferenceHelper.KEY_FIRST_NAME, "");
      PreferenceHelper.saveString(getApplicationContext(), PreferenceHelper.KEY_LAST_NAME, "");
      PreferenceHelper.saveString(getApplicationContext(), PreferenceHelper.KEY_EMAIL, "");
      PreferenceHelper.saveString(getApplicationContext(), PreferenceHelper.KEY_PRIMARY_CIRCLE, "");
      PreferenceHelper.saveString(getApplicationContext(), PreferenceHelper.KEY_PROFILE_IMAGE, "");
      PreferenceHelper.saveString(getApplicationContext(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
      PreferenceHelper.saveString(getApplicationContext(), PreferenceHelper.KEY_REFRESH_TOKEN, "");
      PreferenceHelper.saveString(getApplicationContext(), PreferenceHelper.KEY_PHONE, "");
      PreferenceHelper.saveString(getApplicationContext(), PreferenceHelper.KEY_USER_ID, "");
      PreferenceHelper.saveString(getApplicationContext(), PreferenceHelper.KEY_SUBSCRIPTION_PLAN_ID, "");
      PreferenceHelper.saveString(getApplicationContext(), PreferenceHelper.KEY_MAP_OPTIONS, "");
      PreferenceHelper.saveBoolean(getApplicationContext(), PreferenceHelper.KEY_USER_LOGGED_IN, false);


      ((TjssApplication) getApplicationContext()).stopLocationUpdates();
      ((TjssApplication) getApplicationContext()).stopBatteryMonitor();
      getApplicationContext().stopService(new Intent(getApplicationContext(), ChatListenerService.class));
      Intent signInOrSignUp = new Intent(getApplicationContext(), LoginOrSignUpActivity.class);
      signInOrSignUp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
      this.finish();
      startActivity(signInOrSignUp);
  }
}
