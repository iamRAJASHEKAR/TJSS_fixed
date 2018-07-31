package com.twixttechnologies.tjss.view.fragment.home;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.TjssApplication;
import com.twixttechnologies.tjss.model.internal.ClusterItemBase;
import com.twixttechnologies.tjss.model.network.response.CircleSwitchResponse;
import com.twixttechnologies.tjss.model.network.response.FavoritePlace;
import com.twixttechnologies.tjss.model.network.response.Incident;
import com.twixttechnologies.tjss.model.network.response.SafetyCircle;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;
import com.twixttechnologies.tjss.model.network.response.ServiceProvider;
import com.twixttechnologies.tjss.model.network.response.UnreadMessage;
import com.twixttechnologies.tjss.model.network.response.nearby.Geometry;
import com.twixttechnologies.tjss.model.network.response.nearby.NearbySearchResponse;
import com.twixttechnologies.tjss.model.network.response.nearby.Result;
import com.twixttechnologies.tjss.service.ChatConstants;
import com.twixttechnologies.tjss.service.ChatListenerService;
import com.twixttechnologies.tjss.utils.LocationUtil;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.utils.PreferenceHelper;
import com.twixttechnologies.tjss.utils.extensions.BitmapToPinExtension;
import com.twixttechnologies.tjss.view.activity.AppServiceProviderListingActivity;
import com.twixttechnologies.tjss.view.activity.ChatDetailActivity;
import com.twixttechnologies.tjss.view.activity.CoinPurchaseActivity;
import com.twixttechnologies.tjss.view.activity.CreateCircleActivity;
import com.twixttechnologies.tjss.view.activity.FaqActivity;
import com.twixttechnologies.tjss.view.activity.FavPlacesActivity;
import com.twixttechnologies.tjss.view.activity.HelpAlertActivity;
import com.twixttechnologies.tjss.view.activity.HelpAlertHistoryActivity;
import com.twixttechnologies.tjss.view.activity.IncidentsListingActivity;
import com.twixttechnologies.tjss.view.activity.JoinCircleActivity;
import com.twixttechnologies.tjss.view.activity.LocationSharingActivity;
import com.twixttechnologies.tjss.view.activity.MessagesActivity;
import com.twixttechnologies.tjss.view.activity.PlansListingActivity;
import com.twixttechnologies.tjss.view.activity.PurchaseServiceActivity;
import com.twixttechnologies.tjss.view.activity.SafetyCircleMembersListingActivity;
import com.twixttechnologies.tjss.view.activity.SafetyCircleMembersListingForCheckInActivity;
import com.twixttechnologies.tjss.view.activity.SafetyTipsListingActivity;
import com.twixttechnologies.tjss.view.activity.SecurityQuestionsActivity;
import com.twixttechnologies.tjss.view.activity.ServiceProvidersListingActivity;
import com.twixttechnologies.tjss.view.activity.SettingsActivity;
import com.twixttechnologies.tjss.view.activity.UserProfileActivity;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.view.fragment.generalalerts.PermissionsDetailListDialog;
import com.twixttechnologies.tjss.view.fragment.generalalerts.ProgressDialog;
import com.twixttechnologies.tjss.view.fragment.message.FragmentChatDetail;
import com.twixttechnologies.tjss.view.viewutils.Permissions;
import com.twixttechnologies.tjss.viewmodel.UserHomeViewModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Sony Raj on 15-07-17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class FragmentHome extends BaseFragment implements OnMapReadyCallback,
        PermissionsDetailListDialog.PermissionsDialogCallback,
        GoogleMap.InfoWindowAdapter,
        SafetyCircleMarkerInfoWindowDialog.OnMarkerOptionSelectedListener,
        View.OnClickListener,
        ClusterManager.OnClusterClickListener<ClusterItemBase>,
        ClusterManager.OnClusterItemClickListener<ClusterItemBase> {

    public static final String TAG = "UserHome";
    private static final int SAFETY_CIRCLE = 168;
    private static final int FAV_PLACES = 406;
    private static final int INCIDENTS = 943;
    private final Handler mHandler = new Handler();

    @BindView(R.id.homeTabs)
    TabLayout mHomeTabs;

    @BindView(R.id.homePullOutView)
    CardView mHomePullOutView;

    @BindView(R.id.imgDrawerProfileImage)
    CircleImageView mImgDrawerProfileImage;

    @BindView(R.id.lblDrawerUsername)
    AppCompatTextView mLblDrawerUsername;

    @BindView(R.id.lblDrawerEmail)
    AppCompatTextView mLblDrawerEmail;

    @BindView(R.id.lblSelectedPlanName)
    AppCompatTextView mLblSelectedPlanName;

    @BindView(R.id.lblDrawerPrimaryCircleName)
    AppCompatTextView mLblDrawerPrimaryCircleName;

    @BindView(R.id.btnExpandOrCollapseCircles)
    AppCompatImageButton mBtnExpandCircles;

    @BindView(R.id.btnDrawerCreateCircle)
    AppCompatButton mBtnDrawerCreateCircle;

    @BindView(R.id.btnDrawerJoinCircle)
    AppCompatButton mBtnDrawerJoinCircle;

    @BindView(R.id.btnDrawerMessages)
    AppCompatButton mBtnDrawerMessages;

    @BindView(R.id.btnDrawerHelpAlert)
    AppCompatButton mBtnDrawerHelpAlert;

    @BindView(R.id.btnDrawerPlans)
    AppCompatButton mBtnDrawerPlans;

    @BindView(R.id.btnDrawerFavoritePlaces)
    AppCompatButton mBtnDrawerFavoritePlaces;

    @BindView(R.id.btnDrawerLocationSharing)
    AppCompatButton mBtnDrawerLocationSharing;

    @BindView(R.id.btnDrawerCoins)
    AppCompatButton mBtnDrawerDrawerCoins;

    @BindView(R.id.btnDrawerSettings)
    AppCompatButton mBtnDrawerSettings;

    @BindView(R.id.btnDrawerSecurityQuestion)
    AppCompatButton mBtnDrawerSecurityQuestion;

    @BindView(R.id.btnDrawerCheckIn)
    AppCompatButton mBtnDrawerCheckIn;

    @BindView(R.id.btnDrawerHelpAlertHistory)
    AppCompatButton mBtnDrawerHelpAlertHistory;

    @BindView(R.id.btnDrawerFaqs)
    AppCompatButton mBtnDrawerFaqs;

    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.ltSafetyCirclesHolder)
    LinearLayout mLtSafetyCirclesHolder;

    @BindView(R.id.ltSafetyCirclesHolderMain)
    LinearLayout mLtSafetyCirclesHolderMain;

    @BindView(R.id.tglShowMembersOfSafetyCircle)
    SwitchCompat mTglShowMembersOfSafetyCircle;

    @BindView(R.id.ltShowMembersOfSafetyCircle)
    LinearLayout mLtShowMembersOfSafetyCircle;

    @BindView(R.id.btnDrawerSafetyTips)
    AppCompatButton mBtnDrawerSafetyTips;

    @BindView(R.id.lblViewAllSelectedTabList)
    AppCompatTextView mLblViewAllSelectedTabList;

    @BindView(R.id.drawerLeftMarginHack)
    View mDrawerLeftMarginHack;

    @BindView(R.id.imgPullArrow)
    AppCompatImageView mImgPullArrow;

    @BindView(R.id.btnTabPullOutSeparator)
    AppCompatImageButton mBtnTabPullOutSeparator;

    @BindView(R.id.lblUnreadMessageCount)
    AppCompatTextView mLblUnreadMessagesCount;

    @BindView(R.id.imgPullArrowServiceProviders)
    AppCompatImageView mImgPullArrowServiceProviders;

    @BindView(R.id.homePullOutViewServiceProviders)
    CardView mHomePullOutViewServiceProviders;

    private int mMaxZoom = 0;

    Unbinder unbinder;
    SupportMapFragment mMap;
    private ClusterManager<ClusterItemBase> mClusterManager;
    private TjssClusterRenderer mClusterRenderer;
    private final Observer<UnreadMessage> mUnreadMessageObserver
            = new Observer<UnreadMessage>() {
        @Override
        public void onChanged(@Nullable UnreadMessage unreadMessage) {
            try {
                if (unreadMessage == null || unreadMessage.unread == null || unreadMessage.unread.equals("") || unreadMessage.unread.equals("0")) {
                    mLblUnreadMessagesCount.setVisibility(View.GONE);
                } else {
                    mLblUnreadMessagesCount.setVisibility(View.VISIBLE);
                    mLblUnreadMessagesCount.setText(unreadMessage.unread);
                    if (TextUtils.isEmpty(mLblUnreadMessagesCount.getText())) {
                        M.showToast(getActivity(), "You have unread messages");
                        return;
                    }

                    String lastCount = mLblUnreadMessagesCount.getText().toString();
                    if (!lastCount.equals(unreadMessage.unread)) {
                        M.showToast(getActivity(), "You have unread messages");
                    }

                }
            } catch (Exception e) {
                M.log(e.getMessage());
            }
        }
    };

    //Google map
    boolean ignoreChange = false;
    private int PLACE_PICKER_REQUEST = 1;
    private LocationUtil mLocationUtil;
    private GoogleMap mGoogleMap;
    private UserHomeViewModel mViewModel;

    //progress and progress handlers
    private ProgressDialog mProgressDialog;
    private Runnable mDismissProgressRunnable;
    private Handler mProgressHandler;
    private int selectedTab = SAFETY_CIRCLE;
    private int mCurrentZoom = 0;
    private String mTempPhone = "";
    private boolean mIsExpanded = true;
    private boolean mClearMap = false;
    private Location mExtraMarkersLocation;
    private Observer<List<SafetyCircle>> mSafetyCircleListObserver
            = new Observer<List<SafetyCircle>>() {
        @Override
        public void onChanged(@Nullable List<SafetyCircle> safetyCircleList) {
            if (safetyCircleList == null || safetyCircleList.size() <= 0) return;
            if (mProgressDialog != null && mProgressDialog.isVisible()) mProgressDialog.dismiss();
            mLtSafetyCirclesHolder.removeAllViews();
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            String primaryId = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_PRIMARY_CIRCLE, "");
            for (SafetyCircle s : safetyCircleList) {
                if (s != null) {

                    if (s.circleId.equals(primaryId)) {
                        mLblDrawerPrimaryCircleName.setText(s.circleName);
                        setTitle(s.circleName);
                    }
                    if (inflater != null) {
                        AppCompatTextView view = (AppCompatTextView) inflater.inflate(R.layout.extra_safety_circle_item, mLtSafetyCirclesHolder, false);
                        if (view != null) {
                            view.setText(s.circleName);
                            view.setTag(s.circleId + "~~" + s.circleName);
                            view.setOnClickListener(FragmentHome.this);
                        }
                        mLtSafetyCirclesHolder.removeView(view);
                        mLtSafetyCirclesHolder.addView(view);
                    }
                }
            }
            //mViewModel.getActiveCircleMembers();
        }
    };
    private Observer<List<FavoritePlace>> mFavoritePlacesObserver
            = new Observer<List<FavoritePlace>>() {
        @Override
        public void onChanged(@Nullable List<FavoritePlace> favoritePlaces) {
            if (mProgressDialog != null && mProgressDialog.isVisible()) mProgressDialog.dismiss();
            if (favoritePlaces == null) return;
            if (mGoogleMap == null) return;
            try {
                bindFavPlaces(favoritePlaces);
                getExtraMarkers();
            } catch (Exception e) {
                M.log(e.getMessage());
            }

        }
    };
    private Observer<SafetyCircleMember[]> mActiveCircleMembersObserver
            = new Observer<SafetyCircleMember[]>() {
        @Override
        public void onChanged(@Nullable SafetyCircleMember[] safetyCircleMembers) {
            if (mProgressDialog != null && mProgressDialog.isVisible()) mProgressDialog.dismiss();
            if (safetyCircleMembers == null || safetyCircleMembers.length <= 0) return;
            if (mGoogleMap == null) return;
            try {
                bindSafetyCircleMembers(safetyCircleMembers);
                getExtraMarkers();
            } catch (Exception e) {
                M.log(e.getMessage());
            }
        }
    };
    private Observer<Incident[]> mIncidentLogObserver
            = new Observer<Incident[]>() {
        @Override
        public void onChanged(@Nullable Incident[] incidents) {
            if (mProgressDialog != null && mProgressDialog.isVisible()) mProgressDialog.dismiss();
            if (incidents == null || incidents.length <= 0) return;
            try {
                if (bindIncidents(incidents)) return;
                getExtraMarkers();
            } catch (Exception e) {
                M.log(e.getMessage());
            }
        }
    };
    private Observer<NearbySearchResponse> mHospitalsObserver
            = new Observer<NearbySearchResponse>() {
        @Override
        public void onChanged(@Nullable NearbySearchResponse response) {
            if (response == null) return;
            bindMarkers(R.drawable.ic_hospital, response);
        }
    };
    private Observer<NearbySearchResponse> mFireStationObserver
            = new Observer<NearbySearchResponse>() {
        @Override
        public void onChanged(@Nullable NearbySearchResponse response) {
            if (response == null) return;
            bindMarkers(R.drawable.ic_fire_force, response);
        }
    };
    private Observer<NearbySearchResponse> mPoliceStationObserver
            = new Observer<NearbySearchResponse>() {
        @Override
        public void onChanged(@Nullable NearbySearchResponse response) {
            if (response == null) return;
            bindMarkers(R.drawable.ic_police, response);
        }
    };
    private Observer<List<ServiceProvider>> mServiceProvidersObserver
            = new Observer<List<ServiceProvider>>() {
        @Override
        public void onChanged(@Nullable List<ServiceProvider> serviceProviders) {
            if (serviceProviders == null || serviceProviders.size() <= 0) {
                M.log("service providers chatItems is empty");
                return;
            }
            int index = 0;
            for (ServiceProvider s : serviceProviders) {
                s.setZIndex(++index);
                mGoogleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_service_provider_dummy))
                        .position(new LatLng(Double.valueOf(s.providerLat), Double.valueOf(s.providerLong)))
                        .title(s.provideName + "-" + s.providerAddress));
            }

        }
    };
    private Observer<CircleSwitchResponse> mCircleSwitchObserver
            = new Observer<CircleSwitchResponse>() {
        @Override
        public void onChanged(@Nullable CircleSwitchResponse circleSwitchResponse) {
            if (mProgressDialog != null && mProgressDialog.isVisible()) mProgressDialog.dismiss();
            if (circleSwitchResponse == null) return;
            try {
                mLblDrawerPrimaryCircleName.setText(circleSwitchResponse.circleName);
                setTitle(circleSwitchResponse.circleName);
                if (mViewModel != null)
                    if (mTglShowMembersOfSafetyCircle.isChecked())
                        mViewModel.getActiveCircleMembers();
            } catch (Exception e) {
                M.log(e.getMessage());
            }

            if (mViewModel != null) {
                mViewModel.getUnreadMessages();
            }

        }
    };

    private ObjectAnimator mShowAnimator, mHideAnimator;
    private boolean isShowingToggle = true;
    private boolean mIsRightViewOpen = false;
    private boolean misLeftViewOpen = false;

    private void bindFavPlaces(@Nullable List<FavoritePlace> favoritePlaces) {
        try {
            mGoogleMap.clear();
        } catch (Exception e) {
            M.log(e.getMessage());
        }

        if (favoritePlaces == null || favoritePlaces.size() <= 0) return;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        if (mClusterManager == null) return;
        mClusterManager.clearItems();

        int index = 0;
        for (FavoritePlace favPlace : favoritePlaces) {
            favPlace.setZIndex(++index);
            mClusterManager.addItem(favPlace);
            builder.include(favPlace.getPosition());
        }
        mClusterManager.cluster();

        LatLngBounds bounds = builder.build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 250));

    }

    private void bindSafetyCircleMembers(@Nullable SafetyCircleMember[] safetyCircleMembers) {

        try {
            mGoogleMap.clear();
        } catch (Exception e) {
            M.log(e.getMessage());
        }

        if (safetyCircleMembers == null || safetyCircleMembers.length <= 0) return;

        if (mClusterManager == null) return;
        mClusterManager.clearItems();

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        int index = 0;
        for (SafetyCircleMember circleMember : safetyCircleMembers) {
            circleMember.setZIndex(++index);
            if (circleMember.latitude == 0 || circleMember.longitude == 0) continue;
            mClusterManager.addItem(circleMember);
            builder.include(circleMember.getPosition());
        }
        mClusterManager.cluster();
        LatLngBounds bounds = builder.build();
        if (safetyCircleMembers.length > 1)
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 400));
        else {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(safetyCircleMembers[0].latitude, safetyCircleMembers[0].longitude), 10);
            mGoogleMap.animateCamera(cameraUpdate);
        }
    }

    private boolean bindIncidents(@Nullable Incident[] incidents) {
        try {
            mGoogleMap.clear();
        } catch (Exception e) {
            M.log(e.getMessage());
        }

        if (incidents == null || incidents.length <= 0) return true;

        if (mClusterManager == null) return true;

        mClusterManager.clearItems();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        int index = 0;

        for (Incident incident : incidents) {
            incident.setZIndex(++index);
            mClusterManager.addItem(incident);
            builder.include(incident.getPosition());
        }
        mClusterManager.cluster();
        LatLngBounds bounds = builder.build();
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 250));
        return false;
    }

    private void processDialogAndControllers() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog();
            mProgressDialog.setCancelable(false);
            mProgressDialog.setRetainInstance(true);
        }
        mProgressDialog.show(getChildFragmentManager(), "Progress");

        if (mProgressHandler == null) mProgressHandler = new Handler();

        if (mDismissProgressRunnable == null) {
            mDismissProgressRunnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        if (mProgressDialog != null && mProgressDialog.isVisible())
                            mProgressDialog.dismiss();
                    } catch (Exception e) {
                        M.log(e.getMessage());
                    }
                }
            };
        }
    }

    private void postDismissRunnable() {
        try {
            mProgressHandler.postDelayed(mDismissProgressRunnable, 30 * 1000);
        } catch (Exception e) {
            M.log(e.getMessage());
        }
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        setHasOptionsMenu(true);
        mLocationUtil = new LocationUtil(getActivity());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.check_in, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        } else if (item.getItemId() == R.id.mnuCheckIn) {
            try {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
            } catch (Exception e) {
                M.log(e.getMessage());
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Activity activity = getActivity();
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    Place place = PlacePicker.getPlace(activity, data);
                    String circleId = PreferenceHelper.getString(activity,
                            PreferenceHelper.KEY_PRIMARY_CIRCLE, "");

                    String name = PreferenceHelper.getString(getActivity(),
                            PreferenceHelper.KEY_FIRST_NAME, "");

                    String image = PreferenceHelper.getString(getActivity(),
                            PreferenceHelper.KEY_PROFILE_IMAGE, "");
                    Toast.makeText(getActivity(), image, Toast.LENGTH_LONG).show();

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("lat", place.getLatLng().latitude);
                    jsonObject.put("long", place.getLatLng().longitude);
                    jsonObject.put("circleId", circleId);
                    jsonObject.put("locName", place.getAddress().toString().split(",")[0]);
                    jsonObject.put("senderName", name);
                    jsonObject.put("senderImage", image);
                    Intent intent = new Intent();
                    intent.setAction(ChatListenerService.OUTGOING_CHAT_ACTION);
                    intent.putExtra(ChatConstants.EXTRA, ChatConstants.ON_CHECK_IN);
                    intent.putExtra(ChatConstants.EXTRA_PAYLOAD, jsonObject.toString());
                    LocalBroadcastManager.getInstance(getActivity())
                            .sendBroadcast(intent);
                    Toast.makeText(getActivity(), "Checked in successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    M.log(e.getMessage());
                }

            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        final TabLayout.Tab safetyCircle = mHomeTabs.getTabAt(0);
        final TabLayout.Tab favoritePlaces = mHomeTabs.getTabAt(1);
        final TabLayout.Tab incidents = mHomeTabs.getTabAt(2);

        if (safetyCircle != null) {
            if (safetyCircle.getCustomView() != null) {
                safetyCircle.getCustomView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            mGoogleMap.clear();
                        } catch (Exception e) {
                            M.log(e.getMessage());
                        }
                        mLblViewAllSelectedTabList.setText(R.string.view_all_safety_circle_members);
                        selectedTab = SAFETY_CIRCLE;
                        if (mTglShowMembersOfSafetyCircle.isChecked())
                            mViewModel.getActiveCircleMembers();
                        safetyCircle.select();
                        processDialogAndControllers();
                        postDismissRunnable();
                        showToggle();
                        mBtnTabPullOutSeparator.setBackgroundResource(R.drawable.ic_safety_circles_round_bg);
                        mBtnTabPullOutSeparator.setImageResource(R.drawable.ic_safety_circles_round_bg);
                    }
                });
            }
        }

        if (favoritePlaces != null) {
            if (favoritePlaces.getCustomView() != null) {
                favoritePlaces.getCustomView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            mGoogleMap.clear();
                        } catch (Exception e) {
                            M.log(e.getMessage());
                        }
                        mLblViewAllSelectedTabList.setText(R.string.view_all_fav_places);
                        selectedTab = FAV_PLACES;
                        mViewModel.getFavPlaces();
                        favoritePlaces.select();
                        processDialogAndControllers();
                        postDismissRunnable();
                        hideToggle();
                        mBtnTabPullOutSeparator.setBackgroundResource(R.drawable.ic_fav_places_round_bg);
                        mBtnTabPullOutSeparator.setImageResource(R.drawable.ic_fav_places_round_bg);
                    }
                });
            }
        }

        if (incidents != null) {
            if (incidents.getCustomView() != null) {
                incidents.getCustomView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            mGoogleMap.clear();
                        } catch (Exception e) {
                            M.log(e.getMessage());
                        }
                        mLblViewAllSelectedTabList.setText(R.string.view_all_incidents);
                        selectedTab = INCIDENTS;
                        mViewModel.getIncidentLog();
                        incidents.select();
                        processDialogAndControllers();
                        postDismissRunnable();
                        hideToggle();
                        mBtnTabPullOutSeparator.setBackgroundResource(R.drawable.ic_incidents_round_bg);
                        mBtnTabPullOutSeparator.setImageResource(R.drawable.ic_incidents_round_bg);
                    }
                });
            }
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.getSafetyCircles();
        mViewModel.getUnreadMessages();
        final Activity activity = getActivity();
        String name = PreferenceHelper.getString(activity, PreferenceHelper.KEY_FIRST_NAME, "");
        name += " " + PreferenceHelper.getString(activity, PreferenceHelper.KEY_LAST_NAME, "");
        String email = PreferenceHelper.getString(activity, PreferenceHelper.KEY_EMAIL, "");
        String image = PreferenceHelper.getString(activity, PreferenceHelper.KEY_PROFILE_IMAGE, "");
        //image="1521785143.png";
        // Log.i("image name: ",image);
//        SharedPreferences preferences=getActivity().getSharedPreferences("imageName",0);
//        SharedPreferences.Editor editor=preferences.edit();
        // image= preferences.getString("image","");

        String profileImage = getString(R.string.image_base_url) + image;
        //editor.commit();
        Log.i("imagePath:", profileImage);
        ((TjssApplication) getActivity().getApplication()).getGlide()
                .load(profileImage)
                .into(mImgDrawerProfileImage);

        mLblDrawerUsername.setText(name);
        mLblDrawerEmail.setText(email);
    }

    private void getExtraMarkers() {
        if (mGoogleMap == null) return;

        int minZoomThreshold = 10;
        if (mClearMap) {
            mClearMap = false;
            if (mCurrentZoom < minZoomThreshold) {
                try {
                    mGoogleMap.clear();
                    switch (selectedTab) {
                        case SAFETY_CIRCLE:
                            SafetyCircleMember[] safetyCircleMembers
                                    = mViewModel.getActiveSafetyCircleMembersData().getValue();
                            if (safetyCircleMembers != null && safetyCircleMembers.length > 0)
                                bindSafetyCircleMembers(safetyCircleMembers);
                            break;

                        case FAV_PLACES:
                            List<FavoritePlace> favoritePlaces
                                    = mViewModel.getFavoritePlacesData().getValue();

                            if (favoritePlaces != null && favoritePlaces.size() > 0)
                                bindFavPlaces(favoritePlaces);
                            break;

                        case INCIDENTS:
                            Incident[] incidents
                                    = mViewModel.getIncidentsLogData().getValue();

                            if (incidents != null && incidents.length > 0)
                                bindIncidents(incidents);

                            break;
                    }
                } catch (Exception e) {
                    M.log(e.getMessage());
                }
                return;
            }
        }
        HashMap<String, String> queryParams = new HashMap<>();
        if (havePermission(Permissions.FINE_LOCATION)) {
            if (mLocationUtil == null) mLocationUtil = new LocationUtil(getActivity());
            Location location = mLocationUtil.getFineLocation();
            if (location == null) return;
            LatLng center = new LatLng(location.getLatitude(), location.getLongitude());
            queryParams.put("location", center.latitude + "," + center.longitude);
            queryParams.put("radius", "5000");

            String mapOptions = PreferenceHelper.getString(getActivity(),
                    PreferenceHelper.KEY_MAP_OPTIONS, "");
            if (mapOptions.equals("")) return;
            if (mCurrentZoom != minZoomThreshold) return;
            if (mExtraMarkersLocation == null)
                mExtraMarkersLocation = new Location("");
            mExtraMarkersLocation.setLatitude(center.latitude);
            mExtraMarkersLocation.setLongitude(center.longitude);
            mViewModel.getServiceProviders(mExtraMarkersLocation);


            if (mapOptions.contains("1")) {
                queryParams.put("type", "hospital");
                NearbySearchResponse response = mViewModel.getHospitalData(queryParams);
                if (response == null) return;
                if (response.status.toLowerCase().equals("ok")) {
                    bindMarkers(R.drawable.ic_hospital, response);
                }
            }

            if (mapOptions.contains("2")) {
                queryParams.put("type", "fire_station");
                NearbySearchResponse response = mViewModel.getFireStationData(queryParams);
                if (response == null) return;
                if (response.status.toLowerCase().equals("ok")) {
                    bindMarkers(R.drawable.ic_fire_force, response);
                }
            }

            if (mapOptions.contains("3")) {
                queryParams.put("type", "police");
                NearbySearchResponse response = mViewModel.getPoliceStationData(queryParams);
                if (response == null) return;
                if (response.status.toLowerCase().equals("ok")) {
                    bindMarkers(R.drawable.ic_police, response);
                }
            }
        }

    }

    private void bindMarkers(@DrawableRes int markerIcon, @NonNull NearbySearchResponse response) {
        mClearMap = true;
        List<Result> results = response.results;
        for (Result result : results) {
            if (result == null) continue;
            Geometry geometry = result.geometry;
            com.twixttechnologies.tjss.model.network.response.nearby.Location location = geometry.location;
            LatLng latLng = new LatLng(location.lat, location.lng);
            Toast.makeText(getActivity(), latLng.toString(), Toast.LENGTH_LONG).show();
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(markerIcon))
                    .title(result.name));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new ActionBarDrawerToggle(getActivity(), mDrawerLayout, getToolbar(), R.string.drawer_open, R.string.drawer_close).syncState();
        mMap = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMap.getMapAsync(this);

//        if (mMap == null) {
//            mMap = SupportMapFragment.newInstance();
//            mMap.getMapAsync(new OnMapReadyCallback() {
//                @Override
//                public void onMapReady(GoogleMap googleMap) {
//                    LatLng latLng = new LatLng(1.289545, 103.849972);
//                    googleMap.addMarker(new MarkerOptions().position(latLng)
//                            .title("Singapore"));
//                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//                }
//            });
//        }        // R.id.map is a FrameLayout, not a Fragment
//        getChildFragmentManager().beginTransaction().replace(R.id.map, mMap).commit();

        mViewModel = ViewModelProviders.of(this, new UserHomeViewModel.UserHomeViewModelFactory(getActivity().getApplication()))
                .get(UserHomeViewModel.class);

        mViewModel.getFavoritePlacesData().observe(this, mFavoritePlacesObserver);
        mViewModel.getSafetyCirclesData().observe(this, mSafetyCircleListObserver);
        mViewModel.getHospitalsData().observe(this, mHospitalsObserver);
        mViewModel.getFireStationsData().observe(this, mFireStationObserver);
        mViewModel.getPoliceStationData().observe(this, mPoliceStationObserver);
        mViewModel.getServiceProvidersData().observe(this, mServiceProvidersObserver);
        mViewModel.getSafetyCircleSwitchData().observe(this, mCircleSwitchObserver);
        mViewModel.getUnreadMessageData().observe(this, mUnreadMessageObserver);

        String planId = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_SUBSCRIPTION_PLAN_ID, "");
        if (planId.equals("")) return;
        switch (planId) {
            case "1":
                mLblSelectedPlanName.setText("Basic");
                break;
            case "2":
                mLblSelectedPlanName.setText("Silver");
                break;
            case "3":
                mLblSelectedPlanName.setText("Gold");
                break;
            case "4":
                mLblSelectedPlanName.setText("Platinum");
                break;
            case "5":
                mLblSelectedPlanName.setText("Corporate");
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMaxZoom = (int) mGoogleMap.getMaxZoomLevel();
        mClusterManager = new ClusterManager<>(getActivity().getApplication(), mGoogleMap);
        mClusterRenderer = new TjssClusterRenderer(getActivity().getApplication(), mGoogleMap, mClusterManager);
        mClusterManager.setRenderer(mClusterRenderer);
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);
        mTglShowMembersOfSafetyCircle.setChecked(true);
        mViewModel.getActiveSafetyCircleMembersData().observe(this, mActiveCircleMembersObserver);
        mViewModel.getIncidentsLogData().observe(this, mIncidentLogObserver);
        ArrayList<String> permissionsRequired = Permissions.havePermissionFor(getActivity(),
                Permissions.FINE_LOCATION, Permissions.COARSE_LOCATION, Permissions.CALL_PHONE);
        if (permissionsRequired == null) {
            accessLocation();
        } else {
            ArrayList<String> permissionDetails = new ArrayList<>(2);
            permissionDetails.add("Request to access your location-TJSS will need to access your location in order " +
                    "to work properly");
            permissionDetails.add("Request to make calls-TJSS will need your permission to make calls from your phone" +
                    "when you want to call a safety circle member");
            Permissions.showPermissionDetails(permissionDetails, this, getChildFragmentManager());
        }

        mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                mCurrentZoom = (int) mGoogleMap.getCameraPosition().zoom;
                getExtraMarkers();
                if (mClusterManager != null)
                    mClusterManager.onCameraIdle();
            }
        });

        mGoogleMap.setOnMarkerClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mViewModel.getActiveCircleMembers();
    }

    private void accessLocation() {
        boolean isFineLocationEnabled = mLocationUtil.isFineLocationEnabled(getActivity());
        if (!isFineLocationEnabled) {
            mLocationUtil.showSettingsAlert();
            return;
        }
        Location location = mLocationUtil.getFineLocation();
        if (location == null) {
            return;
        }

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        mViewModel.updateLocation(latLng);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.imgDrawerProfileImage, R.id.btnDrawerCreateCircle, R.id.btnDrawerJoinCircle,
            R.id.btnDrawerFavoritePlaces, R.id.btnDrawerHelpAlert, R.id.btnDrawerMessages,
            R.id.btnDrawerPlans, R.id.btnDrawerLocationSharing, R.id.btnDrawerSettings,
            R.id.btnExpandOrCollapseCircles, R.id.btnDrawerFaqs, R.id.btnDrawerSafetyTips,
            R.id.btnTabPullOutSeparator, R.id.lblViewAllSelectedTabList,
            R.id.btnDrawerServiceProviders, R.id.btnDrawerCoins, R.id.btnDrawerInAppPurchase,
            R.id.btnDrawerSecurityQuestion, R.id.btnDrawerCheckIn, R.id.btnDrawerHelpAlertHistory,
            R.id.btnHomeSendHelpAlert, R.id.homePullOutViewServiceProviders,
            R.id.btnTabPullOutSeparatorServiceProviders, R.id.imgPullArrow,
            R.id.imgPullArrowServiceProviders, R.id.lblViewAllServiceProviders})

    public void onViewClicked(View view) {
        boolean close = true;
        switch (view.getId()) {
            case R.id.imgDrawerProfileImage:
                startActivity(new Intent(getActivity(), UserProfileActivity.class));
                break;

            case R.id.btnDrawerCreateCircle:
                startActivity(new Intent(getActivity(), CreateCircleActivity.class));
                break;

            case R.id.btnDrawerJoinCircle:
                startActivity(new Intent(getActivity(), JoinCircleActivity.class));
                break;

            case R.id.btnDrawerFavoritePlaces:
                startActivity(new Intent(getActivity(), FavPlacesActivity.class));
                break;

            case R.id.btnDrawerHelpAlert:
                boolean hasSecurity = PreferenceHelper.getBoolean(getActivity(), PreferenceHelper.KEY_ADDED_SECURITY, false);
                if (hasSecurity)
                    startActivity(new Intent(getActivity(), HelpAlertActivity.class));
                else {
                    Toast.makeText(getActivity(), "Please select your security question to continue", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), SecurityQuestionsActivity.class));
                }
                break;

            case R.id.btnDrawerMessages:
                startActivity(new Intent(getActivity(), MessagesActivity.class));
                break;

            case R.id.btnDrawerPlans:
                startActivity(new Intent(getActivity(), PlansListingActivity.class));
                break;

            case R.id.btnDrawerLocationSharing:
                startActivity(new Intent(getActivity(), LocationSharingActivity.class));
                break;

            case R.id.btnDrawerSafetyTips:
                startActivity(new Intent(getActivity(), SafetyTipsListingActivity.class));
                break;

            case R.id.btnDrawerCoins:
                startActivity(new Intent(getActivity(), CoinPurchaseActivity.class));
                break;

            case R.id.btnDrawerSettings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                break;

            case R.id.btnDrawerInAppPurchase:
                startActivity(new Intent(getActivity(), PurchaseServiceActivity.class));
                break;

            case R.id.btnDrawerSecurityQuestion:
                startActivity(new Intent(getActivity(), SecurityQuestionsActivity.class));
                break;

            case R.id.btnDrawerCheckIn:
                startActivity(new Intent(getActivity(), SafetyCircleMembersListingForCheckInActivity.class));
                break;

            case R.id.btnExpandOrCollapseCircles:
                mLtSafetyCirclesHolderMain.setVisibility(mIsExpanded ? View.GONE : View.VISIBLE);
                mBtnExpandCircles.setImageResource(!mIsExpanded ? R.drawable.ic_expand_less : R.drawable.ic_expand_more);
                mIsExpanded = mLtSafetyCirclesHolderMain.getVisibility() == View.VISIBLE;
                close = false;
                break;

            case R.id.btnDrawerFaqs:
                startActivity(new Intent(getActivity(), FaqActivity.class));
                break;

            case R.id.imgPullArrow:

            case R.id.btnTabPullOutSeparator:
                if (mIsRightViewOpen)
                    animateCloseRightPullOutView();
                else
                    animateOpenRightPullOutView();
                break;

            case R.id.lblViewAllSelectedTabList:
                if (selectedTab == SAFETY_CIRCLE)
                    startActivity(new Intent(getActivity(), SafetyCircleMembersListingActivity.class));
                else if (selectedTab == FAV_PLACES)
                    startActivity(new Intent(getActivity(), FavPlacesActivity.class));
                else if (selectedTab == INCIDENTS) {
                    startActivity(new Intent(getActivity(), IncidentsListingActivity.class));
                }
                break;

            case R.id.btnDrawerServiceProviders:
                boolean hasSecurityAdded = PreferenceHelper.getBoolean(getActivity(), PreferenceHelper.KEY_ADDED_SECURITY, false);
                if (hasSecurityAdded)
                    startActivity(new Intent(getActivity(), ServiceProvidersListingActivity.class));
                else {
                    Toast.makeText(getActivity(), "Please select your security question to continue", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), SecurityQuestionsActivity.class));
                }
                break;

            case R.id.btnDrawerHelpAlertHistory:
                startActivity(new Intent(getActivity(), HelpAlertHistoryActivity.class));
                break;

            case R.id.btnHomeSendHelpAlert:
                hasSecurity = PreferenceHelper.getBoolean(getActivity(), PreferenceHelper.KEY_ADDED_SECURITY, false);
                if (hasSecurity)
                    startActivity(new Intent(getActivity(), HelpAlertActivity.class));
                else {
                    Toast.makeText(getActivity(), "Please select your security question to continue", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), SecurityQuestionsActivity.class));
                }
                break;

            case R.id.btnTabPullOutSeparatorServiceProviders:

            case R.id.imgPullArrowServiceProviders:

            case R.id.homePullOutViewServiceProviders:
                if (misLeftViewOpen)
                    animateCloseLeftPullOutView();
                else
                    animateOpenLeftPullOutView();
                break;

            case R.id.lblViewAllServiceProviders:
                startActivity(new Intent(getActivity(), AppServiceProviderListingActivity.class));
                break;

        }

        if (close)
            mDrawerLayout.closeDrawer(GravityCompat.START);

    }

    @Override
    public void onPermissionGranted() {
        Permissions.requestPermissionFromFragment(this, Permissions.LOCATION_REQUEST_CODE,
                Permissions.FINE_LOCATION, Permissions.COARSE_LOCATION, Permissions.CALL_PHONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Permissions.LOCATION_REQUEST_CODE) {
            for (String permission : permissions) {
                if (permission.equals(Permissions.FINE_LOCATION) || permission.equals(Permissions.COARSE_LOCATION)) {
                    if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        accessLocation();
                    }
                }
            }
        } else if (requestCode == Permissions.CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                call();
            else
                M.showToast(getActivity(), "Unable to make call, Permission denied");
        }
    }

    //region animation

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("Friends");
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }


    //endregion animation

    @Override
    public View getInfoContents(Marker marker) {

        if (marker.getTag() == null) return null;

        String tag = (String) marker.getTag();


        String[] parts = tag.split("~~");
        switch (selectedTab) {
            case SAFETY_CIRCLE: {
                //parts will be in the order,
                //0.Name
                //1.Battery level
                //2.Image name

                break;
            }

            case FAV_PLACES: {
                //Parts will be in the order
                //1.Name
                //2.Address
                try {


                    @SuppressLint("InflateParams") View view = getActivity().getLayoutInflater().inflate(R.layout.extra_marker_detail_fav_place, null, false);
                    if (view == null) return null;

                    AppCompatTextView favPlaceName = view.findViewById(R.id.lblMarkerDetailsFavPlaceName);
                    AppCompatTextView favPlaceAddress = view.findViewById(R.id.lblMarkerDetailsFavPlaceAddress);

                    favPlaceName.setText(parts[0]);
                    favPlaceAddress.setText(parts[1]);

                    return view;
                } catch (Exception e) {
                    M.log(e.getMessage());
                }
                break;
            }

            case INCIDENTS: {


                try {
                    //Parts will be in the order
                    //1.Name
                    //2.Description
                    //3.Time


                    @SuppressLint("InflateParams") View view = getActivity().getLayoutInflater().inflate(R.layout.extra_marker_detail_incidents, null, false);
                    if (view == null) return null;


                    AppCompatTextView incidentName = view.findViewById(R.id.lblMarkerDetailsIncidentName);
                    AppCompatTextView description = view.findViewById(R.id.lblMarkerDetailsIncidentDescription);
                    AppCompatTextView location = view.findViewById(R.id.lblMarkerDetailsincidentLocation);


                    incidentName.setText(parts[0]);
                    description.setText(parts[1]);
                    location.setText(parts[2]);

                    return view;
                } catch (Exception e) {
                    M.log(e.getMessage());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                break;
            }
        }

        return null;

    }

    private void showToggle() {
        if (isShowingToggle) return;
        if (mShowAnimator == null) {
            mShowAnimator = ObjectAnimator.ofFloat(mLtShowMembersOfSafetyCircle, View.TRANSLATION_Y, 0);
            mShowAnimator.setInterpolator(new LinearInterpolator());
            mShowAnimator.setDuration(300);
        }

        mShowAnimator.start();
        isShowingToggle = true;
    }

    private void hideToggle() {
        if (!isShowingToggle) return;
        if (mHideAnimator == null) {
            mHideAnimator = ObjectAnimator.ofFloat(mLtShowMembersOfSafetyCircle, View.TRANSLATION_Y, -125);
            mHideAnimator.setInterpolator(new LinearInterpolator());
            mHideAnimator.setDuration(300);
        }
        mHideAnimator.start();
        isShowingToggle = false;
    }

    @Deprecated
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTag() == null) return false;

        if (marker.getZIndex() > 0) {
            marker.setZIndex(-marker.getZIndex());
        } else {
            marker.setZIndex(Math.abs(marker.getZIndex()));
        }

        String tag = (String) marker.getTag();

        String[] parts = tag.split("~~");
        if (selectedTab == SAFETY_CIRCLE) {
            mHandler.postDelayed(new SafetyCircleDialogRunnable(parts), 200);
        } else if (selectedTab == FAV_PLACES) {
            mHandler.postDelayed(new FavPlaceDialogRunnable(parts), 200);
        } else if (selectedTab == INCIDENTS) {
            mHandler.postDelayed(new IncidentsDialogRunnable(parts), 200);
        }
        return false;
    }

    @Override
    public void onSelected(@MarkerOption int selectedItem, String... extra) {
        if (selectedItem == CALL) {
            try {

                String phone = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_PHONE, "");
                mTempPhone = extra[0];

                if (phone.contains(mTempPhone)) {
                    M.showToast(getActivity(), "Not Allowed");
                    return;
                }

                ArrayList<String> requiredPermissions
                        = Permissions.havePermissionFor(getActivity(), Permissions.CALL_PHONE);
                if (requiredPermissions != null && requiredPermissions.size() > 0) {
                    requiredPermissions = Permissions.shouldShowPermissionRequestRationale(getActivity(), Permissions.CALL_PHONE);
                    if (requiredPermissions != null && requiredPermissions.size() > 0) {
                        ArrayList<String> permissionDetails = new ArrayList<>(1);
                        permissionDetails.add("Request to make calls-TJSS will need your permission to make calls from your phone " +
                                "when you want to call a safety circle member");
                        Permissions.showPermissionDetails(permissionDetails, new PermissionsDetailListDialog.PermissionsDialogCallback() {
                            @Override
                            public void onPermissionGranted() {
                                Permissions.requestPermissionFromFragment(FragmentHome.this, Permissions.CALL_PERMISSION_REQUEST_CODE, Permissions.CALL_PHONE);
                            }
                        }, getChildFragmentManager());
                    } else {
                        Permissions.requestPermissionFromFragment(FragmentHome.this, Permissions.CALL_PERMISSION_REQUEST_CODE, Permissions.CALL_PHONE);
                    }
                } else {
                    call();
                }

            } catch (Exception e) {
                M.log(e.getMessage());
            }
        } else if (selectedItem == MAIL) {
            String userID = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_USER_ID, "0");
            if (userID.equals(extra[0])) {
                M.showToast(getActivity(), "Not Allowed");
                return;
            }
            int other = Integer.parseInt(extra[0]);
            Intent chatDetailIntent = new Intent(getActivity(), ChatDetailActivity.class);
            chatDetailIntent.putExtra(FragmentChatDetail.USERS, String.valueOf(other));
            chatDetailIntent.putExtra(FragmentChatDetail.GROUP_NAME, extra[1]);
            startActivity(chatDetailIntent);
        }
    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + mTempPhone));
        startActivity(intent);
    }

    private void animateOpenRightPullOutView() {
        if (misLeftViewOpen) {
            animateCloseLeftPullOutView();
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(mHomePullOutView, View.TRANSLATION_X, -mHomePullOutView.getWidth() + 245);
        animator.setDuration(150);
        animator.setInterpolator(new OvershootInterpolator(1.2f));
        animator.start();
        mIsRightViewOpen = true;
        mImgPullArrow.setImageResource(R.drawable.ic_arrow_forward);
    }

    private void animateCloseRightPullOutView() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mHomePullOutView, View.TRANSLATION_X, 0);
        animator.setDuration(100);
        animator.setInterpolator(new OvershootInterpolator(1.2f));
        animator.start();
        mIsRightViewOpen = false;
        mImgPullArrow.setImageResource(R.drawable.ic_arrow_back);
    }

    private void animateOpenLeftPullOutView() {
        if (mIsRightViewOpen) {
            animateCloseRightPullOutView();
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(mHomePullOutViewServiceProviders, View.TRANSLATION_X, mHomePullOutViewServiceProviders.getWidth() - 275);
        animator.setDuration(150);
        animator.setInterpolator(new OvershootInterpolator(1.2f));
        animator.start();
        misLeftViewOpen = true;
        mImgPullArrowServiceProviders.setImageResource(R.drawable.ic_arrow_back);
    }

    private void animateCloseLeftPullOutView() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mHomePullOutViewServiceProviders, View.TRANSLATION_X, 0);
        animator.setDuration(150);
        animator.setInterpolator(new OvershootInterpolator(1.2f));
        animator.start();
        misLeftViewOpen = false;
        mImgPullArrowServiceProviders.setImageResource(R.drawable.ic_arrow_forward);
    }

    @OnCheckedChanged(R.id.tglShowMembersOfSafetyCircle)
    public void onCheckChanged(boolean checked) {
        if (ignoreChange) {
            ignoreChange = false;
            return;
        }
        if (mGoogleMap == null) return;
        if (mViewModel == null) return;
        if (selectedTab != SAFETY_CIRCLE) {
            mTglShowMembersOfSafetyCircle.setChecked(!checked);
            ignoreChange = true;
            return;
        }
        try {
            mGoogleMap.clear();
            if (checked) {
                mViewModel.getActiveCircleMembers();
            } else {
                SafetyCircleMember[] members = mViewModel.getActiveSafetyCircleMembersData().getValue();
                if (members == null || members.length <= 0) return;
                String userID = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_USER_ID, "");
                for (SafetyCircleMember member : members) {
                    if (!member.userId.equals(userID)) {
                        continue;
                    }

                    Location location = mLocationUtil.getFineLocation();
                    if (location != null) {
                        member.latitude = location.getLatitude() == 0 ? member.latitude : location.getLatitude();
                        member.longitude = location.getLongitude() == 0 ? member.longitude : location.getLongitude();
                    }
                    bindSafetyCircleMembers(new SafetyCircleMember[]{member});
                    break;
                }

            }
        } catch (Exception e) {
            M.log(e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        String tag = v.getTag().toString();
        if (tag == null) return;
        final String[] parts = tag.split("~~");
        if (tag.length() < 2) return;
        M.showAlert(getActivity(), "Change Safety Circle", "Are You sure you want to change your circle?",
                "CHANGE", "NO,THANKS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        processDialogAndControllers();
                        postDismissRunnable();
                        mViewModel.switchSafetyCircle(parts[0], parts[1]);
                    }
                }, null, false);


    }

    @Override
    public boolean onClusterClick(Cluster<ClusterItemBase> cluster) {
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (ClusterItem item : cluster.getItems()) {
            builder.include(item.getPosition());
        }
        final LatLngBounds bounds = builder.build();
        try {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 350));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onClusterItemClick(ClusterItemBase item) {

        int zIndex = item.zIndex();


        if (zIndex > 0)
            zIndex = zIndex * -1;
        else
            zIndex = Math.abs(zIndex);


        try {
            item.setZIndex(zIndex);
            mClusterRenderer.getMarker(item).setZIndex(zIndex);
        } catch (Exception e) {
            M.log(e.getMessage());
        }

        if (selectedTab == SAFETY_CIRCLE) {
            mHandler.postDelayed(new SafetyCircleDialogRunnable(item.createParts()), 200);
        } else if (selectedTab == FAV_PLACES) {
            mHandler.postDelayed(new FavPlaceDialogRunnable(item.createParts()), 200);
        } else if (selectedTab == INCIDENTS) {
            mHandler.postDelayed(new IncidentsDialogRunnable(item.createParts()), 200);
        }
        return true;
    }

    private class SafetyCircleDialogRunnable implements Runnable {

        private final String[] mParts;

        SafetyCircleDialogRunnable(String[] parts) {
            this.mParts = parts;
        }

        @Override
        public void run() {
            SafetyCircleMarkerInfoWindowDialog dialog = SafetyCircleMarkerInfoWindowDialog.newInstance(mParts);
            dialog.setMarkerOptionSelectedListener(FragmentHome.this);
            dialog.show(getChildFragmentManager(), "markerInfoWindow");
        }
    }

    private class FavPlaceDialogRunnable implements Runnable {

        private final String[] mParts;


        private FavPlaceDialogRunnable(String[] parts) {
            this.mParts = parts;
        }

        @Override
        public void run() {
            FavPlaceMarkerInfoWindowDialog.newInstance(mParts)
                    .show(getChildFragmentManager(), "favPlaceDialog");
        }
    }

    private class IncidentsDialogRunnable implements Runnable {

        private final String[] mParts;


        private IncidentsDialogRunnable(String[] parts) {
            this.mParts = parts;
        }

        @Override
        public void run() {
            IncidentMarkerInfoWindowDialog.newInstance(mParts)
                    .show(getChildFragmentManager(), "favPlaceDialog");
        }
    }

    private class TjssClusterRenderer extends DefaultClusterRenderer<ClusterItemBase> {

        private final IconGenerator mClusterIconGenerator;


        private RequestOptions mRequestOptions;


        TjssClusterRenderer(Context context, GoogleMap map, ClusterManager<ClusterItemBase> clusterManager) {
            super(context, map, clusterManager);
            mClusterIconGenerator = new IconGenerator(context);
            @SuppressLint("InflateParams")
            View multiProfile = getLayoutInflater().inflate(R.layout.letter_pin, null);
            mClusterIconGenerator.setBackground(null);
            mClusterIconGenerator.setContentView(multiProfile);
            mRequestOptions = new RequestOptions()
                    .centerCrop()
                    .override(75, 75)
                    .transform(new BitmapToPinExtension(String.valueOf(System.currentTimeMillis())));
        }


        @Override
        protected void onBeforeClusterItemRendered(final ClusterItemBase item, MarkerOptions markerOptions) {
            if (item instanceof SafetyCircleMember) {
                Bitmap icon = mClusterIconGenerator.makeIcon(item.getTitle().substring(0, 2) + "..");
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(item.getTitle());

                Glide.with(getActivity().getApplication())
                        .asBitmap()
                        .apply(mRequestOptions)
                        .load(getString(R.string.image_base_url) + item.getImage())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                try {
                                    Marker marker = getMarker(item);
                                    if (marker != null)
                                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(resource));
                                } catch (Exception e) {
                                    M.log(e.getMessage());
                                }
                            }
                        });
            } else if (item instanceof Incident) {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.incidents_pin));
            } else if (item instanceof FavoritePlace) {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.fav_places_pin));
            }
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<ClusterItemBase> cluster, MarkerOptions markerOptions) {
            Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster<ClusterItemBase> cluster) {
            return mCurrentZoom != mMaxZoom && cluster.getSize() > 1;
        }
    }

}
