package com.twixttechnologies.tjss.view.fragment.user.settings.circle;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
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

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.TjssApplication;
import com.twixttechnologies.tjss.model.network.response.SafetyCircleMember;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.model.network.response.UserIdAndStatus;
import com.twixttechnologies.tjss.utils.FileUtil;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.adapter.listadapter.SafetyCircleMembersAdapterForEdit;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.view.fragment.generalalerts.PermissionsDetailListDialog;
import com.twixttechnologies.tjss.view.viewutils.Permissions;
import com.twixttechnologies.tjss.viewmodel.SafetyCircleViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Sony Raj on 25-07-17.
 */

public class FragmentEditSafetyCircle extends BaseFragment implements SafetyCircleMembersAdapterForEdit.OnDeleteListener {
    public static final String TAG = "FragmentEditSafetyCircle";
    private static final int PICK_IMAGE_REQUEST = 1;
    @BindView(R.id.imgSafetyCircleDp)
    CircleImageView mImgSafetyCircleDp;
    @BindView(R.id.txtSafetyCircleName)
    TextInputEditText mTxtSafetyCircleName;
    @BindView(R.id.lstSafetyCircleMembers)
    RecyclerView mLstSafetyCircleMembers;
    Unbinder unbinder;

    private boolean hasLocalImage = false;

    private SafetyCircleViewModel mViewModel;


    private String mCircleId, mCircleName, mCircleImageLink;
    private boolean mIsAdmin;
    private Observer<SafetyCircleMember[]> mSafetyCircleMembersObserver
            = new Observer<SafetyCircleMember[]>() {
        @Override
        public void onChanged(@Nullable SafetyCircleMember[] safetyCircleMembers) {
            if (safetyCircleMembers == null || safetyCircleMembers.length <= 0) return;
            SafetyCircleMembersAdapterForEdit adapter
                    = new SafetyCircleMembersAdapterForEdit(
                    new ArrayList<>(Arrays.asList(safetyCircleMembers)), getActivity(), FragmentEditSafetyCircle.this);

            mLstSafetyCircleMembers.setAdapter(adapter);
            mLstSafetyCircleMembers.setLayoutManager(new LinearLayoutManager(getActivity()));
            mLstSafetyCircleMembers.addItemDecoration(new DividerItemDecoration(getActivity(),
                    LinearLayoutManager.VERTICAL));
        }
    };

    private Observer<UserIdAndStatus> mDeleteResponseData;
    private Observer<StatusMessage> mUpdateStatusObserver;

    public static FragmentEditSafetyCircle newInstance(String circleId, String circleName, String circleImageLink, boolean isAdmin) {

        Bundle args = new Bundle();
        args.putString(FragmentSafetyCircleDetails.SAFETY_CIRCLE_ID, circleId);
        args.putString(FragmentSafetyCircleDetails.SAFETY_CIRCLE_NAME, circleName);
        args.putString(FragmentSafetyCircleDetails.SAFETY_CIRCLE_IMAGE_LINK, circleImageLink);
        args.putBoolean(FragmentSafetyCircleDetails.IS_ADMIN, isAdmin);
        FragmentEditSafetyCircle fragment = new FragmentEditSafetyCircle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

        Bundle args = getArguments();
        mCircleId = args.getString(FragmentSafetyCircleDetails.SAFETY_CIRCLE_ID);
        mCircleName = args.getString(FragmentSafetyCircleDetails.SAFETY_CIRCLE_NAME);
        mCircleImageLink = args.getString(FragmentSafetyCircleDetails.SAFETY_CIRCLE_IMAGE_LINK);
        mIsAdmin = args.getBoolean(FragmentSafetyCircleDetails.IS_ADMIN, false);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {

            if (mUpdateStatusObserver == null) {
                mUpdateStatusObserver = new Observer<StatusMessage>() {
                    @Override
                    public void onChanged(@Nullable final StatusMessage statusMessage) {
                        dismissDialog();
                        if (statusMessage == null || statusMessage.status.equals("")) {
                            M.showAlert(getActivity(), "Update safety circle", "An error occurred, Please try again later",
                                    "OK", null, null, null, false);
                        } else {
                            M.showAlert(getActivity(), "Update safety circle", statusMessage.status,
                                    "OK", null, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Map<String, Object> extras = statusMessage.getAdditionalProperties();
                                            if (extras.containsKey("image")) {
                                                String image = (String) extras.get("image");
                                                Intent intent = new Intent();
                                                intent.putExtra("image", image);
                                                intent.putExtra("name", mTxtSafetyCircleName.getText().toString());
                                                getActivity().setResult(Activity.RESULT_OK, intent);
                                                getActivity().finish();
                                            }
                                        }
                                    }, null, false);

                        }
                    }
                };
                initProgress();
                initErrorObserver();
                mViewModel.getSafetyCircleUpdateData().observe(FragmentEditSafetyCircle.this, mUpdateStatusObserver);
            }

            mViewModel.updateSafetyCircle(!hasLocalImage ? null : mCircleImageLink,
                    (TextUtils.isEmpty(mTxtSafetyCircleName.getText()) ?
                            mCircleName : mTxtSafetyCircleName.getText().toString()), mCircleId);
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_safety_circle, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTxtSafetyCircleName.setText(mCircleName);
        ((TjssApplication) getActivity().getApplication())
                .getGlide()
                .load(getString(R.string.base_url) + "circleimages/" + mCircleImageLink)
                .into(mImgSafetyCircleDp);


        mViewModel = ViewModelProviders.of(this,
                new SafetyCircleViewModel.SafetyCircleViewModelFactory(getActivity().getApplication()))
                .get(SafetyCircleViewModel.class);

        mViewModel.getCircleMembers(mCircleId);
        mViewModel.getSafetyCircleMembersData().observe(this, mSafetyCircleMembersObserver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnChangeSafetyCircleImage)
    public void onViewClicked() {

        if (!havePermission(Permissions.READ_EXTERNAL_STORAGE)) {
            if (shouldShowPermissionReationale(Permissions.READ_EXTERNAL_STORAGE) != null) {
                ArrayList<String> permissionDetails = new ArrayList<>(1);
                permissionDetails.add("Request to read external storage-TJSS requires your permission to select an image from galley to change safety circle image");
                PermissionsDetailListDialog dialog = PermissionsDetailListDialog.newInstance(permissionDetails);
                dialog.setCallback(new PermissionsDetailListDialog.PermissionsDialogCallback() {
                    @Override
                    public void onPermissionGranted() {
                        requestPermission(Permissions.READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE, Permissions.READ_EXTERNAL_STORAGE);
                    }
                });
                dialog.setCancelable(false);
                dialog.show(getChildFragmentManager(), "permission details");
            } else {
                requestPermission(Permissions.READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE, Permissions.READ_EXTERNAL_STORAGE);
            }
        } else {
            selectImage();
        }
    }

    private void selectImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(galleryIntent, "Choose image"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Permissions.READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                M.showToast(getActivity(), "Unable to select image without permission, Permission denied");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (data == null) return;
        if (requestCode == PICK_IMAGE_REQUEST) {
            String path = FileUtil.getPath(getActivity(), data.getData());
            ((TjssApplication) getActivity().getApplication()).getGlide()
                    .load(path)
                    .into(mImgSafetyCircleDp);
            mCircleImageLink = path;
            hasLocalImage = true;
        }
    }

    @Override
    public void onDelete(SafetyCircleMember safetyCircleMember, final int index) {

        if (!mIsAdmin) {
            M.showToast(getActivity(), "You have to be an admin to add or delete member from a safety circle");
            return;
        }

        initProgress();
        initErrorObserver();
        if (mDeleteResponseData == null) {
            mDeleteResponseData = new Observer<UserIdAndStatus>() {
                @Override
                public void onChanged(@Nullable UserIdAndStatus userIdAndStatus) {
                    dismissDialog();
                    if (userIdAndStatus == null) return;
                    if (userIdAndStatus.status == null || userIdAndStatus.status.equals("") || !userIdAndStatus.status.toLowerCase().contains("success")) {
                        M.showAlert(getActivity(), "Delete Member", "An error occurred, Please try again later", "OK",
                                null, null, null, false);
                        return;
                    }
                    ((SafetyCircleMembersAdapterForEdit) mLstSafetyCircleMembers.getAdapter()).onDelete(index, true);
                }
            };
            if (mViewModel != null)
                mViewModel.getDeleteResponseData().observe(FragmentEditSafetyCircle.this, mDeleteResponseData);
        }
        if (mViewModel != null)
            mViewModel.deleteMember(mCircleId, safetyCircleMember.userId);
    }
}
