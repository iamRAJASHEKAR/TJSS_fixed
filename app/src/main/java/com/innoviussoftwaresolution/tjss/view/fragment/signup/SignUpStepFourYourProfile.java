package com.innoviussoftwaresolution.tjss.view.fragment.signup;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.TjssApplication;
import com.innoviussoftwaresolution.tjss.broadcastreceiver.EditorBroadCastReceiver;
import com.innoviussoftwaresolution.tjss.utils.FileUtil;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.view.fragment.generalalerts.PermissionsDetailListDialog;
import com.innoviussoftwaresolution.tjss.view.viewutils.Permissions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Sony Raj on 07-07-17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class SignUpStepFourYourProfile extends SignUpFragmentBase {

    private static final int SELECT_IMAGE = 1;

    @BindView(R.id.imgSignUpUserImage)
    CircleImageView mImgSignUpUserImage;
    @BindView(R.id.txtRegistrationName)
    TextInputLayout mTxtRegistrationName;
    Unbinder unbinder;

    private String mImage;
    private String mName;
    private PermissionsDetailListDialog.PermissionsDialogCallback permissionDialogCallback
            = new PermissionsDetailListDialog.PermissionsDialogCallback() {
        @Override
        public void onPermissionGranted() {
            requestPermission();
        }
    };

    public String getImage() {

            return mImage;

    }

    public String getName() {
        return (mTxtRegistrationName == null || mTxtRegistrationName.getEditText() == null) ? mName
                : mTxtRegistrationName.getEditText().getText().toString();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mTxtRegistrationName.getEditText() != null)
            mTxtRegistrationName.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    Intent intent = new Intent(EditorBroadCastReceiver.EDITOR_ACTION);
                    LocalBroadcastManager.getInstance(getActivity())
                            .sendBroadcast(intent);
                    return true;
                }
            });
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_step_four_your_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    void setDataOnPause() {
        if (mTxtRegistrationName.getEditText() != null)
            if (mTxtRegistrationName.getEditText().getText() != null)
                mName = mTxtRegistrationName.getEditText().getText().toString();
    }

    @Override
    void setDataOnResume() {
        if (mName != null)
            if (mTxtRegistrationName.getEditText() != null)
                mTxtRegistrationName.getEditText().setText(mName);

        if (mImage != null) {
            ((TjssApplication) getActivity().getApplication()).getGlide()
                    .load(mImage)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable resource,
                                                    Transition<? super Drawable> transition) {
                            mImgSignUpUserImage.setImageDrawable(resource);
                        }
                    });
        }



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnRegistrationSelectImage)
    public void onViewClicked() {
        ArrayList<String> permissionsRequired = Permissions
                .havePermissionFor(getActivity(), Permissions.READ_EXTERNAL_STORAGE);

        if (permissionsRequired == null) {
            selectImage();
        } else {
            ArrayList<String> permissionToShowRationale = Permissions
                    .shouldShowPermissionRequestRationale(getActivity(),
                            Permissions.READ_EXTERNAL_STORAGE);
            if (permissionToShowRationale == null) {
                requestPermission();
            } else {
                showPermissionDetailsDialog();
            }
        }
    }

    private void selectImage() {
        Intent imageSelectIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imageSelectIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(imageSelectIntent, "Select an image"), SELECT_IMAGE);
    }

    private void requestPermission() {
        Permissions.requestPermissionFromFragment(this,
                Permissions.READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE,
                Permissions.READ_EXTERNAL_STORAGE);
    }

    private void showPermissionDetailsDialog() {
        ArrayList<String> permissionDetails = new ArrayList<>(1);
        permissionDetails.add("External storage access-Tjss is requesting access to external storage" +
                "to select image for your profile pic.");
        Permissions.showPermissionDetails(permissionDetails, permissionDialogCallback, getChildFragmentManager());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> grantedPermissions = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                grantedPermissions.add(permissions[i]);
        }
        for (int i = 0; i < grantedPermissions.size(); i++) {
            if (grantedPermissions.get(i).equals(Permissions.READ_EXTERNAL_STORAGE))
                selectImage();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != SELECT_IMAGE) return;
        if (resultCode != Activity.RESULT_OK) return;
        if (data == null) {
            M.log("Unable to select image, data is null");
            return;
        }
        Uri uri = data.getData();
        mImage = FileUtil.getPath(getActivity(), uri);

        ((TjssApplication) getActivity().getApplication()).getGlide()
                .load(mImage)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        mImgSignUpUserImage.setImageDrawable(resource);
                    }
                });
     //   Toast.makeText(getActivity(),mImage,Toast.LENGTH_LONG).show();

    }
}
