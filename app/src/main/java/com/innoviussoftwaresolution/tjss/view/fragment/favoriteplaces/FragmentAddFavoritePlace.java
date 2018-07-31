package com.innoviussoftwaresolution.tjss.view.fragment.favoriteplaces;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bugsnag.android.Bugsnag;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.internal.AddressPrediction;
import com.innoviussoftwaresolution.tjss.model.network.response.AddFavPlaceResponse;
import com.innoviussoftwaresolution.tjss.utils.LocationUtil;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.view.adapter.spinneradapter.PredictionsAdapter;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment;
import com.innoviussoftwaresolution.tjss.view.viewutils.Permissions;
import com.innoviussoftwaresolution.tjss.view.viewutils.TextChangeAdapter;
import com.innoviussoftwaresolution.tjss.viewmodel.FavoritePlacesViewModel;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 20-07-17.
 */

public class FragmentAddFavoritePlace extends BaseFragment
        implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnCameraIdleListener {


    @BindView(R.id.txtFavPlaceName)
    TextInputEditText mTxtFavPlaceName;

    @BindView(R.id.txtFavPlaceAddress)
    AutoCompleteTextView mTxtFavPlaceAddress;

    Unbinder unbinder;

    @BindView(R.id.lblRadius)
    AppCompatTextView mLblRadius;

    @BindView(R.id.skRadius)
    AppCompatSeekBar mSkRadius;

    GoogleMap mGoogleMap;

    FavoritePlacesViewModel mViewModel;

    LocationUtil mLocationUtil;

    Marker mMarker = null;

    GoogleApiClient mGoogleApiClient;

    PredictionsAdapter mPredictionsAdapter;

    Geocoder mGeoCoder;

    AdapterView.OnItemClickListener mPredictionSelectedListener;

    AutocompletePredictionBuffer mPredictionsBuffer;

    AddressPrediction mSelectedAddress;

    Circle mCircle;
    CircleOptions mCircleOptions;

    boolean mIsFromPrediction = true;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        setHasOptionsMenu(true);
        mLocationUtil = new LocationUtil(getActivity().getApplicationContext());
        Bugsnag.init(getContext());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.findItem(R.id.mnuSave);
        if (menuItem == null)
            inflater.inflate(R.menu.fav_places_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            String placeName = mTxtFavPlaceName.getText().toString().trim();

            Toast.makeText(getActivity(), placeName, Toast.LENGTH_LONG).show();
            if (placeName == null || placeName.equals("")) {
                showErrorSnackBar("Please select a new name for your new Favorite place",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mTxtFavPlaceName.requestFocus();
                            }
                        });
            } else if (TextUtils.isEmpty(mTxtFavPlaceAddress.getText().toString())) {
                showErrorSnackBar("Please give an address for your new Favorite place",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mTxtFavPlaceAddress.requestFocus();
                            }
                        });
            } else if (mMarker == null) {
                M.showSnackBar(mTxtFavPlaceAddress, "Please select favorite place on the map");
            } else {
                mViewModel.addFavPlace(mMarker.getPosition(), mTxtFavPlaceName.getText().toString(),
                        mTxtFavPlaceAddress.getText().toString(), mSelectedAddress.getPlaceId(), mSkRadius.getProgress());
                M.showSnackBar(mTxtFavPlaceName, "Adding favorite place...");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    void showErrorSnackBar(String message, View.OnClickListener clickListener) {
        M.showSnackBar(mTxtFavPlaceAddress, message,
                "OK", clickListener, Snackbar.LENGTH_LONG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_favorite_place, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mMap = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMap.getMapAsync(this);
        mViewModel = ViewModelProviders.of(this,
                new FavoritePlacesViewModel.FavouritePlacesViewModelFactory(getActivity().getApplication()))
                .get(FavoritePlacesViewModel.class);
        mViewModel.getAddFavPlaceResponse().observe(this, new Observer<AddFavPlaceResponse>() {
            @Override
            public void onChanged(@Nullable AddFavPlaceResponse addFavPlaceResponse) {
                if (addFavPlaceResponse == null) {
                    M.showAlert(getActivity(), "Add Favorite Place", "An error occurred, " +
                            "please try again later", "OK", null, null, null, false);
                    return;
                }
                if (addFavPlaceResponse.status.toLowerCase().equals("success")) {
                    M.showSnackBar(mTxtFavPlaceName, "Success");
                    getActivity().finish();
                } else {
                    M.showSnackBar(mTxtFavPlaceAddress, addFavPlaceResponse.status, "OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //hack
                        }
                    }, Snackbar.LENGTH_INDEFINITE);
                }
            }
        });

        mTxtFavPlaceAddress.addTextChangedListener(new TextChangeAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(mTxtFavPlaceAddress.getText());
            }
        });

        mSkRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 250) return;
                addCircle(progress);
                mLblRadius.setText(MessageFormat.format("Radius ({0} mts)", progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() < 250) {
                    seekBar.setProgress(250);
                }
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        initGoogleApiClient();
        mGoogleMap = googleMap;
        mGoogleMap.setOnMapLongClickListener(this);

        mGoogleMap.setOnCameraIdleListener(this);

        ArrayList<String> permissionsRequired = Permissions.havePermissionFor(getActivity(),
                Permissions.FINE_LOCATION, Permissions.COARSE_LOCATION);
        if (permissionsRequired == null) {
            accessLocation();
        } else {
            M.showSnackBar(mTxtFavPlaceAddress, "Unable to get your location");

        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mGoogleMap.clear();
        mMarker = mGoogleMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 1.0f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin))
                .position(latLng));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void accessLocation() {
        boolean isFineLocationEnabled = mLocationUtil.isFineLocationEnabled(getActivity());
        Location location = null;
        if (Permissions.havePermissionFor(getActivity(), Permissions.FINE_LOCATION) == null)
            if (isFineLocationEnabled) {
                //no need to access coarse location
                location = mLocationUtil.getFineLocation();
            } else {
                if (Permissions.havePermissionFor(getActivity(), Permissions.COARSE_LOCATION) == null) {
                    boolean isCoarseLocationEnabled = mLocationUtil.isCoarseLocationEnabled(getActivity());
                    if (isCoarseLocationEnabled)
                        location = mLocationUtil.getCoarseLocation();
                }
            }

        if (location == null) {
            Toast t = Toast.makeText(getActivity(), "Unable to get your location data", Toast.LENGTH_SHORT);
            t.setGravity(Gravity.TOP, 0, 200);
            t.show();
            return;
        }

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        if (mGoogleMap == null) return;
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
        mLocationUtil.stopUsingGPS();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPredictionsBuffer != null) {
            mPredictionsBuffer.release();
            mPredictionsBuffer = null;
        }
    }

    private void initGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(getActivity(), this)
                .addConnectionCallbacks(this)
                .build();
    }

    private void search(CharSequence searchString) {
        if (searchString.length() <= 1) return;
        if (mGoogleApiClient == null) return;


        PendingResult<AutocompletePredictionBuffer> results
                = Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, searchString.toString(),
                new LatLngBounds(new LatLng(-0, 0), new LatLng(0, 0)), null);


        results.setResultCallback(new ResultCallbacks<AutocompletePredictionBuffer>() {
            @Override
            public void onSuccess(@NonNull AutocompletePredictionBuffer autocompletePredictionBuffer) {
                mPredictionsBuffer = autocompletePredictionBuffer;
                final Status status = autocompletePredictionBuffer.getStatus();
                if (!status.isSuccess()) {
                    M.log("Autocomplete address failure");
                } else {
                    M.log("Query completed, Count : " + autocompletePredictionBuffer.getCount());
                    Iterator<AutocompletePrediction> iterator = autocompletePredictionBuffer.iterator();

                    ArrayList<AddressPrediction> resultList = new ArrayList<>(autocompletePredictionBuffer.getCount());
                    while (iterator.hasNext()) {
                        AutocompletePrediction autocompletePrediction = iterator.next();
                        resultList.add(new AddressPrediction(autocompletePrediction));
                    }
                    resultList.add(null);//for the last item (powered by google)


                    if (mPredictionsAdapter == null) {
                        mPredictionsAdapter = new PredictionsAdapter(getActivity(), R.layout.extra_simple_spinner_item,
                                R.id.lblSpinnerLabel, resultList);
                        mPredictionSelectedListener = new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                mIsFromPrediction = true;
                                try {
                                    //last position means the user selected the powered by google image
                                    if (position == mTxtFavPlaceAddress.getAdapter().getCount())
                                        return;
                                    if (mGeoCoder == null) mGeoCoder = new Geocoder(getActivity());
                                    mSelectedAddress = (AddressPrediction) parent.getItemAtPosition(position);
                                    if (mSelectedAddress == null) return;
                                    String name = mSelectedAddress.getFullText().toString();
                                    List<Address> addresses = mGeoCoder.getFromLocationName(name, 5);
                                    if (addresses.size() <= 0) return;

                                    Address address = addresses.get(0);

                                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                                    if (mMarker == null) {
                                        mMarker = mGoogleMap.addMarker(new MarkerOptions()
                                                .anchor(0.5f, 1.0f)
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin))
                                                .position(latLng));

                                    } else {
                                        mMarker.setPosition(latLng);
                                        mTxtFavPlaceAddress.clearFocus();
                                    }
                                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                                    addCircle(mMarker.getPosition());
                                } catch (Exception e) {
                                    Bugsnag.notify(new RuntimeException(e));
                                    M.log(e.getMessage());
                                } finally {
                                    if (mPredictionsBuffer != null) {
                                        mPredictionsBuffer.release();
                                    }
                                }
                            }
                        };
                    } else {
                        mPredictionsAdapter.clear();
                        mPredictionsAdapter.addAll(resultList);
                    }
                    mTxtFavPlaceAddress.setAdapter(mPredictionsAdapter);
                    mPredictionsAdapter.notifyDataSetChanged();
                    mTxtFavPlaceAddress.setOnItemClickListener(mPredictionSelectedListener);
                }
            }

            @Override
            public void onFailure(@NonNull Status status) {
                    Toast.makeText(getActivity(),status.toString(),Toast.LENGTH_LONG).show();
            }
        }, 60, TimeUnit.SECONDS);
    }

    void addCircle(LatLng latLng) {
        if (mCircle != null) {
            mCircle.remove();
        }

        int circleColor = ContextCompat.getColor(getActivity(), R.color.colorTransparentAccent);
        int strokeColor = ContextCompat.getColor(getActivity(), R.color.colorAccent);
        double radius = mSkRadius.getProgress();

        if (mCircleOptions == null) {
            mCircleOptions = new CircleOptions()
                    .fillColor(circleColor)
                    .strokeColor(strokeColor)
                    .strokeWidth(2);

            mCircleOptions.radius(radius);
        }
        try {
            mCircleOptions.center(latLng);
            mCircle = mGoogleMap.addCircle(mCircleOptions);
        } catch (Exception e) {
            Bugsnag.notify(new RuntimeException(e));
            M.log(e.getMessage());
        }
    }

    void addCircle(double radius) {
        if (mCircle != null) {
            mCircle.remove();
        }
        int circleColor = ContextCompat.getColor(getActivity(), R.color.colorTransparentAccent);
        int strokeColor = ContextCompat.getColor(getActivity(), R.color.colorAccent);

        if (mCircleOptions == null) {
            mCircleOptions = new CircleOptions()
                    .fillColor(circleColor)
                    .strokeColor(strokeColor)
                    .strokeWidth(2);
        }
        mCircleOptions.radius(radius);
        try {
            CameraPosition cameraPosition = mGoogleMap.getCameraPosition();
            mCircleOptions.center(mMarker == null ? cameraPosition.target : mMarker.getPosition());
            mCircle = mGoogleMap.addCircle(mCircleOptions);
        } catch (Exception e) {
            Bugsnag.notify(new RuntimeException(e));
            M.log(e.getMessage());
        }
    }

    @Override
    public void onCameraIdle() {
        if (mMarker == null) return;
        CameraPosition cameraPosition = mGoogleMap.getCameraPosition();
        addCircle(cameraPosition.target);
        mMarker.setPosition(cameraPosition.target);
        if (mGeoCoder == null) {
            mGeoCoder = new Geocoder(getActivity());
        }
        LatLng position = mMarker.getPosition();

        try {
            List<Address> addresses = mGeoCoder.getFromLocation(position.latitude, position.longitude, 1);
            if (addresses.size() <= 0) return;
            Address address = addresses.get(0);
            if (mIsFromPrediction) {
                mIsFromPrediction = false;
            } else {
                if (mTxtFavPlaceAddress != null) {
                    mTxtFavPlaceAddress.setText(address.getLocality() + "," + address.getCountryName());
                }
            }
        } catch (IOException e) {
            Bugsnag.notify(new RuntimeException(e));
            e.printStackTrace();
        }
    }

}
