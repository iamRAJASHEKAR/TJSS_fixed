package com.innoviussoftwaresolution.tjss.view.fragment.payment;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.CoinBalance;
import com.innoviussoftwaresolution.tjss.model.network.response.InAppPurchaseServiceItem;
import com.innoviussoftwaresolution.tjss.model.network.response.StatusMessage;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.view.activity.PaymentActivity;
import com.innoviussoftwaresolution.tjss.view.adapter.listadapter.InAppServicesAdapter;
import com.innoviussoftwaresolution.tjss.view.fragment.generalalerts.PermissionsDetailListDialog;
import com.innoviussoftwaresolution.tjss.view.viewutils.Permissions;
import com.innoviussoftwaresolution.tjss.viewmodel.InAppServicesViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 09-10-17.
 */

public class FragmentPurchaseService extends FragmentPurchaseBase implements InAppServicesAdapter.OnInAppServicesSelectedListener {

    public static final String TAG = "purchaseService";
    @BindView(R.id.lstServices)
    RecyclerView mLstServices;
    private final Observer<List<InAppPurchaseServiceItem>> mServiceItemsObserver
            = new Observer<List<InAppPurchaseServiceItem>>() {
        @Override
        public void onChanged(@Nullable List<InAppPurchaseServiceItem> inAppPurchaseServiceItems) {
            if (inAppPurchaseServiceItems == null
                    || inAppPurchaseServiceItems.size() <= 0) {
                M.showToast(getActivity(), "You have no coins and amount yet");
                return;
            }

            mLstServices.setAdapter(new InAppServicesAdapter(inAppPurchaseServiceItems, getActivity(),
                    FragmentPurchaseService.this));
            mLstServices.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    };
    Unbinder unbinder;
    private Observer<StatusMessage> mTransferStatusObserver;
    private Observer<CoinBalance> mCoinBalanceObserver;
    private String mContactNumber = null;
    private InAppServicesViewModel mViewModel;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        Bugsnag.init(getContext());

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase_service, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new InAppServicesViewModel.InAppServicesViewModelFactory(getActivity().getApplication()))
                .get(InAppServicesViewModel.class);
        mViewModel.getServicesData().observe(this, mServiceItemsObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSelected(final InAppPurchaseServiceItem item, boolean call) {
        final Activity activity = getActivity();
        if (activity == null) return;

        if (call) {
            String phone = item.phone;
            mContactNumber = phone;
            if (phone == null || phone.equals("")) {
                M.showAlert(getActivity(), "Call Provider", "Sorry, provider contact number not available", "OK", null, null, null, false);
            } else {
                if (!havePermission(Permissions.CALL_PHONE)) {
                    if (shouldShowPermissionReationale(Permissions.CALL_PHONE) != null) {
                        ArrayList<String> permissionDetails = new ArrayList<>(1);
                        permissionDetails.add("Request for permission-TJSS will need your permission to make a call to the service provider");
                        showPermissionDetails(permissionDetails, new PermissionsDetailListDialog.PermissionsDialogCallback() {
                            @Override
                            public void onPermissionGranted() {
                                requestPermission(Permissions.CALL_PERMISSION_REQUEST_CODE, Permissions.CALL_PHONE);
                            }
                        });
                    } else {
                        requestPermission(Permissions.CALL_PERMISSION_REQUEST_CODE, Permissions.CALL_PHONE);
                    }
                } else {
                    call();
                }
            }
        } else {
            CoinBalance coinBalance = mViewModel.getCoinBalanceData().getValue();
            if (coinBalance == null) {
                if (mCoinBalanceObserver == null) {
                    mCoinBalanceObserver = new Observer<CoinBalance>() {
                        @Override
                        public void onChanged(@Nullable CoinBalance coinBalance) {
                            checkAndProceed(item, coinBalance);
                        }
                    };
                    mViewModel.getCoinBalanceData().observe(this, mCoinBalanceObserver);
                }
                initProgress();
                mViewModel.getCoinBalance();
                return;
            }
            checkAndProceed(item, coinBalance);
        }
    }

    private void checkAndProceed(final InAppPurchaseServiceItem item, final CoinBalance coinBalance) {
        int availableBalance = 0;
        try {
            availableBalance = Integer.parseInt(coinBalance.coinBalance);
        } catch (Exception e) {
            Bugsnag.notify(new RuntimeException(e));
            availableBalance = 0;
        }
        int coinsRequired = Integer.parseInt(item.points);
        int required = coinsRequired - availableBalance;
        dismissDialog();
        if (required > 0) {
            M.showAlert(getActivity(), "Purchase Service", "You need " + required + " coin(s) more to purchase this service", "PURCHASE", "CANCEL",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            purchase(item, getActivity());
                        }
                    }, null, false);
        } else {
            M.showAlert(getActivity(), "Purchase service", "Are you sure you want to purchase " + item.name, "PURCHASE", "NO,THANKS",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mTransferStatusObserver == null) {
                                mTransferStatusObserver = new Observer<StatusMessage>() {
                                    @Override
                                    public void onChanged(@Nullable StatusMessage statusMessage) {
                                        dismissDialog();
                                        if (statusMessage == null || statusMessage.status == null || statusMessage.status.equals("") || !statusMessage.status.toLowerCase().contains("success")) {
                                            M.showAlert(getActivity(), "Purchase service", "An error occurred, Please try again later", "OK", null, null, null, false);
                                        } else {
                                            M.showAlert(getActivity(), "Purchase service", "Purchase completed successfully", "OK", null, new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    getActivity().finish();
                                                }
                                            }, null, false);
                                        }
                                    }
                                };
                            }
                            mViewModel.getTransferStatusData().observe(FragmentPurchaseService.this, mTransferStatusObserver);
                            initProgress();
                            initErrorObserver();
                            mViewModel.transfer(item, coinBalance.coinBalance);
                        }
                    }, null, false);
        }
    }

    private void purchase(InAppPurchaseServiceItem item, Activity activity) {
        Intent purchaseIntent = new Intent(activity, PaymentActivity.class);
        purchaseIntent.putExtra(PaymentActivity.PAYMENT_TYPE, PaymentActivity.PAYMENT_TYPE_COINS);
        purchaseIntent.putExtra(PaymentActivity.NUMBER_OF_COINS, Integer.valueOf(item.amount));
        startActivity(purchaseIntent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Permissions.CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                call();
            } else {
                M.showToast(getActivity(), "Unable to make call without permission, Permission denied");
                mContactNumber = null;
            }
        }
    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + mContactNumber));
        startActivity(intent);
        mContactNumber = null;
    }

    @Override
    public void makeBankPayment() {

    }

    @Override
    public void updateStatus(HashMap<String, String> data) {

    }
}
