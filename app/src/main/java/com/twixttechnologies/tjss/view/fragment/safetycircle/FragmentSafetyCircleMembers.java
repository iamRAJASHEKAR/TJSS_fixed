package com.twixttechnologies.tjss.view.fragment.safetycircle;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
//import com.twixttechnologies.tjss.R;
import com.twixttechnologies.tjss.model.network.response.InviteCode;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.utils.PreferenceHelper;
import com.twixttechnologies.tjss.view.activity.ChatDetailActivity;
import com.twixttechnologies.tjss.view.activity.ShareInviteCodeActivity;
import com.twixttechnologies.tjss.view.adapter.listadapter.SafetyCircleMembersListingAdapter;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.view.fragment.generalalerts.PermissionsDetailListDialog;
import com.twixttechnologies.tjss.view.fragment.message.FragmentChatDetail;
import com.twixttechnologies.tjss.view.viewutils.Permissions;
import com.twixttechnologies.tjss.viewmodel.SafetyCircleViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 19-09-17.
 */

public class FragmentSafetyCircleMembers extends BaseFragment implements SafetyCircleMembersListingAdapter.OnMemberSelectedCallback {

    public static final String TAG = "CircleMembers";
    @BindView(R.id.lstSafetyCircleMembers)
    RecyclerView mLstSafetyCircleMembers;
    Unbinder unbinder;

    private SafetyCircleViewModel mSafetyCircleViewModel;
    private String mPhone = null;

    private String mInviteCode;

    private Observer<SafetyCircleMember[]> mSafetyCircleMembersObserver
            = new Observer<SafetyCircleMember[]>() {
        @Override
        public void onChanged(@Nullable SafetyCircleMember[] safetyCircleMembers) {
            if (safetyCircleMembers == null || safetyCircleMembers.length <= 0) return;
            Geocoder geocoder = new Geocoder(getActivity());

            for (SafetyCircleMember item : safetyCircleMembers) {
                try {
                    List<Address> addresses = geocoder.getFromLocation(item.latitude, item.longitude, 1);
                    item.locationName = addresses.get(0).getLocality();
                } catch (Exception e) {
                    M.log(e.getMessage());

                }
            }
            SafetyCircleMembersListingAdapter adapter
                    = new SafetyCircleMembersListingAdapter(
                    new ArrayList<>(Arrays.asList(safetyCircleMembers)), getActivity(), FragmentSafetyCircleMembers.this);
            mLstSafetyCircleMembers.setAdapter(adapter);
            mLstSafetyCircleMembers.setLayoutManager(new LinearLayoutManager(getActivity()));
            mLstSafetyCircleMembers.addItemDecoration(new DividerItemDecoration(getActivity(),
                    LinearLayoutManager.VERTICAL));

        }
    };

    private boolean shareCode = false;

    private final Observer<InviteCode> mInviteCodeObserver
            = new Observer<InviteCode>() {
        @Override
        public void onChanged(@Nullable InviteCode inviteCode) {
            dismissDialog();
            if (inviteCode == null || inviteCode.inviteCode == null || inviteCode.inviteCode.equals("")) {
                if (shareCode) {
                    M.showAlert(getActivity(), "Invite new Member",
                            "An error occurred, please try again later",
                            "OK", null, null,
                            null, false);
                }
            } else {
                mInviteCode = inviteCode.inviteCode;
                if (shareCode) {
                    share();
                }
            }
        }
    };

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    private void share() {
        Intent shareIntent = new Intent(getActivity(), ShareInviteCodeActivity.class);
        shareIntent.putExtra(ShareInviteCodeActivity.INVITE_CODE, mInviteCode);
        startActivity(shareIntent);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_safety_circle_members, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("Safety Circle Members");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSafetyCircleViewModel = ViewModelProviders.of(this,
                new SafetyCircleViewModel.SafetyCircleViewModelFactory(getActivity().getApplication()))
                .get(SafetyCircleViewModel.class);
        String mCircleId = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_PRIMARY_CIRCLE, "");
        mSafetyCircleViewModel.getSafetyCircleMembersData().observe(this, mSafetyCircleMembersObserver);
        mSafetyCircleViewModel.getInviteCodeData().observe(this, mInviteCodeObserver);
        mSafetyCircleViewModel.getInviteCode(mCircleId);
        mSafetyCircleViewModel.getCircleMembers(mCircleId);
        mLstSafetyCircleMembers.setHasFixedSize(true);
    }

    @Override
    public void onSelected(SafetyCircleMember member, boolean call) {
        String userId = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_USER_ID, "");
        if (member.userId.equals(userId)){
            M.showToast(getActivity(),"Not Allowed");
            return;
        }

        if (call) {

            mPhone = member.phone;
            if (mPhone == null || mPhone.equals("")) return;
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
            if (userId.equals(member.userId)) {
                M.showToast(getActivity(), "Not Allowed");
                return;
            }
            Intent chatDetailIntent = new Intent(getActivity(), ChatDetailActivity.class);
            chatDetailIntent.putExtra(FragmentChatDetail.USERS, member.userId);
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

    @OnClick(R.id.btnAddNewMember)
    public void onViewClicked() {
        if (mInviteCode == null || mInviteCode.equals("")) {
            initProgress();
            initErrorObserver();
            mSafetyCircleViewModel.getInviteCode(PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_PRIMARY_CIRCLE, ""));
            shareCode = true;
        } else {
            share();
        }
    }
}
