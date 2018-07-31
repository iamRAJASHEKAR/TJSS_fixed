package com.twixttechnologies.tjss.view.fragment.user.settings.serviceprovider;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.ServiceProviderCategory;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.adapter.listadapter.ServiceProviderCategoriesAdapter;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.viewmodel.ServiceProviderCategoriesViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 21-10-17.
 */

public class FragmentServiceProviderCategories extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.lblTitle)
    AppCompatTextView mLblTitle;
    @BindView(R.id.lstData)
    RecyclerView mLstData;

    private ServiceProviderCategoriesAdapter mAdapter;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_providers_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLblTitle.setText("Select Service provider category");

        ServiceProviderCategoriesViewModel viewModel
                = ViewModelProviders.of(this,
                new ServiceProviderCategoriesViewModel.ServiceProviderCategoriesViewModelFactory(
                        getActivity().getApplication()
                )).get(ServiceProviderCategoriesViewModel.class);

        viewModel.getCategoriesData().observe(this, new Observer<ArrayList<ServiceProviderCategory>>() {
            @Override
            public void onChanged(@Nullable ArrayList<ServiceProviderCategory> serviceProviderCategories) {
                if (serviceProviderCategories == null || serviceProviderCategories.size() <= 0) {
                    M.showAlert(getActivity(), "Get Categories", "No service providers available for now, please try again later",
                            "OK", null, null, null, false);
                } else {
                    if (mAdapter == null) {
                        mAdapter = new ServiceProviderCategoriesAdapter(serviceProviderCategories);
                    }
                    mLstData.setAdapter(mAdapter);
                    mLstData.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mAdapter.setData(serviceProviderCategories);
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnServiceProvidersSettingsProceed)
    public void onViewClicked() {

        ServiceProviderCategory selectedCategory = ((ServiceProviderCategoriesAdapter) mLstData.getAdapter()).getSelectedCategory();

        if (selectedCategory == null) {
            M.showAlert(getActivity(), "Select category", "Please select a category first", "OK",
                    null, null, null, false);
        } else {
            Intent intent = new Intent(ServiceProviderSelectionFrgamentBase.CATEGORY_CHANGE_ACTION);
            intent.putExtra("id", selectedCategory.id);
            LocalBroadcastManager.getInstance(getActivity())
                    .sendBroadcast(intent);
        }

    }
}
