package com.twixttechnologies.tjss.view.fragment.incident;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.Incident;
import com.twixttechnologies.tjss.model.network.response.IncidentDetail;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.adapter.listadapter.IncidentLogAdapter;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.viewmodel.IncidentDetailViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 31-10-17.
 */

public class FragmentIncidentDetails extends BaseFragment implements OnMapReadyCallback {

    public static final String TAG = "incidentDetails";

    public static final String INCIDENT_DETAIL = "detail";
    @BindView(R.id.btnIncidentType)
    AppCompatButton mBtnIncidentType;
    @BindView(R.id.lblIncidentHeading)
    AppCompatTextView mLblIncidentHeading;
    @BindView(R.id.lblIncidentTimeAndDate)
    AppCompatTextView mLblIncidentTimeAndDate;
    @BindView(R.id.lblIncidentDescription)
    AppCompatTextView mLblIncidentDescription;
    @BindView(R.id.lstIncidentLog)
    RecyclerView mLstIncidentLog;
    Unbinder unbinder;
    private GoogleMap mGoogleMap;
    private final Observer<IncidentDetail> mDetailsObserver
            = new Observer<IncidentDetail>() {
        @Override
        public void onChanged(@Nullable IncidentDetail incidentDetail) {
            if (incidentDetail == null) {
                return;
            }

            mBtnIncidentType.setText(incidentDetail.incidentType);
            mLblIncidentHeading.setText(incidentDetail.incidentTitle);
            mLblIncidentDescription.setText(incidentDetail.incidentDescription);
            mLblIncidentTimeAndDate.setText(incidentDetail.incidentTime);

            if (incidentDetail.log != null && incidentDetail.log.size() > 0) {
                mLstIncidentLog.setAdapter(new IncidentLogAdapter(incidentDetail.log, getActivity()));
                mLstIncidentLog.setLayoutManager(new LinearLayoutManager(getActivity()));
                mLstIncidentLog.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            }

            LatLng latLng = new LatLng(incidentDetail.incidentLat, incidentDetail.incidentLong);
            bindMarker(latLng);

        }
    };
    private Incident mIncident;

    public static FragmentIncidentDetails newInstance(Incident incident) {
        Bundle args = new Bundle();
        args.putParcelable(INCIDENT_DETAIL, incident);
        FragmentIncidentDetails fragment = new FragmentIncidentDetails();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        mIncident = getArguments().getParcelable(INCIDENT_DETAIL);
    }

    private void bindMarker(LatLng latLng) {
        if (mGoogleMap == null) return;
        try {
            mGoogleMap.clear();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.draggable(false);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin));
            mGoogleMap.addMarker(markerOptions);
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
        } catch (Exception e) {
            M.log(e.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incident_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IncidentDetailViewModel viewModel = ViewModelProviders.of(this,
                new IncidentDetailViewModel.IncidentDetailViewModelFactory(getActivity().getApplication()))
                .get(IncidentDetailViewModel.class);

        viewModel.getIncidentDetailData().observe(this, mDetailsObserver);
        viewModel.getIncidentDetails(mIncident);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }
}
