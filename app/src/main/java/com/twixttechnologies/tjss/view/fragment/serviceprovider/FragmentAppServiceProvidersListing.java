package com.twixttechnologies.tjss.view.fragment.serviceprovider;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.AppServiceProvider;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.utils.PreferenceHelper;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.view.fragment.generalalerts.PermissionsDetailListDialog;
import com.twixttechnologies.tjss.view.fragment.serviceprovider.alert.ServiceProviderInfoWindow;
import com.twixttechnologies.tjss.view.viewutils.Permissions;
import com.twixttechnologies.tjss.viewmodel.AppServiceProviderViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 20/11/17.
 */

public class FragmentAppServiceProvidersListing extends BaseFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, ServiceProviderInfoWindow.ProviderActionListener, PermissionsDetailListDialog.PermissionsDialogCallback {

    public static final String TAG = "AppServiceProviderListing";
    Unbinder unbinder;


    private AppServiceProviderViewModel mViewModel;

    private Observer<List<AppServiceProvider>> mServiceProvidersObserver;

    private AppServiceProvider mSelectedProvider = null;


    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_service_provider_listing, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new AppServiceProviderViewModel.AppServiceProviderViewModelFactory(getActivity().getApplication()))
                .get(AppServiceProviderViewModel.class);

        SupportMapFragment map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);
        initErrorObserver();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("Service Providers");
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setMapToolbarEnabled(false);
        if (mServiceProvidersObserver == null) {
            mServiceProvidersObserver = new Observer<List<AppServiceProvider>>() {
                @Override
                public void onChanged(@Nullable List<AppServiceProvider> appServiceProviders) {
                    if (appServiceProviders == null || appServiceProviders.size() <= 0) return;

                    LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();

                    int i = 0;

                    for (AppServiceProvider a :
                            appServiceProviders) {
                        LatLng latLng = new LatLng(a.latitude, a.longitude);
                        googleMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin))
                                .draggable(false))
                                .setTag(i);
                        boundsBuilder.include(latLng);
                    }
                    ++i;
                    LatLngBounds bounds = boundsBuilder.build();
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 250));

                }
            };
            mViewModel.getAppServiceProvidersData().observe(this, mServiceProvidersObserver);
            googleMap.setOnMarkerClickListener(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        List<AppServiceProvider> providers = mViewModel.getAppServiceProvidersData().getValue();
        if (providers == null || providers.size() <= 0) return false;
        int index = (int) marker.getTag();
        AppServiceProvider provider = providers.get(index);

        ServiceProviderInfoWindow dialog = ServiceProviderInfoWindow.newInstance(provider);
        dialog.setListener(this);
        dialog.setCancelable(true);
        dialog.show(getChildFragmentManager(), "Share location");
        return true;
    }

    @Override
    public void onShare(AppServiceProvider provider) {
        Double latitude = provider.latitude;
        Double longitude = provider.longitude;

        String uri = "http://maps.google.com/maps?saddr=" + latitude + "," + longitude;
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String name = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_FIRST_NAME, "");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, name + " has shared his service provider location with you");
        shareIntent.putExtra(Intent.EXTRA_TEXT, uri);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    @Override
    public void onCall(AppServiceProvider provider) {
        mSelectedProvider = provider;
        if (!havePermission(Permissions.CALL_PHONE)) {
            if (shouldShowPermissionReationale(Permissions.CALL_PHONE) != null) {
                ArrayList<String> permissionDetails = new ArrayList<>(1);
                permissionDetails.add("Request to make call-TJSS Requires your permission to make a call to the service provider");
                PermissionsDetailListDialog dialog = PermissionsDetailListDialog.newInstance(permissionDetails);
                dialog.setCallback(this);
                dialog.show(getChildFragmentManager(),"permissions");
            } else {
                onPermissionGranted();
            }
        } else {
            makeCall();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != Permissions.CALL_PERMISSION_REQUEST_CODE) return;
        if (grantResults[0]!= PackageManager.PERMISSION_GRANTED) return;
        makeCall();
    }

    @Override
    public void onPermissionGranted() {
        requestPermission(Permissions.CALL_PERMISSION_REQUEST_CODE,Permissions.CALL_PHONE);
    }

    private void makeCall() {
        if (mSelectedProvider == null) {
            M.log("Selected service provider is null");
            return;
        }

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + mSelectedProvider.phone));
        startActivity(intent);
        mSelectedProvider = null;
    }

}
