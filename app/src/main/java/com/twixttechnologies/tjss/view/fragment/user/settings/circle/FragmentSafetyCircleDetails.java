package com.twixttechnologies.tjss.view.fragment.user.settings.circle;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.exception.NoCircleIdFoundException;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.model.network.response.UserIdAndStatus;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.utils.PreferenceHelper;
import com.twixttechnologies.tjss.view.activity.EditSafetyCircleDetailsActivity;
import com.twixttechnologies.tjss.view.adapter.listadapter.SafetyCircleMembersAdapter;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.viewmodel.SafetyCircleViewModel;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Sony Raj on 25-07-17.
 */

public class FragmentSafetyCircleDetails extends BaseFragment implements SafetyCircleMembersAdapter.OnAdminStatusChangedListener {

    public static final String SAFETY_CIRCLE_ID = "safetyCircleId";
    public static final String SAFETY_CIRCLE_NAME = "safetyCircleName";
    public static final String SAFETY_CIRCLE_IMAGE_LINK = "safetyCircleImageLink";
    public static final String IS_ADMIN = "isAdmin";
    private static final int EDIT_REQUEST_CODE = 1;

    @BindView(R.id.lblSafetyCircleName)
    AppCompatTextView mLblSafetyCircleName;
    @BindView(R.id.lstSafetyCircleMembers)
    RecyclerView mLstSafetyCircleMembers;
    @BindView(R.id.btnLeaveSafetyCircle)
    AppCompatButton mBtnLeaveSafetyCircle;
    Unbinder unbinder;
    @BindView(R.id.imgSafetyCircleDp)
    CircleImageView mImgSafetyCircleDp;
    private String mCircleId;
    private String mCircleName;
    private String mCircleImage;
    private boolean mIsAdmin;

    private int mSelectedIndex = -1;

    private SafetyCircleViewModel mViewModel;
    private Observer<SafetyCircleMember[]> mSafetyCircleMembersObserver
            = new Observer<SafetyCircleMember[]>() {
        @Override
        public void onChanged(@Nullable SafetyCircleMember[] safetyCircleMembers) {
            if (safetyCircleMembers == null || safetyCircleMembers.length <= 0) return;
            SafetyCircleMembersAdapter adapter = new SafetyCircleMembersAdapter(
                    new ArrayList<>(Arrays.asList(safetyCircleMembers)), getActivity(), FragmentSafetyCircleDetails.this);

            mLstSafetyCircleMembers.setAdapter(adapter);
            mLstSafetyCircleMembers.setLayoutManager(new LinearLayoutManager(getActivity()));
            mLstSafetyCircleMembers.addItemDecoration(new DividerItemDecoration(getActivity(),
                    LinearLayoutManager.VERTICAL));
        }
    };


    private Observer<UserIdAndStatus> mDeletedataObserver
            = new Observer<UserIdAndStatus>() {
        @Override
        public void onChanged(@Nullable UserIdAndStatus userIdAndStatus) {
            if (userIdAndStatus == null) {
                M.showAlert(getActivity(), "Leave safety circle", "An error occurred, Please try again later",
                        "OK", null, null, null, false);
            } else {
                getActivity().finish();
            }
        }
    };


    private Observer<StatusMessage> mStatusMessageObserver
            = new Observer<StatusMessage>() {
        @Override
        public void onChanged(@Nullable StatusMessage statusMessage) {
            dismissDialog();
            if (statusMessage == null || statusMessage.status.equals("") || !statusMessage.status.toLowerCase().contains("success")) {
                ((SafetyCircleMembersAdapter) mLstSafetyCircleMembers.getAdapter()).update(mSelectedIndex, false);
                mSelectedIndex = -1;
            } else {
                ((SafetyCircleMembersAdapter) mLstSafetyCircleMembers.getAdapter()).update(mSelectedIndex, true);
                M.showAlert(getActivity(), "Add/Remove admin", "Updated successfully", "OK", null, null, null, false);
            }
        }
    };

    public static FragmentSafetyCircleDetails newInstance(String circleId, String circleName,
                                                          String image, boolean isAdamin) {
        Bundle args = new Bundle();
        args.putString(SAFETY_CIRCLE_ID, circleId);
        args.putString(SAFETY_CIRCLE_NAME, circleName);
        args.putString(SAFETY_CIRCLE_IMAGE_LINK, image);
        args.putBoolean(IS_ADMIN, isAdamin);
        FragmentSafetyCircleDetails fragment = new FragmentSafetyCircleDetails();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        try {
            Bundle b = getArguments();
            mCircleId = b.getString(SAFETY_CIRCLE_ID, null);
            mCircleImage = b.getString(SAFETY_CIRCLE_IMAGE_LINK, null);
            mCircleName = b.getString(SAFETY_CIRCLE_NAME, null);
            mIsAdmin = b.getBoolean(IS_ADMIN, false);
        } catch (NullPointerException e) {
            NoCircleIdFoundException exception = new NoCircleIdFoundException("Circle id not found");
            exception.setStackTrace(e.getStackTrace());
            throw exception;
        }

        setHasOptionsMenu(mIsAdmin);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_safety_circle_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this, new SafetyCircleViewModel
                .SafetyCircleViewModelFactory(getActivity().getApplication()))
                .get(SafetyCircleViewModel.class);

        if (mCircleId == null)
            throw new NoCircleIdFoundException("Circle Id not Found");

        mLblSafetyCircleName.setText(mCircleName);
        Glide.with(getActivity())
                .asBitmap()
                .load(getString(R.string.base_url) + "circleimages/" + mCircleImage)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        mImgSafetyCircleDp.setImageBitmap(resource);
                    }
                });

        mViewModel.getSafetyCircleMembersData().observe(this, mSafetyCircleMembersObserver);
        mViewModel.getCircleMembers(mCircleId);
        mViewModel.getDeleteResponseData().observe(this, mDeletedataObserver);
        mViewModel.getUpdateAdminStatusMessageData().observe(this, mStatusMessageObserver);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (!mIsAdmin) return;
        inflater.inflate(R.menu.edit, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != EDIT_REQUEST_CODE) return;
        if (resultCode != Activity.RESULT_OK) return;
        if (data == null) return;
        mCircleName = data.getStringExtra("name");
        mLblSafetyCircleName.setText(mCircleName);
        String path = data.getStringExtra("image");
        if (TextUtils.isEmpty(path)) return;
        mCircleImage = path;
        Glide.with(getActivity())
                .asBitmap()
                .load(getString(R.string.base_url) + "circleimages/" + mCircleImage)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        mImgSafetyCircleDp.setImageBitmap(resource);
                    }
                });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!mIsAdmin) return super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.mnuEdit) {
            Intent editIntent = new Intent(getActivity(), EditSafetyCircleDetailsActivity.class);
            editIntent.putExtra(SAFETY_CIRCLE_ID, mCircleId);
            editIntent.putExtra(SAFETY_CIRCLE_NAME, mCircleName);
            editIntent.putExtra(SAFETY_CIRCLE_IMAGE_LINK, mCircleImage);
            editIntent.putExtra(IS_ADMIN, mIsAdmin);
            startActivityForResult(editIntent, EDIT_REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnLeaveSafetyCircle)
    public void onViewClicked() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Leave circle")
                .setCancelable(false)
                .setPositiveButton("LEAVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userId = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_USER_ID, "");
                        if (userId.equals("") || mCircleId == null || mCircleId.equals("")) return;
                        if (mLstSafetyCircleMembers.getAdapter().getItemCount() == 1) {
                            M.showAlert(getActivity(), "Leave safety circle", "A group should contain atleast one member", "OK",
                                    null, null, null, false);
                            return;
                        }

                        if (mIsAdmin && ((SafetyCircleMembersAdapter) mLstSafetyCircleMembers.getAdapter()).numberOfAdmins() == 1) {
                            M.showAlert(getActivity(), "Leave safety circle", "A group should have atleast one admin",
                                    "OK", null, null, null, false);
                            return;
                        }

                        mViewModel.deleteMember(mCircleId, userId);
                    }
                })
                .setNegativeButton("DON'T LEAVE", null)
                .setMessage("Are you sure, you want to leave this circle?")
                .show();
    }

    @Override
    public void onStatusChanged(SafetyCircleMember safetyCircleMember, int index) {
        int count = mLstSafetyCircleMembers.getAdapter().getItemCount();
        if (count <= 1) {
            M.showToast(getActivity(), "A group should have at-least one admin");
            return;
        }

        if (!mIsAdmin) {
            M.showToast(getActivity(), "You have to be an admin to assign roles");
            ((SafetyCircleMembersAdapter) mLstSafetyCircleMembers.getAdapter()).update(index, false);
            return;
        }

        if (((SafetyCircleMembersAdapter) mLstSafetyCircleMembers.getAdapter()).numberOfAdmins() <= 1) {
            if (safetyCircleMember.admin != 0) {
                M.showToast(getActivity(), "A group should have at-least one admin");
                return;
            }
        }


        initProgress();
        initErrorObserver();
        mViewModel.updateAdminStatus(safetyCircleMember, mCircleId);
        mSelectedIndex = index;
    }
}
