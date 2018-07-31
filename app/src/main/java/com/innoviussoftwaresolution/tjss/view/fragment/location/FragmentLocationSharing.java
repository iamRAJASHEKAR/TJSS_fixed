package com.innoviussoftwaresolution.tjss.view.fragment.location;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.network.response.SafetyCircleMember;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;
import com.innoviussoftwaresolution.tjss.view.activity.ChatDetailActivity;
import com.innoviussoftwaresolution.tjss.view.adapter.listadapter.SafetyCircleMembersListingAdapter;
import com.innoviussoftwaresolution.tjss.view.fragment.BaseFragment;
import com.innoviussoftwaresolution.tjss.view.fragment.generalalerts.PermissionsDetailListDialog;
import com.innoviussoftwaresolution.tjss.view.fragment.message.FragmentChatDetail;
import com.innoviussoftwaresolution.tjss.view.viewutils.Permissions;
import com.innoviussoftwaresolution.tjss.viewmodel.LocationSharingViewModel;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Sony Raj on 25-07-17.
 */

public class FragmentLocationSharing extends BaseFragment implements SafetyCircleMembersListingAdapter.OnMemberSelectedCallback {

    @BindView(R.id.lblLocationSharingHeading)
    AppCompatTextView mLblLocationSharingHeading;

    @BindView(R.id.lblLocationSharingInfo)
    AppCompatTextView mLblLocationSharingInfo;

    @BindView(R.id.imgLocationSharingUserImage)
    CircleImageView mImgLocationSharingUserImage;

    @BindView(R.id.lblLocationSharingUserName)
    AppCompatTextView mLblLocationSharingUserName;

    @BindView(R.id.lblLocationSharingEmail)
    AppCompatTextView mLblLocationSharingEmail;

    @BindView(R.id.tglLocationSharingShareMyLocation)
    SwitchCompat mTglLocationSharingShareMyLocation;

    @BindView(R.id.lblLocationSharingLocationOfPeopleInCircle)
    AppCompatTextView mLblLocationSharingLocationOfPeopleInCircle;

    @BindView(R.id.lstLocationSharingLocationOfPeopleInCircle)
    RecyclerView mLstLocationSharingLocationOfPeopleInCircle;

    Unbinder unbinder;

    boolean ignoreEvent = false;

    private LocationSharingViewModel mViewModel;
    private Observer<SafetyCircleMember[]> mSafetyCircleMembersObserver
            = new Observer<SafetyCircleMember[]>() {
        @Override
        public void onChanged(@Nullable SafetyCircleMember[] safetyCircleMembers) {
            if (safetyCircleMembers == null || safetyCircleMembers.length <= 0) return;
            SafetyCircleMembersListingAdapter adapter
                    = new SafetyCircleMembersListingAdapter(
                    new ArrayList<>(Arrays.asList(safetyCircleMembers)), getActivity(), FragmentLocationSharing.this);

            mLstLocationSharingLocationOfPeopleInCircle.setAdapter(adapter);
            mLstLocationSharingLocationOfPeopleInCircle.setLayoutManager(new LinearLayoutManager(getActivity()));
            mLstLocationSharingLocationOfPeopleInCircle.addItemDecoration(new DividerItemDecoration(getActivity(),
                    LinearLayoutManager.VERTICAL));
        }
    };
    private String mPhone = null;

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("Location Sharing");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_sharing, container, false);
        unbinder = ButterKnife.bind(this, view);
        boolean locationSharingOn = PreferenceHelper.getBoolean(getActivity(), PreferenceHelper.KEY_LOCATION_SHARED, true);
        if (locationSharingOn && !mTglLocationSharingShareMyLocation.isChecked()) {
            ignoreEvent = true;
            mTglLocationSharingShareMyLocation.setChecked(true);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new LocationSharingViewModel.LocationSharingViewModelFactory(getActivity().getApplication()))
                .get(LocationSharingViewModel.class);

        mViewModel.getSafetyCircleMembersData().observe(this, mSafetyCircleMembersObserver);

        String userName = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_FIRST_NAME, "");
        String email = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_EMAIL, "");
        String imagePath = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_PROFILE_IMAGE, "");
        String imageBaseUrl = getString(R.string.image_base_url);


        mLblLocationSharingUserName.setText(userName);
        mLblLocationSharingEmail.setText(email);
        Glide.with(getActivity())
                .load(imageBaseUrl + imagePath)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        mImgLocationSharingUserImage.setImageDrawable(resource);
                    }
                });
    }

    @OnCheckedChanged({R.id.tglLocationSharingShareMyLocation})
    public void onCheckedChanged(boolean isChecked) {
        if (ignoreEvent) {
            ignoreEvent = false;
            return;
        }
        mViewModel.update(isChecked);
        mLblLocationSharingInfo.setText(isChecked ?
                "You are sharing your location with this safety circle" :
                "You are not sharing your location with this safety circle, unless you send a help alert");

        PreferenceHelper.saveBoolean(getActivity(), PreferenceHelper.KEY_LOCATION_SHARED, isChecked);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSelected(SafetyCircleMember member, boolean call) {
        String currentUserId = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_USER_ID, "0");
        if (member.userId.equals(currentUserId)) {
            M.showToast(getActivity(), "Not Allowed");
            return;
        }
        if (call) {
            mPhone = member.phone;
            if (mPhone == null || mPhone.equals("")) return;
            String phone = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_PHONE, "");
            if (phone.contains(mPhone)) {
                M.showToast(getActivity(), "Not Allowed");
                return;
            }
            if (!havePermission(Permissions.CALL_PHONE)) {
                if (shouldShowPermissionReationale(Permissions.CALL_PHONE) != null) {
                    ArrayList<String> permissionDetails = new ArrayList<>(1);
                    permissionDetails.add("Request for Permission-TJSS will need your permission to make a call to your safety circle member");
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
        } else {
            int other = Integer.parseInt(member.userId);

            Intent chatDetailIntent = new Intent(getActivity(), ChatDetailActivity.class);
            chatDetailIntent.putExtra(FragmentChatDetail.USERS, String.valueOf(other));
            chatDetailIntent.putExtra(FragmentChatDetail.GROUP_NAME, member.fname);
            startActivity(chatDetailIntent);
        }
    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + mPhone));
        startActivity(intent);
        mPhone = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Permissions.CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                call();
            } else {
                M.showToast(getActivity(), "Unable to make call without permission, Permission Denied!!!");
                mPhone = null;
            }
        }
    }
}
