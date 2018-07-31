package com.twixttechnologies.tjss.view.fragment.user.history;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.PathInfo;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.viewmodel.TimeLineHistoryDetailViewModel;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 09-10-17.
 */

public class FragmentTimeLineHistoryDetail extends BaseFragment implements OnMapReadyCallback {

    public static final String TAG = "timeLineDetails";
    @BindView(R.id.lblTimeLineDetailSource)
    AppCompatTextView mLblTimeLineDetailSource;
    @BindView(R.id.lblTimeLineDetailDestination)
    AppCompatTextView mLblTimeLineDetailDestination;
    @BindView(R.id.lblTimeLineDetailSourceTime)
    AppCompatTextView mLblTimeLineDetailSourceTime;
    @BindView(R.id.lblTimeLineDetailDestinationTime)
    AppCompatTextView mLblTimeLineDetailDestinationTime;
    Unbinder unbinder;

    private SupportMapFragment mMap;

    private String mPathId;
    private String mUserId;


    private GoogleMap mGoogleMap;
    private final Observer<List<PathInfo>> mPathDataObserver
            = new Observer<List<PathInfo>>() {
        @Override
        public void onChanged(@Nullable List<PathInfo> pathInfoData) {
            if (pathInfoData == null || pathInfoData.size() <= 0) return;
            if (mGoogleMap == null) return;

            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();

            PolylineOptions polyLineOptions
                    = new PolylineOptions()
                    .clickable(false)
                    .color(Color.BLACK)
                    .width(5);
            ArrayList<LatLng> paths = new ArrayList<>(pathInfoData.size());
            for (PathInfo pathInfo :
                    pathInfoData) {
                LatLng latLng = new LatLng(pathInfo.latitude, pathInfo.longitude);
                paths.add(latLng);
                polyLineOptions.add(latLng);
                boundsBuilder.include(latLng);
            }
            mGoogleMap.addPolyline(polyLineOptions);

            if (paths.size() <= 0) return;

            mGoogleMap.addMarker(new MarkerOptions()
                    .position(paths.get(0))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_source_18)));

            mGoogleMap.addMarker(new MarkerOptions()
                    .position(paths.get(paths.size() - 1)))
                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_destination_18));

            LatLngBounds bounds = boundsBuilder.build();
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 250));

            Activity activity = getActivity();
            if (activity == null) return;
            Geocoder geocoder = new Geocoder(activity);
            try {

                DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-mm-dd HH:mm:ss");

                List<Address> addresses = geocoder.getFromLocation(paths.get(0).latitude, paths.get(0).longitude, 1);
                if (addresses != null && addresses.size() > 0) {
                    mLblTimeLineDetailSource.setText(addresses.get(0).getLocality());

                    LocalTime time = LocalTime.parse(pathInfoData.get(0).createdAt, dtf);
                    mLblTimeLineDetailSourceTime.setText(time.toString("h:mm:a"));
                }

                addresses = geocoder.getFromLocation(paths.get(paths.size() - 1).latitude,
                        paths.get(paths.size() - 1).longitude, 1);
                if (addresses != null && addresses.size() > 0) {
                    mLblTimeLineDetailDestination.setText(addresses.get(0).getLocality());
                    LocalTime time = LocalTime.parse(pathInfoData.get(pathInfoData.size() - 1).createdAt, dtf);
                    mLblTimeLineDetailDestinationTime.setText(time.toString("h:mm.a"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private TimeLineHistoryDetailViewModel mViewModel;

    public static FragmentTimeLineHistoryDetail newInstance(String id, String userId) {
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("userId",userId);
        FragmentTimeLineHistoryDetail fragment = new FragmentTimeLineHistoryDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        mPathId = getArguments().getString("id");
        mUserId = getArguments().getString("userId");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_line_item_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new TimeLineHistoryDetailViewModel.TimeLineHistoryDetailViewModelFactory(getActivity().getApplication()))
                .get(TimeLineHistoryDetailViewModel.class);

        mViewModel.getPathData().observe(this, mPathDataObserver);

        mMap = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mMap != null) mMap.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mViewModel.getPath(mPathId,mUserId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
