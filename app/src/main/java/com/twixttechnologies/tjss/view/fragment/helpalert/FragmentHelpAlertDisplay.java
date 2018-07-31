package com.twixttechnologies.tjss.view.fragment.helpalert;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;

import java.io.IOException;
import java.util.List;

/**
 * @author Sony Raj on 10/11/17.
 */

public class FragmentHelpAlertDisplay extends BaseFragment implements OnMapReadyCallback {

    public static final String TAG = "HelpAlertDisplay";
    public static final String LAT = "lat";
    public static final String LNG = "lng";

    private SupportMapFragment mMap;

    private String lat;
    private String lng;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        lat = getArguments().getString(LAT);
        lng = getArguments().getString(LNG);
    }

    public static FragmentHelpAlertDisplay newInstance(String lat, String lng) {

        Bundle args = new Bundle();
        args.putString(LAT, lat);
        args.putString(LNG, lng);
        FragmentHelpAlertDisplay fragment = new FragmentHelpAlertDisplay();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help_alert_display,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMap = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMap.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));

        Geocoder geocoder = new Geocoder(getActivity());
        String name = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                if (address != null) {
                    name = address.getLocality();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin))
                .position(latLng))
                .setTitle(name);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));

    }
}
