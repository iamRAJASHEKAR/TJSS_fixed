package com.twixttechnologies.tjss.view.fragment.serviceprovider;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
//import com.twixttechnologies.tjss.R;
import com.twixttechnologies.tjss.model.network.response.AppServiceProvider;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.utils.LocationUtil;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.activity.ServiceProviderSelectionActivity;
import com.twixttechnologies.tjss.view.adapter.listadapter.AppServiceProviderAdapter;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.view.fragment.helpalert.alert.CountDownAlert;
import com.twixttechnologies.tjss.view.fragment.helpalert.alert.InitHelpAlertDialog;
import com.twixttechnologies.tjss.viewmodel.AppServiceProvidersViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 25-09-17.
 */

public class FragmentServiceProviders extends BaseFragment implements InitHelpAlertDialog.OnStatusListener, CountDownAlert.OnCountDownFinishedListener, AppServiceProviderAdapter.onDeleteListener {

    public static final String TAG = "serviceProviders";
    @BindView(R.id.lstServiceProviders)
    RecyclerView mLstServiceProviders;
    @BindView(R.id.btnServiceProvidersSendHelpAlert)
    AppCompatButton mBthServiceProvidersSendHelpAlert;
    Unbinder unbinder;
    @BindView(R.id.lblTitle)
    AppCompatTextView mLblTitle;

    private AppServiceProvidersViewModel mViewModel;

    private Observer<List<AppServiceProvider>> mObserver = new Observer<List<AppServiceProvider>>() {
        @Override
        public void onChanged(@Nullable List<AppServiceProvider> appServiceProviders) {
            if (appServiceProviders == null || appServiceProviders.size() <= 0) {
                M.showAlert(getActivity(), "Service Providers", "You haven't selected any service providers yet, Please select a service provider first",
                        "SELECT PROVIDER", "NO,THANKS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(getActivity(), ServiceProviderSelectionActivity.class));
                            }
                        }, null, false);


                return;
            }
            AppServiceProviderAdapter adapter = new AppServiceProviderAdapter(appServiceProviders);
            adapter.setOnDeleteListener(FragmentServiceProviders.this);
            mLstServiceProviders.setAdapter(adapter);
            mLstServiceProviders.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    };

    private Observer<StatusMessage> mStatusMessageObserver
            = new Observer<StatusMessage>() {
        @Override
        public void onChanged(@Nullable StatusMessage statusMessage) {
            dismissDialog();
            if (statusMessage == null || statusMessage.status == null || statusMessage.status.equals("")) {
                M.showToast(getActivity(), "An Error Occurred");
            } else {
                if (statusMessage.status.toLowerCase().contains("success")) {
                    ((AppServiceProviderAdapter) mLstServiceProviders.getAdapter()).remove(selectedIndex);
                }
            }
        }
    };

    private Observer<String> mErrorObserver
            = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            if (s == null || s.equals("")) return;
            M.showAlert(getActivity(), "TJSS", s, "OPEN SETTINGS", "NO,THANKS", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new LocationUtil(getActivity()).showSettingsAlert();
                }
            }, null, false);
        }
    };


    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_providers, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new AppServiceProvidersViewModel.AppServiceProvidersViewModelFactory(getActivity().getApplication()))
                .get(AppServiceProvidersViewModel.class);
        mViewModel.getServiceProvidersData().observe(this, mObserver);
        mViewModel.getError().observe(this, mErrorObserver);
        mViewModel.getStatusMessageData().observe(this, mStatusMessageObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnServiceProvidersSendHelpAlert)
    public void onViewClicked() {
        InitHelpAlertDialog dialog = new InitHelpAlertDialog();
        dialog.setListener(this);
        dialog.setCancelable(true);
        dialog.show(getChildFragmentManager(), "HelpAlert");
    }

    @Override
    public void onStatus(boolean success) {
        if (!success) {
            M.showToast(getActivity(), "Invalid data");
        } else {
            CountDownAlert dialog = new CountDownAlert();
            dialog.setCancelable(false);
            dialog.setListener(this);
            dialog.show(getChildFragmentManager(), "Counter");
        }
    }

    @Override
    public void onFinish() {
        mViewModel.sendHelpAlert();
    }

    private int selectedIndex = -1;

    @Override
    public void onDelete(AppServiceProvider provider, int index) {
        selectedIndex = index;
        initProgress();
        initErrorObserver();
        mViewModel.delete(provider);
    }
}
