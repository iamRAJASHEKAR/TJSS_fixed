package com.innoviussoftwaresolution.tjss.ServiceProviderModule.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersAdapters.ServiceProviderSelectionAdapter;
import com.innoviussoftwaresolution.tjss.model.network.response.ServiceProviderCategory;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;
import com.innoviussoftwaresolution.tjss.view.adapter.listadapter.ServiceProviderCategoriesAdapter;
import com.innoviussoftwaresolution.tjss.viewmodel.ServiceProviderCategoriesViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NormalServiceProviderSelection extends AppCompatActivity {

//
//    private RecyclerView rvProviderlist;
//    private ArrayList<ServiceProviderCategory> arrayList;
    private String userId;
    Unbinder unbinder;
    /*@BindView(R.id.lblTitle)
    AppCompatTextView mLblTitle;*/
    @BindView(R.id.rvNormalSPlist)
    RecyclerView mLstData;
    private ServiceProviderSelectionAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_service_provider_selection);
        ButterKnife.bind(this);
        initViews();
        loadData();

    }

    private void loadData() {
        ServiceProviderCategoriesViewModel viewModel
                = ViewModelProviders.of(this,
                new ServiceProviderCategoriesViewModel.ServiceProviderCategoriesViewModelFactory(
                        getApplication()
                )).get(ServiceProviderCategoriesViewModel.class);

        viewModel.getCategoriesData().observe(this, new Observer<ArrayList<ServiceProviderCategory>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ServiceProviderCategory> serviceProviderCategories) {
                if (serviceProviderCategories == null || serviceProviderCategories.size() <= 0) {
                    M.showAlert(getApplicationContext(), "Get Categories", "No service providers available for now, please try again later",
                            "OK", null, null, null, false);
                } else {
                    if (mAdapter == null) {
                        mAdapter = new ServiceProviderSelectionAdapter(serviceProviderCategories);
                    }
                    mLstData.setAdapter(mAdapter);
                    mLstData.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    mAdapter.setData(serviceProviderCategories);
                }
            }
        });
    }

    void initViews()
    {
                userId=PreferenceHelper.getString(getApplicationContext(),PreferenceHelper.KEY_USER_ID,"");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbinder.unbind();
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(NormalServiceProviderSelection.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog;
        dialog = builder.create();
        dialog.show();
    }
    /*@OnClick(R.id.rvNormalSPlist)
    public void onViewClicked() {
  //      Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        ServiceProviderCategory selectedCategory = ((ServiceProviderCategoriesAdapter) mLstData.getAdapter()).getSelectedCategory();

     *//*   if (selectedCategory == null) {
            M.showAlert(getActivity(), "Select category", "Please select a category first", "OK",
                    null, null, null, false);
        } else {
            Intent intent = new Intent(ServiceProviderSelectionFrgamentBase.CATEGORY_CHANGE_ACTION);
            intent.putExtra("id", selectedCategory.id);
            //  Toast.makeText(getContext(),selectedCategory.id,Toast.LENGTH_LONG).show();
            LocalBroadcastManager.getInstance(getActivity())
                    .sendBroadcast(intent);
        }*//*

    }*/
//
//    //views
//    private void initViews() {
//        rvProviderlist = (RecyclerView) findViewById(R.id.rvNormalSPlist);
//        userId=PreferenceHelper.getString(getApplicationContext(),PreferenceHelper.KEY_USER_ID,"");
//        Observer<List<AppServiceProvider>> mServiceProvidersObserver
//                = new Observer<List<AppServiceProvider>>() {
//            @Override
//            public void onChanged(@Nullable List<AppServiceProvider> serviceProviders) {
//                if (serviceProviders == null || serviceProviders.size() <= 0) {
//                    showAlert("Error", "An Error occurred. Please try again");
//
//                } else {
//                    if (rvProviderlist != null)
//                        rvProviderlist.setAdapter(new AppServiceProviderAdapter(serviceProviders));
//                    rvProviderlist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                }
//            }
//        };
//
//    }
//

//    HashMap<String, String> getHeaders() {
//        String token = PreferenceHelper.getString(this.getApplication(), PreferenceHelper.KEY_ACCESS_TOKEN, "");
//
//
//
//        HashMap<String, String> headers = new HashMap<>(2);
//        headers.put("api_token", token);
//        headers.put("userId", userId);
//        return headers;
//    }


}
