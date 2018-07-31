package com.twixttechnologies.tjss.view.fragment.user;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.TjssApplication;
import com.twixttechnologies.tjss.model.network.response.IsdCode;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.model.network.response.UserProfile;
import com.twixttechnologies.tjss.utils.FileUtil;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.utils.PreferenceHelper;
import com.twixttechnologies.tjss.view.activity.DeleteAccountActivity;
import com.twixttechnologies.tjss.view.activity.SettingsActivity;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.view.fragment.generalalerts.PermissionsDetailListDialog;
import com.twixttechnologies.tjss.view.fragment.user.alert.FirstAndLastNameDialog;
import com.twixttechnologies.tjss.view.viewutils.Permissions;
import com.twixttechnologies.tjss.viewmodel.UserProfileViewModel;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Sony Raj on 15-07-17.
 */

@SuppressWarnings("HardCodedStringLiteral")
public class FragmentUserProfile extends BaseFragment implements PermissionsDetailListDialog.PermissionsDialogCallback, FirstAndLastNameDialog.OnNameUpdateListener {

    public static final String TAG = "fragmentUserProfile";

    private static final int SELECT_IMAGE = 1;

    @BindView(R.id.imgUserProfileImage)
    CircleImageView mImgUserProfileImage;

    @BindView(R.id.lblUserProfileName)
    AppCompatTextView mLblUserProfileName;

    @BindView(R.id.lblUserProfileEmail)
    AppCompatTextView mLblUserProfileEmail;

    @BindView(R.id.lblUserProfileFirstName)
    AppCompatTextView mLblUserProfileFirstName;

    @BindView(R.id.lblUserProfileLastName)
    AppCompatTextView mLblUserProfileLastName;

    @BindView(R.id.lblUserProfileEmail2)
    AppCompatTextView mLblUserProfileEmail2;

    @BindView(R.id.cboUserProfileIsdCodes)
    AppCompatSpinner mCboUserProfileIsdCodes;

    @BindView(R.id.lblUserProfilePhone)
    AppCompatTextView mLblUserProfilePhone;

    Unbinder unbinder;

    private String mFirstName, mLastName;

    private UserProfileViewModel mViewModel;

    private Observer<StatusMessage> mProfileUpdateObserver
            = new Observer<StatusMessage>() {
        @Override
        public void onChanged(@Nullable StatusMessage statusMessage) {
            dismissDialog();
            if (statusMessage == null || !statusMessage.status.toLowerCase().contains("success")) {
                M.showAlert(getActivity(), "Update profile image", "An error occurred, Please try again later",
                        "OK", null, null, null, false);
                return;
            }

            if (statusMessage.status.toLowerCase().equals("success")) {
                Map<String, Object> extras = statusMessage.getAdditionalProperties();
                if (extras.containsKey("image")) {
                    String image = (String) extras.get("image");
                    PreferenceHelper.saveString(getActivity(), PreferenceHelper.KEY_PROFILE_IMAGE, image);
                    Log.d("PreferenceImage", PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_PROFILE_IMAGE, ""));
                    M.showToast(getActivity(), "Updated successfully");
                }
            }


        }
    };

    private Observer<StatusMessage> mFirstAndLastNameObserver
            = new Observer<StatusMessage>() {
        @Override
        public void onChanged(@Nullable StatusMessage statusMessage) {
            dismissDialog();
            if (statusMessage == null || !statusMessage.status.toLowerCase().contains("success")) {
                M.showAlert(getActivity(), "Update profile image", "An error occurred, Please try again later",
                        "OK", null, null, null, false);
                return;
            }

            try {
                if (mFirstName != null) {
                    PreferenceHelper.saveString(getActivity(), PreferenceHelper.KEY_FIRST_NAME, mFirstName);
                    mLblUserProfileFirstName.setText(mFirstName);
                }
                else {
                    mLblUserProfileFirstName.setText("Dummy");
                }
                if (mLastName != null) {
                    PreferenceHelper.saveString(getActivity(), PreferenceHelper.KEY_LAST_NAME, mLastName);
                    mLblUserProfileLastName.setText(mLastName);
                    if (TextUtils.isEmpty(mLblUserProfileLastName.getText())) {
                        mLblUserProfileLastName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    } else {
                        mLblUserProfileLastName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.white_check_in_accent, 0);
                    }
                }
                else {
                    mLblUserProfileFirstName.setText("Dummy");
                }

            } catch (Exception e) {
                M.log(e.getMessage());
            }

        }
    };

    private Observer<UserProfile> mUserProfileObserver
            = new Observer<UserProfile>() {
        @Override
        public void onChanged(@Nullable UserProfile userProfile) {
            String userFname,userLname,userPhone,userEmail;
            if (userProfile == null) return;
            mViewModel.getIsdCodes();
            try {
                ((TjssApplication) getActivity().getApplication()).getGlide()
                        .load(getString(R.string.image_base_url) + userProfile.profileImage)
                        .into(mImgUserProfileImage);



                userEmail=userProfile.email.toString();
                userFname=userProfile.fname.toString();
                userLname=userProfile.lname.toString();
                userPhone=userProfile.userPhone.toString();
                    Toast.makeText(getActivity(),userEmail,Toast.LENGTH_LONG).show();


                try {
                    mLblUserProfileFirstName.setText(userProfile.fname);
                    mLblUserProfileLastName.setText(userProfile.lname);
                } catch (Exception e) {
                    M.log(e.getMessage());
                    //safeguarding Array index out bound exception
                }
               // mLblUserProfileName.setText(MessageFormat.format("{0} {1}", userProfile.fname == null ? "" : userProfile.fname));
                mLblUserProfileName.setVisibility(View.GONE);
//                if(userFname!=null )
//                {
//                    mLblUserProfileName.setText(userFname.toString());
//                }
                Log.d("userProfile",userProfile.fname);
//               mLblUserProfileName.setText(PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_FIRST_NAME, "") + " " +
//                        PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_FIRST_NAME + " ", ""));


                //mLblUserProfilePhone.setText(userProfile.userPhone);

                if (TextUtils.isEmpty(mLblUserProfileLastName.getText())) {
                    mLblUserProfileLastName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else {
                    mLblUserProfileLastName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.white_check_in_accent, 0);
                }


            } catch (Exception e) {
                M.log(e.getMessage());
            }

        }
    };
    private Observer<List<IsdCode>> mIsdCodeListObserver
            = new Observer<List<IsdCode>>() {
        @Override
        public void onChanged(@Nullable List<IsdCode> isdCodes) {
            if (isdCodes == null) return;
            if (isdCodes.size() <= 0) return;

            int index = 0;
            if (mViewModel.getUserProfileData().getValue() != null) {
                String isdCode = mViewModel.getUserProfileData().getValue().isdcode;
                if (isdCode != null && !isdCode.equals("")) {
                    for (int i = 0; i < isdCodes.size(); i++) {
                        if (isdCodes.get(i).isdCode.equals(isdCode)) {
                            index = i;
                            break;
                        }
                    }
                }
            }

            ArrayAdapter<IsdCode> adapter = new ArrayAdapter<>(getActivity(), R.layout.extra_simple_spinner_item, R.id.lblSpinnerLabel, isdCodes);
            if (mCboUserProfileIsdCodes == null) return;
            mCboUserProfileIsdCodes.setAdapter(adapter);
            if (adapter.getCount() > 0) {
                mCboUserProfileIsdCodes.setSelection(index);
            }
        }
    };

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgament_user_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(this,
                new UserProfileViewModel.UserProfileViewModelFactory(getActivity().getApplication()))
                .get(UserProfileViewModel.class);

        mViewModel.getUserProfileData().observe(this, mUserProfileObserver);
        mViewModel.getIsdCodesData().observe(this, mIsdCodeListObserver);
        mLblUserProfileFirstName.setText(PreferenceHelper.getString(getActivity(),PreferenceHelper.KEY_FIRST_NAME,"").toString());
        mLblUserProfileLastName.setText(PreferenceHelper.getString(getActivity(),PreferenceHelper.KEY_LAST_NAME,"").toString());
        mLblUserProfileEmail.setText(PreferenceHelper.getString(getActivity(),PreferenceHelper.KEY_EMAIL,"").toString());
        mLblUserProfileEmail2.setText(PreferenceHelper.getString(getActivity(),PreferenceHelper.KEY_EMAIL,"").toString());
//        mLblUserProfileName.setText(PreferenceHelper.getString(getActivity(),PreferenceHelper.KEY_FIRST_NAME,"").toString()+" "
//                                     + PreferenceHelper.getString(getActivity(),PreferenceHelper.KEY_LAST_NAME,"").toString());
        mLblUserProfilePhone.setText(PreferenceHelper.getString(getActivity(),PreferenceHelper.KEY_PHONE,"").toString());
        mLblUserProfilePhone.setVisibility(View.GONE);
        Log.d("preferenceValue",PreferenceHelper.getString(getActivity(),PreferenceHelper.KEY_EMAIL,""));


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnUserProfileMyAccount, R.id.btnUserProfileDeleteAccount, R.id.btnUserProfileChangeImage,
            R.id.lblUserProfileFirstName, R.id.lblUserProfileLastName})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnUserProfileMyAccount:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                break;
            case R.id.btnUserProfileDeleteAccount:
                startActivity(new Intent(getActivity(), DeleteAccountActivity.class));
                break;
            case R.id.lblUserProfileFirstName:
                showDialog();
                break;
            case R.id.lblUserProfileLastName:
                showDialog();
                break;
            case R.id.btnUserProfileChangeImage:
                ArrayList<String> permissionsRequired
                        = Permissions.havePermissionFor(getActivity(), Permissions.READ_EXTERNAL_STORAGE);

                if (permissionsRequired == null) {
                    selectImage();
                } else {
                    showPermissionsDetails();
                }

                break;
        }
    }

    private void showDialog() {
        FirstAndLastNameDialog dialog = new FirstAndLastNameDialog();
        dialog.setListener(this);
        dialog.setCancelable(true);
        dialog.show(getChildFragmentManager(), "firstAndLastNameDialog");
    }

    private void selectImage() {
        Intent imageSelectIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imageSelectIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(imageSelectIntent, "Select Image"), SELECT_IMAGE);
    }

    private void showPermissionsDetails() {
        ArrayList<String> permissionDetails = new ArrayList<>();
        permissionDetails.add("Read Sd Card-TJSS requires your permission to read your phone's Sd Card " +
                "to select an image for your profile");
        PermissionsDetailListDialog dialog = PermissionsDetailListDialog.newInstance(permissionDetails);
        dialog.setCallback(this);
        dialog.setCancelable(false);
        dialog.show(getChildFragmentManager(), PermissionsDetailListDialog.TAG);
    }

    @Override
    public void onPermissionGranted() {
        Permissions.requestPermissionFromFragment(this,
                Permissions.READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE,
                Permissions.READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != Permissions.READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) return;
        for (int i = 0; i < grantResults.length; i++) {
            switch (permissions[i]) {
                case Permissions.READ_EXTERNAL_STORAGE:
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        selectImage();
                    }
                    break;
            }
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
        String image = FileUtil.getPath(getActivity(), uri);
        if (image == null) {
            M.showToast(getActivity(), "Failed to select image");
            return;
        }
        ((TjssApplication) getActivity().getApplication()).getGlide()
                .load(image)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        mImgUserProfileImage.setImageDrawable(resource);
                    }
                });
        initProgress();
        mViewModel.getProfileImageData().observe(this, mProfileUpdateObserver);
        mViewModel.updateImage(new File(image));

    }

    @Override
    public void onUpdate(String firstName, String lastName) {
        mFirstName = firstName;
        mLastName = lastName;
        mViewModel.getFirstNameAndLastNameData().observe(this, mFirstAndLastNameObserver);
        initProgress();
        mViewModel.updateFirstAndlastName(firstName, lastName);
    }
}
