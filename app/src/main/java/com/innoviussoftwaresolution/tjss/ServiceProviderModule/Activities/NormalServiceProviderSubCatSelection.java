package com.innoviussoftwaresolution.tjss.ServiceProviderModule.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.ServiceProviderModule.ProvidersAdapters.ServiceProviderSubCategorySelectionAdapter;
import com.innoviussoftwaresolution.tjss.model.network.response.ServiceProviderSubCategory;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.view.adapter.listadapter.ServiceProviderSubCategoriesAdapter;
import com.innoviussoftwaresolution.tjss.viewmodel.HelpAlertViewModel;
import com.innoviussoftwaresolution.tjss.viewmodel.ServiceProvidersSubCategoriesViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NormalServiceProviderSubCatSelection extends AppCompatActivity {

    @BindView(R.id.rvNormalSPCatList)
    RecyclerView mLstData;
    String categoryId = "";
    Intent intent = getIntent();
    ServiceProvidersSubCategoriesViewModel mViewModel;
    private HelpAlertViewModel mViewModel1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_service_provider_sub_cat_selection);
        ButterKnife.bind(this);
        getSubCategory();
    }

    public void getSubCategory() {
        categoryId = getIntent().getStringExtra("id");
        M.log("subCategories", categoryId);
        if (mViewModel == null)
            mViewModel = ViewModelProviders.of(this,
                    new ServiceProvidersSubCategoriesViewModel
                            .ServiceProvidersSubCategoriesViewModelFactory(getApplication()))
                    .get(ServiceProvidersSubCategoriesViewModel.class);
        mViewModel1 = ViewModelProviders.of(this,
                new HelpAlertViewModel.HelpAlertViewModelFactory(getApplication()))
                .get(HelpAlertViewModel.class);

        mViewModel.getSubCategoriesData().observe(this, new Observer<ArrayList<ServiceProviderSubCategory>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ServiceProviderSubCategory> serviceProviderSubCategories) {
                if (serviceProviderSubCategories == null || serviceProviderSubCategories.size() <= 0) {
                    M.showAlert(getApplicationContext(), "Service Providers", "No Subcategories available for the selected category, please try again with another category",
                            "OK", null, null, null, false);
                } else {
                    if (mLstData != null) {
                        ServiceProviderSubCategorySelectionAdapter adapter = new ServiceProviderSubCategorySelectionAdapter(mViewModel1,getSupportFragmentManager(),serviceProviderSubCategories);
                        mLstData.setAdapter(adapter);
                        mLstData.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }
                }
            }
        });
        mViewModel.getSubCategories(categoryId);
    }

    // @OnClick(R.id.btnServiceProvidersSettingsProceed)
    public void onViewClicked() {
        try {
            ServiceProviderSubCategory selectedSubCategory = ((ServiceProviderSubCategoriesAdapter) mLstData.getAdapter()).getSelectedSubCategory();

            if (selectedSubCategory == null) {
                M.showAlert(getApplicationContext(), "Select category", "Please select a subcategory first", "OK",
                        null, null, null, false);
            } else {
//                Intent intent = new Intent(ServiceProviderSelectionFrgamentBase.SUB_CATEGORY_CHANGE_ACTION);
//
//                intent.putExtra("id", selectedSubCategory.id);
//                preferences = getActivity().getSharedPreferences("CategotyIds", 0);
//                editor = preferences.edit();
//                editor.putString("subCategoryId", selectedSubCategory.id);
//                editor.commit();
//                // Toast.makeText(getContext(), selectedSubCategory.id, Toast.LENGTH_LONG).show();
//                LocalBroadcastManager.getInstance(getActivity())
//                        .sendBroadcast(intent);
            }
        } catch (Throwable e) {
            Bugsnag.notify(new RuntimeException(e));
            Bugsnag.notify(e);
        }
    }
}
