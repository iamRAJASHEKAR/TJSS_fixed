package com.twixttechnologies.tjss.view.fragment.user.settings.serviceprovider;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.adapter.pageradapter.ServiceProviderSelectionPagerAdapter;
import com.twixttechnologies.tjss.view.widget.TouchDisabledViewPager;
import com.twixttechnologies.tjss.viewmodel.ServiceProviderSettingsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 21-10-17.
 */

public class FragmentServiceProvidersSelection extends ServiceProviderSelectionFrgamentBase {

    public static final String TAG = "serviceProviderSelection";
    @BindView(R.id.vpServiceProviderSelection)
    TouchDisabledViewPager mVpServiceProviderSelection;
    Unbinder unbinder;
    private String mCategoryId;
    private String mSubcategoryId;
    private ServiceProviderSettingsViewModel mViewModel;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
    }

    public boolean canGoBack() {
        int currentItem = mVpServiceProviderSelection.getCurrentItem();
        if (currentItem > 0)
            mVpServiceProviderSelection.setCurrentItem(currentItem - 1);
        return currentItem == 0;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("Service Providers");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_provider_selection, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mVpServiceProviderSelection.setAdapter(new ServiceProviderSelectionPagerAdapter(getChildFragmentManager()));

        mViewModel = ViewModelProviders.of(this,
                new ServiceProviderSettingsViewModel.ServiceProviderSettingsViewModelFactory(getActivity().getApplication()))
                .get(ServiceProviderSettingsViewModel.class);

        mViewModel.getStatusMessageMutableLiveData().observe(this, new Observer<StatusMessage>() {
            @Override
            public void onChanged(@Nullable StatusMessage statusMessage) {
                dismissDialog();
                if (statusMessage == null || statusMessage.status == null || statusMessage.status.equals("") || !statusMessage.status.toLowerCase().contains("success")) {
                    M.showAlert(getActivity(), "Service Provider", "An error occurred, Please try again later",
                            "OK", null, null, null, false);
                } else {
                    M.showAlert(getActivity(), "Service Providers", "Saved Successfully", "OK",
                            null, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getActivity().finish();
                                }
                            }, null, false);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    void onSelectionChanged(Intent intent) {
        if (intent.getAction().equals(CATEGORY_CHANGE_ACTION)) {
            mVpServiceProviderSelection.setCurrentItem(1, true);
            mCategoryId = intent.getStringExtra("id");
        } else if (intent.getAction().equals(SUB_CATEGORY_CHANGE_ACTION)) {
            mVpServiceProviderSelection.setCurrentItem(2, true);
            mSubcategoryId = intent.getStringExtra("id");
        } else if (intent.getAction().equals(SERVICE_PROVIDER_SELECTED_ACTION)) {
            String selectedServiceProviderId = intent.getStringExtra("id");
            initProgress();
            initErrorObserver();
            mViewModel.saveData(mCategoryId, mSubcategoryId, selectedServiceProviderId);
        }
    }
}
