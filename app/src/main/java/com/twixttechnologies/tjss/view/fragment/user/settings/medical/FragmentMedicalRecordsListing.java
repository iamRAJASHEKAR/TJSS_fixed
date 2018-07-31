package com.twixttechnologies.tjss.view.fragment.user.settings.medical;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.twixttechnologies.tjss.model.network.response.MedicalRecord;
import com.twixttechnologies.tjss.model.network.response.StatusMessage;
import com.twixttechnologies.tjss.utils.FileUtil;
import com.twixttechnologies.tjss.utils.M;
import com.twixttechnologies.tjss.view.adapter.listadapter.MedicalRecordsListAdapter;
import com.twixttechnologies.tjss.view.fragment.BaseFragment;
import com.twixttechnologies.tjss.view.fragment.generalalerts.PermissionsDetailListDialog;
import com.twixttechnologies.tjss.view.fragment.generalalerts.TextInputDialog;
import com.twixttechnologies.tjss.view.viewutils.Permissions;
import com.twixttechnologies.tjss.viewmodel.MedicalRecordsViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Sony Raj on 20-10-17.
 */

public class FragmentMedicalRecordsListing extends BaseFragment implements
        PermissionsDetailListDialog.PermissionsDialogCallback,
        TextInputDialog.OnTextConfirmedCallback,
        MedicalRecordsListAdapter.DeleteRecordCallback {

    public static final String TAG = "MedicalRecordsListing";
    private static final int SELECT_FILE_REQUEST_CODE = 1;
    @BindView(R.id.lstMedicalRecords)
    RecyclerView mLstMedicalRecords;
    Unbinder unbinder;
    private int mSelectedRecordIndexToDelete = -1;
    private String mFilePath;

    private MedicalRecordsViewModel mViewModel;

    private Observer<StatusMessage> mUploadStatusObserver
            = new Observer<StatusMessage>() {
        @Override
        public void onChanged(@Nullable StatusMessage statusMessage) {
            dismissDialog();
            if (statusMessage == null || statusMessage.status.equals("") ||
                    !statusMessage.status.toLowerCase().contains("success")) {
                M.showAlert(getActivity(), "Upload Medical Records",
                        (statusMessage == null || statusMessage.status.equals("")) ?
                                "An error occurred while uploading records, please try again later"
                                : statusMessage.status,
                        "OK", null, null, null, false);
            } else {
                M.showAlert(getActivity(), "Upload Medical Record", "Uploaded successfully", "OK",
                        null, null, null, true);
                mViewModel.list();
            }
        }
    };

    private Observer<ArrayList<MedicalRecord>> mMedicalRecordsListObserver
            = new Observer<ArrayList<MedicalRecord>>() {
        @Override
        public void onChanged(@Nullable ArrayList<MedicalRecord> medicalRecords) {
            if (medicalRecords == null || medicalRecords.size() <= 0) return;
            mLstMedicalRecords.setAdapter(new MedicalRecordsListAdapter(medicalRecords, FragmentMedicalRecordsListing.this));
            mLstMedicalRecords.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    };


    private Observer<StatusMessage> mRecordDeleteObserver
            = new Observer<StatusMessage>() {
        @Override
        public void onChanged(@Nullable StatusMessage statusMessage) {
            dismissDialog();
            if (statusMessage == null || statusMessage.status.equals("") ||
                    !statusMessage.status.toLowerCase().contains("success")) {
                M.showAlert(getActivity(), "Delete Medical Records",
                        (statusMessage == null || statusMessage.status.equals("")) ?
                                "An error occurred while deleting record, please try again later"
                                : statusMessage.status,
                        "OK", null, null, null, false);
            } else {
                M.showAlert(getActivity(), "Delete Medical Record", "Deleted successfully", "OK",
                        null, null, null, true);
                ((MedicalRecordsListAdapter) mLstMedicalRecords.getAdapter()).remove(mSelectedRecordIndexToDelete);
            }
        }
    };


    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medical_records, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = ViewModelProviders.of(this,
                new MedicalRecordsViewModel.MedicalRecordsViewModelFactory(getActivity().getApplication()))
                .get(MedicalRecordsViewModel.class);
        mViewModel.getRecordUploadResultData().observe(this, mUploadStatusObserver);
        initErrorObserver();
        mViewModel.getError().observe(this, mErrorObserver);
        mViewModel.getMedicalRecordsData().observe(this, mMedicalRecordsListObserver);
        mViewModel.getDeleteStatusData().observe(this, mRecordDeleteObserver);
    }

    @Override
    protected void initErrorObserver() {
        mErrorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s != null && !s.equals(""))
                    M.showAlert(getActivity(), "Upload Medical Records", s, "OK", null, null, null, false);
            }
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle("Medical Records");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnAddNewMedicalRecord)
    public void onViewClicked() {
        ArrayList<String> permissionRequired = Permissions.havePermissionFor(getActivity(),
                Permissions.READ_EXTERNAL_STORAGE);
        if (permissionRequired == null) {
            selectFile();
        } else {
            if (Permissions.shouldShowPermissionRequestRationale(getActivity(),
                    Permissions.READ_EXTERNAL_STORAGE) != null) {
                ArrayList<String> permissions = new ArrayList<>(1);
                permissionRequired.add("Request to read external storage-TJSS requires your permission " +
                        "to read external storage to select medical documents");
                PermissionsDetailListDialog permissionsDetailListDialog = PermissionsDetailListDialog.newInstance(permissions);
                permissionsDetailListDialog.setCallback(this);
                permissionsDetailListDialog.setCancelable(false);
                permissionsDetailListDialog.show(getChildFragmentManager(), "permissionsDialog");
            } else {
                requestPermission();
            }
        }
    }

    private void requestPermission() {
        Permissions.requestPermissionFromFragment(this,
                Permissions.READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE,
                Permissions.READ_EXTERNAL_STORAGE);
    }

    private void selectFile() {

        String[] mimeTypes =
                {"application/pdf", "application/msword", "application/vnd.ms-excel"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), SELECT_FILE_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Permissions.READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectFile();
            } else {
                M.showAlert(getActivity(), "Select File", "Failed to select Medical Record, " +
                        "Permission denied!, Please try again or go to settings and grant external " +
                        "storage permission to select medical record", "OK", null, null, null, false);
            }
        }
    }

    @Override
    public void onPermissionGranted() {
        requestPermission();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            mFilePath = FileUtil.getPath(getActivity(), uri);
            if (mFilePath != null && !mFilePath.equals("")) {
                if (!mFilePath.endsWith("doc") && !mFilePath.endsWith("docx") && !mFilePath.endsWith("pdf")
                        && !mFilePath.endsWith("xls") && !mFilePath.endsWith("xlsx")) {
                    M.showAlert(getActivity(), "Error", "Only \"Pdf\", \"Word\" and \"Excel\" " +
                            "files are supported", "OK", null, null, null, true);
                } else {
                    final TextInputDialog dialog = TextInputDialog.newInstance("Enter a title");
                    dialog.setCancelable(false);
                    dialog.setCallback(this);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.show(getChildFragmentManager(), "TextInoutDialog");
                        }
                    }, 500);
                }
            }
        }
    }

    @Override
    public void processText(CharSequence text) {
        initProgress();
        mViewModel.uploadData(text.toString(), mFilePath);
    }

    @Override
    public void delete(MedicalRecord record, int index) {
        mSelectedRecordIndexToDelete = index;
        initProgress();
        mViewModel.delete(record.id);
    }
}
