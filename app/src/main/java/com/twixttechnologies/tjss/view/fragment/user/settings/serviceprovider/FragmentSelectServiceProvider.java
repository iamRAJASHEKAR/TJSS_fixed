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
import com.twixttechnologies.tjss.model.network.response.AppServiceProvider;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.adapter.listadapter.AppServiceProviderAdapter;
import com.twixttechnologies.tjss.viewmodel.ServiceProviderSettingsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 21-10-17.
 */

public class FragmentSelectServiceProvider extends ServiceProviderSelectionFrgamentBase {

    @BindView(R.id.lblTitle)
    AppCompatTextView mLblTitle;
    @BindView(R.id.lstData)
    RecyclerView mLstData;
    private final Observer<List<AppServiceProvider>> mServiceProvidersObserver
            = new Observer<List<AppServiceProvider>>() {
        @Override
        public void onChanged(@Nullable List<AppServiceProvider> serviceProviders) {
            if (serviceProviders == null || serviceProviders.size() <= 0) {
                M.showAlert(getActivity(), "Service Providers", "No Service provider available for the selected subcategory, Please try again with another subcategory",
                        "OK", null, null, null, false);
            } else {
                if (mLstData != null)
                    mLstData.setAdapter(new AppServiceProviderAdapter(serviceProviders));
                mLstData.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        }
    };
    Unbinder unbinder;
    ServiceProviderSettingsViewModel mViewModel;
    private String subCategoryId = null;

    @Override
    void onSelectionChanged(Intent intent) {
        if (intent.getAction().equals(SUB_CATEGORY_CHANGE_ACTION)) {
            subCategoryId = intent.getStringExtra("id");
            try {
                ((AppServiceProviderAdapter) mLstData.getAdapter()).clear();
            } catch (Exception e) {
                M.log(e.getMessage());
            }
            if (mViewModel == null)
                mViewModel = ViewModelProviders.of(this,
                        new ServiceProviderSettingsViewModel.ServiceProviderSettingsViewModelFactory(
                                getActivity().getApplication()
                        )).get(ServiceProviderSettingsViewModel.class);

            mViewModel.getProvidersData().observe(this, mServiceProvidersObserver);
            mViewModel.getServiceProviders(subCategoryId);
        }
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_providers_settings, container, false);
        unbinder = ButterKnife.bind(this, view);
        mLblTitle.setText("Select Provider");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
        if (mLstData.getAdapter() == null) {
            return;
        }
        String selectedIds = ((AppServiceProviderAdapter) mLstData.getAdapter()).getSelectedIds();
        if (selectedIds == null || selectedIds.equals("") || selectedIds.equals(",")) {
            M.showAlert(getActivity(), "Service Providers", "Please select at least one provider", "OK",
                    null, null, null, false);
        } else {
            Intent intent = new Intent(SERVICE_PROVIDER_SELECTED_ACTION);
            intent.putExtra("id", selectedIds);
            LocalBroadcastManager.getInstance(getActivity())
                    .sendBroadcast(intent);
        }
    }
}
