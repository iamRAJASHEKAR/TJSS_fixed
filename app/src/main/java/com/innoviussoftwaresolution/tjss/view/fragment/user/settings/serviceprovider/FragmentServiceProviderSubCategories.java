package com.innoviussoftwaresolution.tjss.view.fragment.user.settings.serviceprovider;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bugsnag.android.Bugsnag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.ServiceProviderSubCategory;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.view.adapter.listadapter.ServiceProviderSubCategoriesAdapter;
import com.innoviussoftwaresolution.tjss.viewmodel.ServiceProvidersSubCategoriesViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 21-10-17.
 */

public class FragmentServiceProviderSubCategories extends ServiceProviderSelectionFrgamentBase {

    @BindView(R.id.lblTitle)
    AppCompatTextView mLblTitle;
    @BindView(R.id.lstData)
    RecyclerView mLstData;
    Unbinder unbinder;
    ServiceProvidersSubCategoriesViewModel mViewModel;
    @JsonIgnore
    public boolean isLocallySelected = false;
    private String categoryId = null;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    void onSelectionChanged(Intent intent) {
        if (intent.getAction().equals(CATEGORY_CHANGE_ACTION)) {
            categoryId = intent.getStringExtra("id");
            M.log("subCategories", categoryId);
            if (mViewModel == null)
                mViewModel = ViewModelProviders.of(this,
                        new ServiceProvidersSubCategoriesViewModel
                                .ServiceProvidersSubCategoriesViewModelFactory(getActivity().getApplication()))
                        .get(ServiceProvidersSubCategoriesViewModel.class);


            mViewModel.getSubCategoriesData().observe(this, new Observer<ArrayList<ServiceProviderSubCategory>>() {
                @Override
                public void onChanged(@Nullable ArrayList<ServiceProviderSubCategory> serviceProviderSubCategories) {
                    if (serviceProviderSubCategories == null || serviceProviderSubCategories.size() <= 0) {
                        M.showAlert(getActivity(), "Service Providers", "No Subcategories available for the selected category, please try again with another category",
                                "OK", null, null, null, false);
                    } else {
                        if (mLstData != null) {
                            ServiceProviderSubCategoriesAdapter adapter = new ServiceProviderSubCategoriesAdapter(serviceProviderSubCategories);
                            mLstData.setAdapter(adapter);
                            mLstData.setLayoutManager(new LinearLayoutManager(getActivity()));
                        }
                    }
                }
            });
            mViewModel.getSubCategories(categoryId);
        }
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_providers_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        mLblTitle.setText("Select Subcategory");
        return view;
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnServiceProvidersSettingsProceed)
    public void onViewClicked() {
        try {
            ServiceProviderSubCategory selectedSubCategory = ((ServiceProviderSubCategoriesAdapter) mLstData.getAdapter()).getSelectedSubCategory();

            if (selectedSubCategory == null) {
                M.showAlert(getActivity(), "Select category", "Please select a subcategory first", "OK",
                        null, null, null, false);
            } else {
                Intent intent = new Intent(ServiceProviderSelectionFrgamentBase.SUB_CATEGORY_CHANGE_ACTION);

                intent.putExtra("id", selectedSubCategory.id);
                preferences = getActivity().getSharedPreferences("CategotyIds", 0);
                editor = preferences.edit();
                editor.putString("subCategoryId", selectedSubCategory.id);
                editor.commit();
               // Toast.makeText(getContext(), selectedSubCategory.id, Toast.LENGTH_LONG).show();
                LocalBroadcastManager.getInstance(getActivity())
                        .sendBroadcast(intent);
            }
        } catch (Throwable e) {
            Bugsnag.notify(new RuntimeException(e));
            Bugsnag.notify(e);
        }
    }
}
